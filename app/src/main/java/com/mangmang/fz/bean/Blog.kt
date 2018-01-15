package com.mangmang.fz.bean

/**
 * Created by wangzhenguang on 2017/12/13.
 */
data class Blog(
        val blogid: String,
        val username: String,
        val dateline: String,
        val pic: String, //日志里的图片
        val avatar: String,
        val subject: String,
        val message: String,
        val uid: String
)