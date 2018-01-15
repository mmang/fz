package com.mangmang.fz.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mangmang.fz.R
import com.mangmang.fz.bean.UserPhotoItem
import com.mangmang.fz.utils.GlideUtil

/**
 * Created by mangmang on 2017/9/21.
 */
class PhotoAdapter(layoutRes: Int, data: MutableList<UserPhotoItem>) : BaseQuickAdapter<UserPhotoItem, BaseViewHolder>(layoutRes, data) {


    override fun convert(helper: BaseViewHolder, item: UserPhotoItem) {
        var image = helper.getView<ImageView>(R.id.image)
//        GlideUtil.loadRoundImage(mContext,image,item.thumbpic,ImageView.ScaleType.CENTER_CROP)
        GlideUtil.load(mContext, item.thumbpic)
                .into(image, true)
    }
}