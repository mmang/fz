package com.mangmang.fz.net

import com.mangmang.fz.bean.Dynamic
import com.mangmang.fz.bean.User
import io.reactivex.Observable
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by mangmang on 2017/9/13.
 */
interface ApiService {


    companion object {
        val BASE_URL: String
            get() = "http://api.feizan.com"
    }

    @POST("api.php")
    @FormUrlEncoded
    fun login(@QueryMap queryMap: Map<String, String>, @FieldMap params: Map<String, String>): Observable<User>


    @GET("api.php")
    fun requestForGet(@QueryMap queryMap: Map<String, String>, @FieldMap params: Map<String, String>): Call<Response>

    @GET("api.php")
    fun loadDymaic(@QueryMap queryMap: HashMap<String, String>): Observable<Dynamic>


}