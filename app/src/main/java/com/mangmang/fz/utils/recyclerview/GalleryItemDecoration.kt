package com.mangmang.fz.utils.recyclerview

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.mangmang.fz.UiUtils

/**
 * Created by wangzhenguang on 2018/1/9.
 */
class GalleryItemDecoration : RecyclerView.ItemDecoration() {
    private val TAG = "GalleryItemDecoration"


    companion object {
        var mPageMargin = 0          // 每一个页面默认页边距
        var mLeftPageVisibleWidth = 50 // 中间页面左右两边的页面可见部分宽度

        var mItemComusemY = 0
        var mItemComusemX = 0
    }


    private var onItemClickListener: GalleryRecyclerView.OnItemClickListener? = null

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        Log.d(TAG, "getItemOffset() --> position = " + parent.getChildAdapterPosition(view))

        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter.itemCount

        parent.post {
            if ((parent as GalleryRecyclerView).getOrientation() === LinearLayoutManager.HORIZONTAL) {
                onSetHoritiontalParams(parent, view, position, itemCount)
            } else {
                onSetVerticalParams(parent, view, position, itemCount)
            }
        }

        view.setOnClickListener { v ->
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(v, position)
            }
        }
    }

    private fun onSetVerticalParams(parent: ViewGroup, itemView: View, position: Int, itemCount: Int) {
        val itemNewWidth = parent.width
        val itemNewHeight = parent.height - UiUtils.dip2px(4 * mPageMargin + 2 * mLeftPageVisibleWidth.toFloat())

        mItemComusemY = (itemNewHeight + UiUtils.dip2px(2f * mPageMargin)).toInt()


        Log.d("TAG", "itemNewHeight=" + itemNewHeight)

        // 适配第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
        val topMargin = if (position == 0) UiUtils.dip2px(mLeftPageVisibleWidth + 2 * mPageMargin.toFloat()) else UiUtils.dip2px(mPageMargin.toFloat())
        val bottomMargin = if (position == itemCount - 1) UiUtils.dip2px(mLeftPageVisibleWidth + 2f * mPageMargin) else UiUtils.dip2px(mPageMargin.toFloat())

        setLayoutParams(itemView, 0, topMargin.toInt(), 0, bottomMargin.toInt(), itemNewWidth, itemNewHeight.toInt())
    }

    /**
     * 设置水平滚动的参数
     *
     * @param parent
     * @param itemView
     * @param position
     * @param itemCount
     */
    private fun onSetHoritiontalParams(parent: ViewGroup, itemView: View, position: Int, itemCount: Int) {
        val itemNewWidth = parent.width - UiUtils.dip2px(4 * mPageMargin + 2f * mLeftPageVisibleWidth)
        val itemNewHeight = parent.height

        mItemComusemX = (itemNewWidth + UiUtils.dip2px(2f* mPageMargin)).toInt()

        Log.d(TAG, "onSetHoritiontalParams -->" + "parent.width=" + parent.width + ";mPageMargin=" + UiUtils.dip2px(mPageMargin.toFloat()) + ";mLeftVis=" + UiUtils.dip2px(mLeftPageVisibleWidth.toFloat()) + ";itemNewWidth=" + itemNewWidth)

        // 适配第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
        val leftMargin = if (position == 0) UiUtils.dip2px(mLeftPageVisibleWidth + 2f * mPageMargin) else UiUtils.dip2px(mPageMargin.toFloat())
        val rightMargin = if (position == itemCount - 1) UiUtils.dip2px(mLeftPageVisibleWidth + 2f * mPageMargin) else UiUtils.dip2px(mPageMargin.toFloat())

        setLayoutParams(itemView, leftMargin.toInt(), 0, rightMargin.toInt(), 0, itemNewWidth.toInt(), itemNewHeight)
    }

    /**
     * 设置参数
     *
     * @param itemView
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param itemWidth
     * @param itemHeight
     */
    private fun setLayoutParams(itemView: View, left: Int, top: Int, right: Int, bottom: Int, itemWidth: Int, itemHeight: Int) {
        val lp = itemView.layoutParams as RecyclerView.LayoutParams
        var mMarginChange = false
        var mWidthChange = false
        var mHeightChange = false

        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom)
            mMarginChange = true
        }
        if (lp.width != itemWidth) {
            lp.width = itemWidth
            mWidthChange = true
        }
        if (lp.height != itemHeight) {
            lp.height = itemHeight
            mHeightChange = true

        }

        // 因为方法会不断调用，只有在真正变化了之后才调用
        if (mWidthChange || mMarginChange || mHeightChange) {
            itemView.layoutParams = lp
        }
    }

    fun setOnItemClickListener(onItemClickListener: GalleryRecyclerView.OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}