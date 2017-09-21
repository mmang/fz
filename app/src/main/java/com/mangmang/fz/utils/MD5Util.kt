package com.mangmang.fz.utils

import java.security.MessageDigest
import kotlin.experimental.and

/**
 * Created by mangmang on 2017/9/18.
 */
object MD5Util {

    fun md5Encode(string: String): String {

        var md5 = MessageDigest.getInstance("MD5")

        val bytes = string.toByteArray(Charsets.UTF_8)

        val md5Bytes = md5.digest(bytes)

        val hexValue = StringBuilder()
        for (value in md5Bytes) {
            val value = value.toInt() and (0xff)

            if (value < 16) {
                hexValue.append("0")
            }
            hexValue.append(Integer.toHexString(value))
        }

        return hexValue.toString()
    }

}