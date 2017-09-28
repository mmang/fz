package com.mangmang.fz.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mangmang.fz.R
import com.mangmang.fz.adapter.base.BaseQuickAdapter
import com.mangmang.fz.adapter.base.BaseViewHolder
import com.mangmang.fz.bean.DynamicItem
import com.mangmang.fz.bean.UserAlbumItem
import com.mangmang.fz.bean.UserPhotoListDataList
import com.mangmang.fz.utils.GlideUtil

/**
 * Created by mangmang on 2017/9/21.
 */
class PhotoAdapter(layoutRes: Int, data: MutableList<UserPhotoListDataList>) : BaseQuickAdapter<UserPhotoListDataList, BaseViewHolder>(layoutRes, data) {


    override fun convert(helper: BaseViewHolder, item: UserPhotoListDataList) {
        var image = helper.getView<ImageView>(R.id.image)
        GlideUtil.loadRoundImage(mContext,image,item.thumbpic,ImageView.ScaleType.FIT_CENTER)
    }
}