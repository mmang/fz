package com.mangmang.fz.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mangmang.fz.R
import com.mangmang.fz.bean.NearbyUser
import com.mangmang.fz.utils.GlideUtil

/**
 * Created by mangmang on 2017/9/21.
 */
class NearbyUserAdapter(layoutRes: Int, data: MutableList<NearbyUser>) : BaseQuickAdapter<NearbyUser, BaseViewHolder>(layoutRes, data) {


    init {
    }

    override fun convert(helper: BaseViewHolder, item: NearbyUser) {
        helper.setText(R.id.tvName, item.username)
        helper.setText(R.id.tvDistance, item.distance)
        val avatar = helper.getView<ImageView>(R.id.ivAvatar)

        GlideUtil.load(mContext, item.avatar)
                .into(avatar, true)

    }
}