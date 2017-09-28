package com.mangmang.fz.bean
import com.google.gson.annotations.SerializedName



data class UserAlbums(
		@SerializedName("status") val status: Int, //1
		@SerializedName("data") val data: UserAlbumData
)

data class UserAlbumData(
		@SerializedName("count") val count: String, //8
		@SerializedName("more") val more: Int, //0
		@SerializedName("page") val page: Int, //
		@SerializedName("pageSize") val pageSize: Int, //20
		@SerializedName("list") val list: List<UserAlbumItem>
)

data class UserAlbumItem(

		@SerializedName("albumid") val albumid: String, //169318
		@SerializedName("albumname") val albumname: String, //Youwei～
		@SerializedName("uid") val uid: String, //495022
		@SerializedName("username") val username: String, //YouweiQIU
		@SerializedName("dateline") val dateline: String, //1月31日
		@SerializedName("updatetime") val updatetime: String, //15分钟前
		@SerializedName("picnum") val picnum: String, //394
		@SerializedName("pic") val pic: String, //http://www.feizan.cn/attachment/201709/26/495022_1506401567oYGt.jpeg.thumb.jpg
		@SerializedName("picflag") val picflag: String, //1
		@SerializedName("friend") val friend: String, //0
		@SerializedName("password") val password: String, //false
		@SerializedName("target_ids") val targetIds: String,
		@SerializedName("thumbpic") val thumbpic: String ,//http://www.feizan.cn/attachment/201709/26/495022_1506401567oYGt.jpeg.thumb.jpg
		val filepath :String,
		val picid:String,
		val topicid:String
)

