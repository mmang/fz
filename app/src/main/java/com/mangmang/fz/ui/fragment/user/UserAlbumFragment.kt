package com.mangmang.fz.ui.fragment.user

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.mangmang.fz.R
import com.mangmang.fz.adapter.PhotoAdapter
import com.mangmang.fz.adapter.UserAlbumAdapter
import com.mangmang.fz.adapter.base.BaseQuickAdapter
import com.mangmang.fz.net.ApiService
import com.mangmang.fz.ui.act.photo.PhotoListActivity
import com.mangmang.fz.ui.fragment.BaseFragment
import com.mangmang.fz.ui.route.Router
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.UserManager
import com.mangmang.fz.utils.applySchedulers
import com.mangmang.fz.utils.showToast
import kotlinx.android.synthetic.main.fragment_photo.*
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/19.
 */
class UserAlbumFragment : BaseFragment(), BaseQuickAdapter.OnItemClickListener {

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
//        UserPhotosItem
        var photoItem = userAlbumAdapter.getData()!!.get(position)
        Router.newIntent(activity)
                .to(PhotoListActivity::class.java)
                .putString(Constants.ALBUM_ID, photoItem.albumid)
                .launch()
    }

    @Inject
    lateinit var apiservice: ApiService

    private lateinit var userAlbumAdapter: UserAlbumAdapter


    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        userAlbumAdapter = UserAlbumAdapter(R.layout.item_album, mutableListOf())
        recyclerView.adapter = userAlbumAdapter
        userAlbumAdapter.setOnItemClickListener(this)
    }

    override fun getLayoutResources(): Int {
        return R.layout.fragment_photo
    }

    override fun initData() {
        super.initData()
        var uid = arguments?.getString(Constants.USER_ID)
        if (TextUtils.isEmpty(uid)) {
            showError("未知用户")
            return
        }
        apiservice.getUserPhotos(false, uid!!)
                .applySchedulers()
                .subscribe({
                    userAlbumAdapter.addData(it.data.list)
                }, {
                    showToast(it.message.toString())
                })
    }

}