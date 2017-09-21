package com.mangmang.fz.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mangmang.fz.R
import com.mangmang.fz.adapter.base.BaseQuickAdapter
import com.mangmang.fz.adapter.base.BaseViewHolder
import com.mangmang.fz.bean.DynamicItem

/**
 * Created by mangmang on 2017/9/21.
 */
class DynamicAdapter(layoutRes: Int, data: MutableList<DynamicItem>) : BaseQuickAdapter<DynamicItem, BaseViewHolder>(layoutRes, data) {

    val blogid = "blogid" //评论日志
    val albumid = "albumid" //更新相册
    val doid = "doid"// 发表记录
    val picid = "picid" //评论图片


    override fun convert(helper: BaseViewHolder, item: DynamicItem) {
        helper.setText(R.id.userName, item.username)
        helper.setText(R.id.time, item.dateline)
        helper.setText(R.id.title, item.title)
        helper.setText(R.id.textContent, item.message)
        var avatar = helper.getView<ImageView>(R.id.avatar)
        Glide.with(mContext)
                .load(item.avatar)
                .placeholder(R.mipmap.ic_launcher)
                .into(avatar)
        val image1 = helper.getView<ImageView>(R.id.image1)
        val image2 = helper.getView<ImageView>(R.id.image2)
        image1.visibility = View.GONE
        image2.visibility = View.GONE
        when (item.idtype) {
            blogid -> ""
            albumid -> {
                Glide.with(mContext)
                        .load(item.image_1)
                        .into(image1)
                image1.visibility = View.VISIBLE
                Glide.with(mContext)
                        .load(item.image_2)
                        .into(image2)
                image2.visibility = if (image2 == null) View.INVISIBLE else View.VISIBLE
            }
            doid -> ""
            picid -> helper.getView<View>(R.id.textContent).visibility = View.GONE
        }
        helper.addOnClickListener(R.id.avatar)

    }
}