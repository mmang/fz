package com.mangmang.fz.ui.act.photo

import com.mangmang.fz.R
import com.mangmang.fz.base.BaseCommonActivity
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.GlideUtil
import kotlinx.android.synthetic.main.activity_photopre.*

/**
 * Created by mangmang on 2017/9/28.
 */
class PhotoPreActivity : BaseCommonActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_photopre
    }

    override fun initDatas() {
        val photoUrl = intent.getStringExtra(Constants.PHOTO_URL)
        GlideUtil.loadImage(this, ivPhoto, photoUrl)
    }

    override fun initlistener() {

    }

}