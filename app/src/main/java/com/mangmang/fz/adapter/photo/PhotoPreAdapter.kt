package com.mangmang.fz.adapter.photo

import android.support.v4.view.PagerAdapter
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mangmang.fz.R
import com.mangmang.fz.bean.DynamicItem
import com.mangmang.fz.ui.act.photo.PhotoPreActivity
import com.mangmang.fz.utils.GlideUtil

/**
 * Created by wangzhenguang on 2018/1/9.
 */
class PhotoPreAdapter : BaseQuickAdapter<PhotoPreActivity.PhotoPreBean, BaseViewHolder> {

    constructor(data: MutableList<PhotoPreActivity.PhotoPreBean>) : super(R.layout.item_photo_pre, data)

    override fun convert(helper: BaseViewHolder, item: PhotoPreActivity.PhotoPreBean) {
        var avatar = helper.getView<ImageView>(R.id.ivPhoto)
        GlideUtil.load(mContext, item.imgUrl, item.thumbnailUrl)
                .into(avatar, false, ImageView.ScaleType.FIT_CENTER)
        helper.setText(R.id.imgTitle, item.title)
        if(TextUtils.isEmpty(item.title)){
            helper.setText(R.id.imgTitle, "tips:点击这里评论此图片")
        }

        helper.addOnClickListener(R.id.imgTitle)

    }


}