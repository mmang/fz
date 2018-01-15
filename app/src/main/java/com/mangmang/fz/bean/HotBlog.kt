package com.mangmang.fz.bean

/**
 * Created by wangzhenguang on 2017/12/18.
 */
data class HotBlog(
        val more: Int,
        val list: MutableList<HotBlogItem>

)


data class HotBlogItem(
        val feedid: String,
        val uid: String,
        val avatar: String,
        val username: String,
        val dateline: String,
        val hot: String,
        val id: String,
        val idtype: String,
        val subject: String,
        val title: String,
        val message: String,
        val image_1: String,
        val title_message: String
)
