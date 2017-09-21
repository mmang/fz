package com.wzg.readbook.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.Charset
import okhttp3.Headers
import okhttp3.Protocol
import okio.Buffer
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import java.net.HttpURLConnection.HTTP_NOT_MODIFIED
import java.net.HttpURLConnection.HTTP_NO_CONTENT
import okhttp3.internal.http.StatusLine.HTTP_CONTINUE


/**
 * Created by mangmang on 2017/8/28.
 */
class LoggingInterceptor(val logger: Logger) : Interceptor {

    private val UTF8: Charset = Charset.forName("UTF-8")
    private lateinit var level: Level

    fun setLevel(level: Level): LoggingInterceptor {
        if (level == null) throw NullPointerException()
        this.level = level
        return this
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (level == Level.NONE) {
            return chain.proceed(request)
        }

        val logBody = level == Level.BODY
        val logHeaders = logBody || level == Level.HEADERS

        val body = request.body()

        val hasRequestBody = body != null

        val connection = chain.connection()
        val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1

        val requestStartMessage = "--->${request.method()}  ${request.url()}  ${protocol(protocol)}"

        if (!logHeaders && hasRequestBody) {
            requestStartMessage.plus("(${body?.contentLength()} - byte body)");
        }

        logger.log(requestStartMessage)

        if (logHeaders) {
            if (hasRequestBody) {
                if (body?.contentType() != null) {
                    logger.log("Content-Type:  ${body?.contentType()}")
                }
                if (body?.contentLength() != -1L) {
                    logger.log("Content-Length : ${body?.contentLength()}")
                }
            }
            // 打印请求头
            val headers = request.headers()
            for (name in headers.names()) {
                if (!"Content-Type".equals(name, true) && !"Content-Length".equals(name, true)) {
                    logger.log(name + ": " + headers.get(name))
                }
            }

            if (!logBody || !hasRequestBody) {
                logger.log("---END  ${request.method()}")
            } else if (bodyEncoded(request.headers())) {
                logger.log("-----END ${request.method()}  (encoded body omitted ) ")
            } else {
                val buffer = Buffer()
                body?.writeTo(buffer)
                var charset = UTF8;
                if (body?.contentType() != null) {
                    charset = body.contentType()!!.charset()!!
                }

                logger.log("--------------")
                logger.log(buffer.readString(charset))
                logger.log("---END  ${request.method()}  ( ${body?.contentLength()}   -byte body)")

            }
        }

        val nanoTime = System.nanoTime()
        val response = chain.proceed(request)
        val toMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime)


        val responseBody = response.body()
        val contentLength = responseBody?.contentLength()
        val bodySize = if (contentLength != -1L) "${contentLength}  -byte" else "unknow-length";
        logger.log("<----------" +
                "${response.code()} "
                + ' '
                + response.message()
                + ' '
                + response.request().url()
                + " ("
                + toMillis
                + "ms"
                + (if (!logHeaders) ", ${bodySize}  body" else "")
                + (')'))

        if(logHeaders){
            val headers = response.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                logger.log(headers.name(i) + ": " + headers.value(i))
                i++
            }


//
            if (!logBody || !hasBody(response)) {
                logger.log("<-- END HTTP")
            } else if (bodyEncoded(response.headers())) {
                logger.log("<-- END HTTP (encoded body omitted)")
            } else {
                val source = responseBody?.source()
                source?.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source?.buffer()

                var charset = UTF8
                val contentType = responseBody?.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)!!
                }

                if (contentLength !== 0L) {
                    logger.log("")
                    logger.log(buffer!!.clone().readString(charset))
                }

                logger.log("<-- END HTTP (" + buffer!!.size() + "-byte body)")
            }
        }


        return response

    }

    fun hasBody(response: Response): Boolean {
        // HEAD requests never yield a body regardless of the response headers.
        if (response.request().method() == "HEAD") {
            return false
        }

        val responseCode = response.code()
        if ((responseCode < HTTP_CONTINUE || responseCode >= 200)
                && responseCode != HTTP_NO_CONTENT
                && responseCode != HTTP_NOT_MODIFIED) {
            return true
        }
        if (stringToLong(response.headers().get("Content-Length")) !== -1L || "chunked".equals(response.header("Transfer-Encoding")!!, ignoreCase = true)) {
            return true
        }

        return false
    }

    private fun stringToLong(s: String?): Long {
        if (s == null) return -1
        try {
            return java.lang.Long.parseLong(s)
        } catch (e: NumberFormatException) {
            return -1
        }

    }

    private fun protocol(protocol: Protocol): String {
        return if (protocol == Protocol.HTTP_1_0) "HTTP/1.0" else "HTTP/1.1"
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return contentEncoding != null && !contentEncoding!!.equals("identity", ignoreCase = true)
    }


    interface Logger {
        fun log(message: String)

        companion object {
            /**
             * A [Logger] defaults output appropriate for the current platform.
             */
            val DEFAULT: Logger = object : Logger {
                override fun log(message: String) {
                    //Platform.get().log(4, message, null);
                }
            }
        }
    }


    enum class Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         * <p/>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1 (3-byte body)
         * <p/>
         * <-- HTTP/1.1 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p/>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         * <p/>
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p/>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * <p/>
         * Hi?
         * --> END GET
         * <p/>
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <p/>
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }
}