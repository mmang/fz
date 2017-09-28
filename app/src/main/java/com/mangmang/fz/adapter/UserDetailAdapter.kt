package com.mangmang.fz.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mangmang.fz.R
import com.mangmang.fz.adapter.base.BaseQuickAdapter
import com.mangmang.fz.adapter.base.BaseViewHolder
import com.mangmang.fz.bean.DynamicItem
import com.mangmang.fz.bean.UserDetailField

/**
 * Created by mangmang on 2017/9/21.
 */
class UserDetailAdapter(layoutRes: Int, data: MutableList<UserDetailField>) : BaseQuickAdapter<UserDetailField, BaseViewHolder>(layoutRes, data) {

    override fun convert(helper: BaseViewHolder, item: UserDetailField) {
        helper.setText(R.id.leftTitle, item.name)
        helper.setText(R.id.rightTitle, item.value)
    }
}