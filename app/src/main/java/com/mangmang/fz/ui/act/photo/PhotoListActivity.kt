package com.mangmang.fz.ui.act.photo

import android.os.Bundle
import android.text.TextUtils
import com.happyfi.lelerong.base.BaseCommonActivity
import com.mangmang.fz.R
import com.mangmang.fz.ui.fragment.PhotoFragment
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.showToast

/**
 * Created by mangmang on 2017/9/27.
 *
 * 用户相册照片列表
 */
class PhotoListActivity : BaseCommonActivity() {

    var page = 1

    override fun getLayoutId(): Int {

        return R.layout.activity_photolist
    }

    override fun initView() {

    }


    override fun initDatas() {
        val albumId = intent.getStringExtra(Constants.ALBUM_ID)
        if (TextUtils.isEmpty(albumId)) {
            showToast("未知相册")
            finish()
        }

        val fragment = PhotoFragment()
        val bundle = Bundle()
        bundle.putString(Constants.ALBUM_ID, albumId)
        fragment.arguments = bundle
        supportDelegate.loadRootFragment(R.id.container, fragment)
    }

    override fun initlistener() {
    }

}