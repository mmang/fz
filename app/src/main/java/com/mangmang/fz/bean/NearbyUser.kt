package com.mangmang.fz.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by wangzhenguang on 2017/12/21.
 */
data class NearbyUser(
        val uid: String,
        val username: String,
        @SerializedName("resideprovince") val location: String,//
        val avatar: String,
        val big_avatar: String,
        val distance: String
)