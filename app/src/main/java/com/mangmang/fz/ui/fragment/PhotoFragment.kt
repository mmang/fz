package com.mangmang.fz.ui.fragment

import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.happyfi.lelerong.base.BaseFragment
import com.happyfi.lelerong.base.BaseInjectorFragment
import com.mangmang.fz.R
import com.mangmang.fz.adapter.PhotoAdapter
import com.mangmang.fz.net.ApiService
import com.mangmang.fz.net.requestCallBack
import com.mangmang.fz.ui.act.photo.PhotoPreActivity
import com.mangmang.fz.ui.route.Router
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.applySchedulers
import com.mangmang.fz.utils.showToast
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import kotlinx.android.synthetic.main.layout_refresh.*
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
class PhotoFragment : BaseInjectorFragment(), BaseQuickAdapter.OnItemClickListener {
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        // 进入图片预览
//        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, Pair(view, "ivPhoto"))
//        ActivityCompat.startActivity(context, Intent(context,PhotoPreActivity::class.java),options.toBundle())
        val data = photoAdapter.data?.get(position)
        /**
         * 转换预览界面的bean
         */
        val photoPreList = ArrayList<PhotoPreActivity.PhotoPreBean>()
        photoAdapter.data?.forEach {
            val photoPreBean = PhotoPreActivity.PhotoPreBean(
                    it.picid,
                    it.title,
                    it.filepath,
                    it.thumbpic
            )
            photoPreList.add(photoPreBean)
        }

        /**
         *
         */
        Router.newIntent(activity)
                .putInt(Constants.INDEX, position) //过去要首先显示的条目
                .putSerializable(Constants.ALL_PHOTO, photoPreList)
                .to(PhotoPreActivity::class.java)
                .launch()
    }

    @Inject
    lateinit var apiservice: ApiService

    private var page = 1

    private lateinit var photoAdapter: PhotoAdapter

    override fun initView() {

        photoAdapter = PhotoAdapter(R.layout.item_photo, mutableListOf())
        photoAdapter.setOnItemClickListener(this)
        recyclerView.adapter = photoAdapter
        recyclerView.layoutManager = GridLayoutManager(context, 4)
        refreshLayout.setOnRefreshListener {
            refreshLayout.isEnableLoadmore = true //刷新后默认开启加载更多
            page = 1
            initData()
        }

        refreshLayout.setOnLoadmoreListener {
            page++
            initData()
        }

    }

    override fun getLayoutResources(): Int {
        return R.layout.layout_refresh
    }

    override fun initData() {
        /**
         * 判断是主页，还是用户详情里的相册
         */
        var albumid = arguments?.getString(Constants.ALBUM_ID)

        //不是用户详情页 加载的
        if (TextUtils.isEmpty(albumid)) {
            apiservice.getAllPhotos(Constants.PHOTO_TYPE_HOT, page)
                    .applySchedulers()
                    .bindUntilEvent(this, FragmentEvent.DESTROY)
                    .requestCallBack {
                        refreshLayout.finishRefresh()
                        refreshLayout.finishLoadmore()
                        refreshLayout.isEnableLoadmore = it.data!!.more != 0
                        if (page == 1) {
                            photoAdapter.replaceData(it.data!!.list)
                        } else {
                            photoAdapter.addData(it.data!!.list)
                        }
                    }

        } else
        // 通过相册id加载图片
            apiservice.getPhotosForAlbum(page, albumid!!)
                    .applySchedulers()
                    .bindUntilEvent(this, FragmentEvent.DESTROY)
                    .requestCallBack {
                        refreshLayout.finishRefresh()
                        refreshLayout.finishLoadmore()
                        it.data?.let {
                            refreshLayout.isEnableLoadmore = it.more != 0
                            if (page == 1) {
                                photoAdapter.replaceData(it.list)
                            } else {
                                photoAdapter.addData(it.list)
                            }
                        }
                    }
    }

}