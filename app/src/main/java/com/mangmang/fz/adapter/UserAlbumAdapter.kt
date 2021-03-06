package com.mangmang.fz.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mangmang.fz.R
import com.mangmang.fz.bean.UserAlbumItem
import com.mangmang.fz.utils.GlideUtil

/**
 * Created by mangmang on 2017/9/21.
 */
class UserAlbumAdapter(layoutRes: Int, data: MutableList<UserAlbumItem>) : BaseQuickAdapter<UserAlbumItem, BaseViewHolder>(layoutRes, data) {


    override fun convert(helper: BaseViewHolder, item: UserAlbumItem) {
        var image = helper.getView<ImageView>(R.id.ivAlumb)
        GlideUtil.load(mContext, item.thumbpic)
                .into(image)
        helper.setText(R.id.albumTitle, item.albumname)
        helper.setText(R.id.photoCount, item.picnum)
        helper.setText(R.id.updateTime, item.updatetime)
    }
}