package com.mangmang.fz.utils.recyclerview

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.mangmang.fz.UiUtils

/**
 * Created by wangzhenguang on 2018/1/9.
 */
class ScrollManager(private val mGalleryRecyclerView:GalleryRecyclerView){

    private var mLinearySnapHelper: LinearSnapHelper? = null
    private var mPagerSnapHelper: PagerSnapHelper? = null

    private var mPosition = 0

    // 使偏移量为左边距 + 左边Item的可视部分宽度
    private var mConsumeX = 0
    private var mConsumeY = 0


    private val SLIDE_LEFT = 1    // 左滑
    private val SLIDE_RIGHT = 2   // 右滑
    private val SLIDE_TOP = 3     // 上滑
    private val SLIDE_BOTTOM = 4  // 下滑

    // 滑动方向
    private var slideDirct = SLIDE_RIGHT

    /**
     * 初始化SnapHelper
     *
     * @param helper
     */
    fun initSnapHelper(helper: Int) {
        when (helper) {
            GalleryRecyclerView.LinearySnapHelper -> {
                mLinearySnapHelper = LinearSnapHelper()
                mLinearySnapHelper!!.attachToRecyclerView(mGalleryRecyclerView)
            }
            GalleryRecyclerView.PagerSnapHelper -> {
                mPagerSnapHelper = PagerSnapHelper()
                mPagerSnapHelper!!.attachToRecyclerView(mGalleryRecyclerView)
            }
        }
    }

    /**
     * 监听RecyclerView的滑动
     */
    fun initScrollListener() {
        val mScrollerListener = GalleryScrollerListener()
        mGalleryRecyclerView.addOnScrollListener(mScrollerListener)
    }

    fun updateComsume() {
        mConsumeX += UiUtils.dip2px(GalleryItemDecoration.mLeftPageVisibleWidth + GalleryItemDecoration.mPageMargin * 2f).toInt()
        mConsumeY += UiUtils.dip2px(GalleryItemDecoration.mLeftPageVisibleWidth + GalleryItemDecoration.mPageMargin * 2f).toInt()

    }


    internal inner class GalleryScrollerListener : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (mGalleryRecyclerView.getOrientation() === LinearLayoutManager.HORIZONTAL) {
                onHoritiontalScroll(recyclerView, dx)
            } else {
                onVerticalScroll(recyclerView, dy)
            }
        }
    }

    /**
     * 垂直滑动
     *
     * @param recyclerView
     * @param dy
     */
    private fun onVerticalScroll(recyclerView: RecyclerView?, dy: Int) {
        mConsumeY += dy

        if (dy > 0) {
            slideDirct = SLIDE_BOTTOM
        } else {
            slideDirct = SLIDE_TOP
        }

        // 让RecyclerView测绘完成后再调用，避免GalleryAdapterHelper.mItemHeight的值拿不到
        recyclerView!!.post(Runnable {
            val shouldConsumeY = GalleryItemDecoration.mItemComusemY
            // 获取当前的位置
            val position = getPosition(mConsumeY, shouldConsumeY)
            val offset = mConsumeY.toFloat() / shouldConsumeY.toFloat()     // 位置浮点值（即总消耗距离 / 每一页理论消耗距离 = 一个浮点型的位置值）
            // 避免offset值取整时进一，从而影响了percent值
            if (offset >= mGalleryRecyclerView.getLinearLayoutManager().findFirstVisibleItemPosition() + 1 && slideDirct == SLIDE_BOTTOM) {
                return@Runnable
            }
            // 获取当前页移动的百分值
            val percent = offset - offset.toInt()

            Log.d("TAG", "offset=$offset; mConsumeY=$mConsumeY; shouldConsumeY=$shouldConsumeY")


            // 设置动画变化
            AnimManager.getInstance().setAnimation(recyclerView, position, percent)
        })
    }

    /**
     * 水平滑动
     *
     * @param recyclerView
     * @param dx
     */
    private fun onHoritiontalScroll(recyclerView: RecyclerView?, dx: Int) {
        mConsumeX += dx

        if (dx > 0) {
            // 右滑
            slideDirct = SLIDE_RIGHT
        } else {
            // 左滑
            slideDirct = SLIDE_LEFT
        }

        // 让RecyclerView测绘完成后再调用，避免GalleryAdapterHelper.mItemWidth的值拿不到
        recyclerView!!.post(Runnable {
            val shouldConsumeX = GalleryItemDecoration.mItemComusemX
            // 获取当前的位置
            val position = getPosition(mConsumeX, shouldConsumeX)

            val offset = mConsumeX.toFloat() / shouldConsumeX.toFloat()     // 位置浮点值（即总消耗距离 / 每一页理论消耗距离 = 一个浮点型的位置值）
            Log.d("TAG", "offset=$offset; mConsumeX=$mConsumeX; shouldConsumeX=$shouldConsumeX")

            // 避免offset值取整时进一，从而影响了percent值
            if (offset >= mGalleryRecyclerView.getLinearLayoutManager().findFirstVisibleItemPosition() + 1 && slideDirct == SLIDE_RIGHT) {
                return@Runnable
            }

            // 获取当前页移动的百分值
            val percent = offset - offset.toInt()


            // 设置动画变化
            AnimManager.getInstance().setAnimation(recyclerView, position, percent)
        })

    }


    /**
     * 获取位置
     *
     * @param mConsumeX      实际消耗距离
     * @param shouldConsumeX 理论消耗距离
     * @return
     */
    private fun getPosition(mConsumeX: Int, shouldConsumeX: Int): Int {
        val offset = mConsumeX.toFloat() / shouldConsumeX.toFloat()
        val position = Math.round(offset)        // 四舍五入获取位置
        mPosition = position
        return position
    }

    fun getPosition(): Int {
        return mPosition
    }
}