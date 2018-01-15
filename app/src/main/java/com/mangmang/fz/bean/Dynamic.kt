package com.mangmang.fz.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by mangmang on 2017/9/20.
 */
//data class Dynamic(
//
//        val feed_status: String,
//        val data: DynamicData
//)

//data class Dynamic(
//        val count: Int,
//        val more: Int,
//        val page: Int,
//        val pageSize: Int,
//        val list: List<DynamicItem>
//)

data class DynamicItem(
        val feedid: String, // 5323910
        val uid: String, // 309535
        var avatar: String, // http://center.feizan.cn/avatar/000/30/95/35_avatar_small.jpg
        val username: String, // 呼啸山庄
        val dateline: String, // 28秒前
        val time: String, // 1505874202
        val hot: String, // 0
        @SerializedName("id", alternate = arrayOf("blogid","doid"))
        val id: String, // 2559836
        var idtype: String = "", // doid
        val title: String, // 发表了记录
        val message: String, // 我来了个假星巴克？就像酒店的餐厅一样，一个大客厅的样子，里面的人说话也没点分寸，那样大声。完全没有我印象中静幽的感觉！
        val title_message: String,//
        val image_1: String,
        val image_2: String,
        val touid: Int,
        val touser: String,
        val subject: String
)
