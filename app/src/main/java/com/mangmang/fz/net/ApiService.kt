package com.mangmang.fz.net

import com.mangmang.fz.bean.*
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

    // 用户信息
    @GET("api.php?m=user/user&a=getProfile")
    fun getUserProfile(@Query("uid") uid: String): Observable<UserDetail>

    //用户相册
    @GET("api.php?m=user/photo&a=getPhotoListV1")
    fun getUserPhotos(@Query("pageList") pageList: Boolean, @Query("uid") uid: String): Observable<UserAlbums>


    @GET("api.php?m=user/photo&a=getAllPhotos&pageSize=36")
    fun getAllPhotos(@Query("type") type: String, @Query("page") page: Int):Observable<UserPhotoList>

    @GET("api.php?m=user/photo&a=getPhotos&pageSize=36")
    fun getPhotosForAlbum(@Query("page") page: Int, @Query("id") albumid: String):Observable<UserPhotoList>

}