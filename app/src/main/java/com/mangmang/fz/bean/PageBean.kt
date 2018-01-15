package com.mangmang.fz.bean

import com.google.gson.annotations.SerializedName
import com.mangmang.fz.base.BaseResponse

/**
 * Created by wangzhenguang on 2017/12/21.
 */


data class PageBean<K>(
        val count: String,
        val more: Int,
        val page: Int,
        val pageSize: Int,
        @SerializedName("list", alternate = arrayOf("user"))
        val list: MutableList<K>
)