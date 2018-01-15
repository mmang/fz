package com.mangmang.fz.ui.act.photo

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Image
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.happyfi.lelerong.base.BaseCommonActivity
import com.mangmang.fz.R
import com.mangmang.fz.SystemBarUtils
import com.mangmang.fz.adapter.photo.PhotoPreAdapter
import com.mangmang.fz.ui.act.TopicDetailActivity
import com.mangmang.fz.ui.route.Router
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.GlideUtil
import com.mangmang.fz.utils.StatusBarUtils
import com.mangmang.fz.utils.recyclerview.AnimManager
import com.mangmang.fz.utils.recyclerview.GalleryRecyclerView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_photopre.*
import kotlinx.android.synthetic.main.activity_welcome.*
import java.io.Serializable

/**
 * Created by mangmang on 2017/9/28.
 */
class PhotoPreActivity : BaseCommonActivity(), BaseQuickAdapter.OnItemChildClickListener {
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        val photoPreBean = this.adapter.data[position]
        Router.newIntent(this)
                .to(TopicDetailActivity::class.java)
                .putString(Constants.TOPIC_ID, photoPreBean.id)
                .putString(Constants.ID_TYPE, Constants.PIC_TYPE_ID)
                .launch()
    }

    private val adapter: PhotoPreAdapter by lazy { PhotoPreAdapter(mutableListOf()) }

    override fun initView() {
        titleBar.setBackgroundResource(R.color.black)
        titleBar.visibility = View.GONE
        titleUnderLine.visibility = View.GONE
    }

    override fun setStatuBar() {
        SystemBarUtils.fullScreen(window)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_photopre
    }

    override fun initDatas() {
//        val photoUrl = intent.getStringExtra(Constants.PHOTO_URL)
//        val photoThumbnailUrl = intent.getStringExtra(Constants.PHOTO_THUMBNAIL_URL)
////        GlideUtil.loadImage(this, photoUrl, ivPhoto, ImageView.ScaleType.FIT_CENTER)
//
//        GlideUtil.load(this, photoUrl, photoThumbnailUrl)
//                .into(ivPhoto, false, ImageView.ScaleType.FIT_CENTER)
        val photoData = intent.getSerializableExtra(Constants.ALL_PHOTO) as? ArrayList<PhotoPreBean>
        val index = intent.getIntExtra(Constants.INDEX, 0)
        photoData?.let {
            adapter.onItemChildClickListener = this
            galleryRV.adapter = adapter
            galleryRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            galleryRV.initFlingSpeed(15000)                                   // 设置滑动速度（像素/s）
                    .initPageParams(0, 0)     // 设置页边距和左右图片的可见宽度，单位dp
                    .setAnimFactor(0f)                                   // 设置切换动画的参数因子
                    .setAnimType(AnimManager.ANIM_TOP_TO_BOTTOM)            // 设置切换动画类型，目前有AnimManager.ANIM_BOTTOM_TO_TOP和目前有AnimManager.ANIM_TOP_TO_BOTTOM
//                    .setOnItemClickListener(this)                            // 设置点击事件
            adapter.setNewData(photoData)
        }


        galleryRV.scrollToPosition(index) //设置第一次显示的index 不需要动画
    }

    override fun initlistener() {

    }

    data class PhotoPreBean(
            val id: String,
            val title: String,
            val imgUrl: String,
            val thumbnailUrl: String
    ) : Serializable

}

