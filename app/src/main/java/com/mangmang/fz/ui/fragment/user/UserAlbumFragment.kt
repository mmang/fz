package com.mangmang.fz.ui.fragment.user

import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.happyfi.lelerong.base.BaseFragment
import com.happyfi.lelerong.base.BaseInjectorFragment
import com.mangmang.fz.R
import com.mangmang.fz.adapter.UserAlbumAdapter
import com.mangmang.fz.net.ApiService
import com.mangmang.fz.net.requestCallBack
import com.mangmang.fz.ui.act.photo.PhotoListActivity
import com.mangmang.fz.ui.route.Router
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.applySchedulers
import kotlinx.android.synthetic.main.layout_refresh.*
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/19.
 */
class UserAlbumFragment : BaseInjectorFragment(), BaseQuickAdapter.OnItemClickListener {

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
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        userAlbumAdapter = UserAlbumAdapter(R.layout.item_album, mutableListOf())
        recyclerView.adapter = userAlbumAdapter
        userAlbumAdapter.setOnItemClickListener(this)
        refreshLayout.isEnableRefresh = false
    }

    override fun getLayoutResources(): Int {
        return R.layout.layout_refresh
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
                .requestCallBack {
                    it.data?.let {
                        refreshLayout.isEnableLoadmore = it.more != 0
                        userAlbumAdapter.addData(it.list)
                    }
                }
    }

}