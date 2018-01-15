package com.mangmang.fz.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mangmang.fz.R
import com.mangmang.fz.bean.DynamicItem
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.GlideUtil
import org.w3c.dom.Text

/**
 * Created by mangmang on 2017/9/21.
 */
class DynamicAdapter(layoutRes: Int, data: MutableList<DynamicItem>) : BaseQuickAdapter<DynamicItem, BaseViewHolder>(layoutRes, data) {


    init {
    }

    override fun convert(helper: BaseViewHolder, item: DynamicItem) {
        helper.setText(R.id.userName, item.username)
        helper.setText(R.id.time, item.dateline)

        val tvTitle = helper.getView<TextView>(R.id.title)
        tvTitle.text = if (TextUtils.isEmpty(item.title)) item.subject else item.title
        //判断title是否有内容
        tvTitle.visibility = if (TextUtils.isEmpty(tvTitle.text)) View.GONE else View.VISIBLE

        helper.setText(R.id.textContent, item.message)

        var avatar = helper.getView<ImageView>(R.id.avatar)
        GlideUtil.loadRoundImage(mContext, avatar, item.avatar)

        val image1 = helper.getView<ImageView>(R.id.image1)
        val image2 = helper.getView<ImageView>(R.id.image2)
        image1.visibility = View.GONE
        image2.visibility = View.GONE

        val textContent = helper.getView<View>(R.id.textContent)
        textContent.visibility = if (TextUtils.isEmpty(item.message)) View.GONE else View.VISIBLE

        if (item.idtype != null)
            when (item.idtype) {
                Constants.BLOG_TYPE_ID -> {
                }
                Constants.ALBUM_TYPE_ID -> {
                    GlideUtil.load(mContext, item.image_1)
                            .into(image1)
                    image1.visibility = View.VISIBLE

                    GlideUtil.load(mContext, item.image_2)
                            .into(image2)
                    image2.visibility = if (TextUtils.isEmpty(item.image_2)) View.INVISIBLE else View.VISIBLE
                }
                Constants.DO_TYPE_ID -> ""
                Constants.PIC_TYPE_ID -> {
                    textContent.visibility = View.GONE
                    GlideUtil.load(mContext, item.image_1)
                            .into(image1)
                    image1.visibility = View.VISIBLE
                    image2.visibility = View.INVISIBLE
                }
            }

        item.avatar?.let {
            helper.addOnClickListener(R.id.avatar)
        }
    }
}