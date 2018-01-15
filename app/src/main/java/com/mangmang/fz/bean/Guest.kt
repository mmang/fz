package com.mangmang.fz.bean

import com.mangmang.fz.base.BaseResponse

/**
 * Created by wangzhenguang on 2018/1/5.
 */
data class Guest(val uid: String,
                 val username: String,
                 val message: String,
                 val avatar: String,
                 val big_avatar: String,
                 val lastlogin: String
)