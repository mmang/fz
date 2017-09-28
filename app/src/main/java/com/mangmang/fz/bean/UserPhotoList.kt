package com.mangmang.fz.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by mangmang on 2017/9/27.
 */

data class UserPhotoList(
		@SerializedName("photo_status") val photoStatus: Int, //1
		@SerializedName("data") val data: UserPhotoListData
)

data class UserPhotoListData(
		@SerializedName("count") val count: String, //2
		@SerializedName("more") val more: Int, //0
		@SerializedName("page") val page: String, //1
		@SerializedName("pageSize") val pageSize: String, //30
		@SerializedName("list") val list: List<UserPhotoListDataList>
)

data class UserPhotoListDataList(
		@SerializedName("picid") val picid: String, //1622830
		@SerializedName("albumid") val albumid: String, //169048
		@SerializedName("topicid") val topicid: String, //0
		@SerializedName("uid") val uid: String, //2245058
		@SerializedName("username") val username: String, //风吹云走
		@SerializedName("dateline") val dateline: String, //1月14日
		@SerializedName("postip") val postip: String, //0
		@SerializedName("filename") val filename: String, //IMG_20160114_235015.jpg
		@SerializedName("title") val title: String, //明天跑这么多银行心好累(?;︵;`)
		@SerializedName("type") val type: String, //application/octet-st
		@SerializedName("size") val size: String, //152686
		@SerializedName("filepath") val filepath: String, //http://www.feizan.cn/attachment/201601/14/2245058_1452786615TBUi.jpg
		@SerializedName("thumb") val thumb: String, //1
		@SerializedName("remote") val remote: String, //0
		@SerializedName("hot") val hot: String, //0
		@SerializedName("click_6") val click6: String, //0
		@SerializedName("click_7") val click7: String, //0
		@SerializedName("click_9") val click9: String, //0
		@SerializedName("magicframe") val magicframe: String, //0
		@SerializedName("click_16") val click16: String, //0
		@SerializedName("click_18") val click18: String, //0
		@SerializedName("click_21") val click21: String, //0
		@SerializedName("click_32") val click32: String, //0
		@SerializedName("thumbpic") val thumbpic: String //http://www.feizan.cn/attachment/201601/14/2245058_1452786615TBUi.jpg.thumb.jpg
)