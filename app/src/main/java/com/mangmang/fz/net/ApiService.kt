package com.mangmang.fz.net

import com.mangmang.fz.base.BaseResponse
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
    fun login(@QueryMap queryMap: Map<String, String>, @FieldMap params: Map<String, String>): Observable<BaseResponse<User>>


    @GET("api.php")
    fun requestForGet(@QueryMap queryMap: Map<String, String>, @FieldMap params: Map<String, String>): Call<Response>

    @GET("api.php")
    fun loadDymaic(@QueryMap queryMap: HashMap<String, String>): Observable<BaseResponse<PageBean<DynamicItem>>>

    // 用户信息
    @GET("api.php?m=user/user&a=getProfile")
    fun getUserProfile(@Query("uid") uid: String): Observable<UserDetail>

    //用户相册
    @GET("api.php?m=user/photo&a=getPhotoListV1")
    fun getUserPhotos(@Query("pageList") pageList: Boolean, @Query("uid") uid: String): Observable<BaseResponse<UserAlbum>>

    //获取我的记录
    @GET("api.php?m=user/user&a=getStatusByUser&pageSize=30")
    fun getStatusByUser(@Query("uid") uid: String, @Query("page") page: Int): Observable<BaseResponse<PageBean<DynamicItem>>>

    //获取我的日志
    @GET("api.php?m=user/blog&a=getBlogListByUser&pageSize=30")
    fun getBlogListByUser(@Query("uid") uid: String, @Query("page") page: Int): Observable<BaseResponse<PageBean<DynamicItem>>>

    //获取我的粉丝
    @GET("api.php?m=user/blog&a=getBlogListByUser&pageSize=30")
    fun getFans(@Query("uid") uid: String, @Query("page") page: String): Observable<String>

    //获取我的关注
    @GET("api.php?m=user/blog&a=getFollows&pageSize=30")
    fun getFollows(@Query("uid") uid: String, @Query("page") page: String): Observable<String>

    //获取我的访客
    @GET("api.php?m=search/friend&a=getBeVisited&pageSize=30")
    fun getBeVisited(@Query("uid") uid: String, @Query("page") page: String): Observable<BaseResponse<PageBean<Guest>>>


    //获取我的私信
    @GET("api.php?m=search/friend&a=getMsgUsers&pageSize=30")
    fun getBeVisited(@Query("page") page: String): Observable<String> //获取我的私信

    //获取通知列表
    @GET("api.php?m=search/friend&a=getNoticeList&pageSize=30")
    fun getNoticeList(@Query("page") page: String): Observable<String>


    //获取首页列表
    @GET("api.php?m=feed/feed&a=getFeeds&pageSize=30") //scope all/follow/zone
    fun getFeeds(@Query("scope") scope: String = "all", @Query("page") page: String): Observable<String>

    //图片列表
    @GET("api.php?m=user/photo&a=getAllPhotos&pageSize=32") // type   hot/不传为最新
    fun getAllPhotos(@Query("type") type: String = "", @Query("page") page: Int): Observable<BaseResponse<UserPhoto>>

    //相册图片列表
    @GET("api.php?m=user/photo&a=getPhotos&pageSize=36")
    fun getPhotosForAlbum(@Query("page") page: Int, @Query("id") albumid: String): Observable<BaseResponse<UserPhoto>>

    //更新地址位置
    @GET("api.php?m=user/user&a=updateLocation&pageSize=32")
    fun updateLocation(@Query("latitude") latitude: String, @Query("longitude") longitude: String): Observable<String>

    //附近的人
    @GET("api.php?m=search/friend&a=getNearUsers&pageSize=32")
    fun getNearUsers(@Query("latitude") latitude: String, @Query("longitude") longitude: String, @Query("page") page: Int): Observable<BaseResponse<PageBean<NearbyUser>>>

    //记录的评论列表
    @GET("api.php?m=user/user&a=getStatusComment&pageSize=30")
    fun getStatusComment(@Query("id") id: String, @Query("page") page: Int): Observable<BaseResponse<TopicCommentOrg>>

    //照片详情
    @GET("api.php?m=user/photo&a=getPhotoMessage")
    fun getPhotoMessage(@Query("id") id: String): Observable<BaseResponse<UserPhotoDetail>>

    //照片评论列表
    @GET("api.php?m=user/photo&a=getPhotoReply&pageSize=32")
    fun getPhotoReply(@Query("id") id: String, @Query("page") page: String): Observable<BaseResponse<Comment>>


    //日志列表
    @GET("api.php?m=user/blog&a=getHotBlogs&pageSize=32")
    fun getHotBlogs(@Query("period") period: Int = 30, @Query("page") page: Int): Observable<BaseResponse<PageBean<DynamicItem>>>

    //日志详情
    @GET("api.php?m=user/blog&a=getBlog&type=android")
    fun getBlog(@Query("blogId") blogId: String): Observable<BaseResponse<Blog>>

    //日志评论
    @GET("api.php?m=user/blog&a=getBlogReply&pageSize=32")
    fun getBlogReply(@Query("blogId") blogId: String, @Query("page") page: Int): Observable<BaseResponse<Comment>>

    //别人的空间
    @GET("api.php?m=user/user&a=getProfile&pageSize=32")
    fun getProfile(@Query("uid") uid: String): Observable<BaseResponse<Comment>>

    //写记录
    @GET("api.php?m=user/user&a=updateStatus&pageSize=32")
    fun updateStatus(@Query("message") message: String): Observable<BaseResponse<Comment>>

    //登出
    @GET("api.php?m=account/account&a=logout")
    fun logout(): Observable<BaseResponse<Comment>>

    //添加日志评论
    @GET("api.php?m=user/blog&a=addBlogReply")
    fun addBlogReply(@Query("comment") comment: String): Observable<BaseResponse<String>>


    @POST("forum.php?mod=post")
    @FormUrlEncoded
    fun reply(@HeaderMap header: Map<String, String>, @QueryMap queryMap: Map<String, String>,
              @FieldMap params: Map<String, String>): Observable<String>

    @GET("api.php")  //回复日志，记录，图片都应这个
    fun reply(@QueryMap param: Map<String, String>): Observable<BaseResponse<String>>


}