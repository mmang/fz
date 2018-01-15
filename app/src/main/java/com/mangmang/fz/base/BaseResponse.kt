package com.mangmang.fz.base

import com.google.gson.annotations.SerializedName

/**
 * Created by mangmang on 2017/9/20.
 */
class BaseResponse<T> {
    @SerializedName("status", alternate =
    arrayOf("photo_status", "search_status", "auth_status", "account_status", "feed_status"))
    var status: Int = 0
    var data: T? = null
    var message: String = ""
    val error: String = ""
}