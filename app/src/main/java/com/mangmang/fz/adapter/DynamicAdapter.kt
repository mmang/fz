package com.mangmang.fz.adapter

import android.opengl.Visibility
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mangmang.fz.R
import com.mangmang.fz.adapter.base.BaseQuickAdapter
import com.mangmang.fz.adapter.base.BaseViewHolder
import com.mangmang.fz.bean.DynamicItem
import com.mangmang.fz.utils.GlideUtil

/**
 * Created by mangmang on 2017/9/21.
 */
class DynamicAdapter(layoutRes: Int, data: MutableList<DynamicItem>) : BaseQuickAdapter<DynamicItem, BaseViewHolder>(layoutRes, data) {

    val blogid = "blogid" //评论日志
    val albumid = "albumid" //更新相册
    val doid = "doid"// 发表记录
    val picid = "picid" //评论图片

    init {
    }

    override fun convert(helper: BaseViewHolder, item: DynamicItem) {
        helper.setText(R.id.userName, item.username)
        helper.setText(R.id.time, item.dateline)
        helper.setText(R.id.title, item.title)
        helper.setText(R.id.textContent, item.message)

        var avatar = helper.getView<ImageView>(R.id.avatar)
        GlideUtil.loadRoundImage(mContext, avatar, item.avatar)

        val image1 = helper.getView<ImageView>(R.id.image1)
        val image2 = helper.getView<ImageView>(R.id.image2)
        image1.visibility = View.GONE
        image2.visibility = View.GONE

        val textContent = helper.getView<View>(R.id.textContent)
        textContent.visibility = View.VISIBLE

        when (item.idtype) {

            blogid -> ""
            albumid -> {
                GlideUtil.loadImage(mContext, image1, item.image_1)
                image1.visibility = View.VISIBLE

                GlideUtil.loadImage(mContext, image2, item.image_2)
                image2.visibility = if (TextUtils.isEmpty(item.image_2)) View.INVISIBLE else View.VISIBLE
            }
            doid -> ""
            picid -> textContent.visibility = View.GONE
        }
        helper.addOnClickListener(R.id.avatar)
        helper.addOnClickListener(R.id.textContent)
        helper.addOnClickListener(R.id.title)

    }
}