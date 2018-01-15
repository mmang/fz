package com.mangmang.fz.adapter.user

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mangmang.fz.R
import com.mangmang.fz.bean.Guest
import com.mangmang.fz.utils.GlideUtil

/**
 * Created by wangzhenguang on 2018/1/11.
 */
class GuestAdapter : BaseQuickAdapter<Guest, BaseViewHolder> {

    constructor(data: MutableList<Guest>) : super(R.layout.item_guest, data)

    override fun convert(helper: BaseViewHolder, item: Guest) {
        helper.setText(R.id.userName, item.username)
        helper.setText(R.id.time, item.lastlogin)

        val tvContent = helper.getView<TextView>(R.id.textContent)
        tvContent.text = item.message
        tvContent.visibility = if (TextUtils.isEmpty(tvContent.text)) View.GONE else View.VISIBLE

        var avatar = helper.getView<ImageView>(R.id.avatar)
        GlideUtil.loadRoundImage(mContext, avatar, item.avatar)
    }

}