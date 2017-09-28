package com.mangmang.fz.ui.fragment

import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.mangmang.fz.R
import com.mangmang.fz.adapter.PhotoAdapter
import com.mangmang.fz.adapter.base.BaseQuickAdapter
import com.mangmang.fz.bean.UserAlbumItem
import com.mangmang.fz.bean.UserPhotoListDataList
import com.mangmang.fz.net.ApiService
import com.mangmang.fz.ui.act.photo.PhotoPreActivity
import com.mangmang.fz.ui.route.Router
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.UserManager
import com.mangmang.fz.utils.applySchedulers
import com.mangmang.fz.utils.showToast
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import kotlinx.android.synthetic.main.fragment_photo.*
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/19.
 * 懒得写 p层了
 *
 * 用户详情里
 *                  共用此fragment
 * 首页照片里
 *
 *
 *
 *
 */
class PhotoFragment : BaseFragment(), BaseQuickAdapter.OnItemClickListener {
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        // 进入图片预览
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, "ivPhoto")
//        ActivityCompat.startActivity(context, Intent(context,PhotoPreActivity::class.java),options.toBundle())
        val data = photoAdapter.getData()?.get(position)
        Router.newIntent(activity)
                .putString(Constants.PHOTO_URL, data!!.filepath)
                .options(options)
                .to(PhotoPreActivity::class.java)
                .launch()
    }

    @Inject
    lateinit var apiservice: ApiService

    private var page = 1

    private lateinit var photoAdapter: PhotoAdapter

    override fun initView() {
        recyclerView.layoutManager = GridLayoutManager(context, 4)
        photoAdapter = PhotoAdapter(R.layout.item_photo, mutableListOf())
        recyclerView.adapter = photoAdapter
        photoAdapter.setOnItemClickListener(this)
    }

    override fun getLayoutResources(): Int {
        return R.layout.fragment_photo
    }

    override fun initData() {
        super.initData()
        /**
         * 判断是主页，还是用户详情里的相册
         */
        var albumid = arguments?.getString(Constants.ALBUM_ID)

        //不是用户详情页 加载的
        if (TextUtils.isEmpty(albumid)) {
            apiservice.getAllPhotos(Constants.PHOTO_TYPE_HOT, page)
                    .applySchedulers()
                    .subscribe({
                        photoAdapter.addData(it.data.list)
                    }, {
                        showToast(it.message.toString())
                    })
            return
        }

        // 通过相册id加载图片
        apiservice.getPhotosForAlbum(page, albumid!!)
                .applySchedulers()
                .bindUntilEvent(this, FragmentEvent.DESTROY)
                .subscribe({
                    photoAdapter.addData(it.data.list)
                }, {

                })


    }

}