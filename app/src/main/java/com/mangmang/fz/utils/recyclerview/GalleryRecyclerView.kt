package com.mangmang.fz.utils.recyclerview

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.mangmang.fz.R

/**
 * Created by wangzhenguang on 2018/1/9.
 */
class GalleryRecyclerView :RecyclerView{


    private var FLING_SPEED = 1000 // 滑动速度

    companion object {
        val LinearySnapHelper = 0
        val PagerSnapHelper = 1
    }



    private var mScrollManager: ScrollManager? = null
    private var mDecoration: GalleryItemDecoration? = null


    constructor(context: Context):this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.gallery_recyclerview)
        val helper = ta.getInteger(R.styleable.gallery_recyclerview_helper, LinearySnapHelper)
        ta.recycle()
        attachDecoration()
        attachToRecyclerHelper(helper)
    }


    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)

        if (adapter.itemCount <= 0) {
            return
        }
        if (mScrollManager != null) {
            mScrollManager!!.updateComsume()
        }
        // 获得焦点后滑动至第0项，避免第0项的margin不对
//        smoothScrollToPosition(0)  手动设置吧
    }



    private fun attachDecoration() {
        mDecoration = GalleryItemDecoration()
        addItemDecoration(mDecoration)
    }


    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        var velocityX = velocityX
        var velocityY = velocityY
        velocityX = balancelocity(velocityX)
        velocityY = balancelocity(velocityY)
        return super.fling(velocityX, velocityY)
    }

    /**
     * 返回滑动速度值
     *
     * @param velocity
     * @return
     */
    private fun balancelocity(velocity: Int): Int {
        return if (velocity > 0) {
            Math.min(velocity, FLING_SPEED)
        } else {
            Math.max(velocity, -FLING_SPEED)
        }
    }

    /**
     * 连接RecyclerHelper
     * @param helper
     */
    private fun attachToRecyclerHelper(helper: Int) {
        mScrollManager = ScrollManager(this)
        mScrollManager!!.initSnapHelper(helper)
        mScrollManager!!.initScrollListener()
    }

    /**
     * 设置页面参数，单位dp
     *
     * @param pageMargin           默认：0dp
     * @param leftPageVisibleWidth 默认：50dp
     * @return
     */
    fun initPageParams(pageMargin: Int, leftPageVisibleWidth: Int): GalleryRecyclerView {
        GalleryItemDecoration.mPageMargin = pageMargin
        GalleryItemDecoration.mLeftPageVisibleWidth = leftPageVisibleWidth
        return this
    }

    /**
     * 设置滑动速度（像素/s）
     *
     * @param speed
     * @return
     */
    fun initFlingSpeed(speed: Int): GalleryRecyclerView {
        this.FLING_SPEED = speed
        return this
    }

    /**
     * 设置动画因子
     *
     * @param factor
     * @return
     */
    fun setAnimFactor(factor: Float): GalleryRecyclerView {
        AnimManager.getInstance().setmAnimFactor(factor)
        return this
    }

    /**
     * 设置动画类型
     *
     * @param type
     * @return
     */
    fun setAnimType(type: Int): GalleryRecyclerView {
        AnimManager.getInstance().setmAnimType(type)
        return this
    }

    /**
     * 设置点击事件
     *
     * @param mListener
     */
    fun setOnItemClickListener(mListener: OnItemClickListener): GalleryRecyclerView {
        if (mDecoration != null) {
            mDecoration!!.setOnItemClickListener(mListener)
        }
        return this
    }

    fun getOrientation(): Int {

        return if (layoutManager is LinearLayoutManager) {
            if (layoutManager is GridLayoutManager) {
                throw RuntimeException("请设置LayoutManager为LinearLayoutManager")
            } else {
                (layoutManager as LinearLayoutManager).orientation
            }
        } else {
            throw RuntimeException("请设置LayoutManager为LinearLayoutManager")
        }
    }

    fun getLinearLayoutManager(): LinearLayoutManager {
        return if (layoutManager is LinearLayoutManager) {
            if (layoutManager is GridLayoutManager) { 
                throw RuntimeException("请设置LayoutManager为LinearLayoutManager")

            } else {
                layoutManager as LinearLayoutManager
            }
        } else {
            throw RuntimeException("请设置LayoutManager为LinearLayoutManager")
        }
    }

    fun getScrolledPosition(): Int {
        return if (mScrollManager == null) {
            0
        } else {
            mScrollManager!!.getPosition()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}