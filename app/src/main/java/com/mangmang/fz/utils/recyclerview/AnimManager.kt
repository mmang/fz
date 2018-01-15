package com.mangmang.fz.utils.recyclerview

import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Created by wangzhenguang on 2018/1/9.
 */
class AnimManager {


    private var mAnimType = ANIM_BOTTOM_TO_TOP //动画类型
    private var mAnimFactor = 0.2f   //变化因子

    companion object {
        var INSTANCE: AnimManager? = null
        val ANIM_BOTTOM_TO_TOP = 0
        val ANIM_TOP_TO_BOTTOM = 1
        fun getInstance(): AnimManager {
            if (INSTANCE == null) {
                INSTANCE = AnimManager()
            }
            return INSTANCE!!
        }
    }


    fun setAnimation(recyclerView: RecyclerView, position: Int, percent: Float) {
        when (mAnimType) {
            ANIM_BOTTOM_TO_TOP -> setBottomToTopAnim(recyclerView, position, percent)
            ANIM_TOP_TO_BOTTOM -> setTopToBottomAnim(recyclerView, position, percent)
            else -> setBottomToTopAnim(recyclerView, position, percent)
        }
    }


    /**
     * 从下到上的动画效果
     *
     * @param recyclerView
     * @param position
     * @param percent
     */
    private fun setBottomToTopAnim(recyclerView: RecyclerView, position: Int, percent: Float) {
        val mCurView = recyclerView.layoutManager.findViewByPosition(position)       // 中间页
        val mRightView = recyclerView.layoutManager.findViewByPosition(position + 1) // 左边页
        val mLeftView = recyclerView.layoutManager.findViewByPosition(position - 1)  // 右边页


        if (percent <= 0.5) {
            if (mLeftView != null) {
                Log.d("TAG", "mLeftView=" + (1 - mAnimFactor + percent * mAnimFactor) + "; position=" + position)
                mLeftView.scaleX = 1 - mAnimFactor + percent * mAnimFactor
                mLeftView.scaleY = 1 - mAnimFactor + percent * mAnimFactor
            }
            if (mCurView != null) {
                Log.d("TAG", "mCurView=" + (1 - percent * mAnimFactor) + "; position=" + position)
                mCurView.scaleX = 1 - percent * mAnimFactor
                mCurView.scaleY = 1 - percent * mAnimFactor
            }
            if (mRightView != null) {
                Log.d("TAG", "mRightView=" + (1 - mAnimFactor + percent * mAnimFactor) + "; position=" + position)
                mRightView.scaleX = 1 - mAnimFactor + percent * mAnimFactor
                mRightView.scaleY = 1 - mAnimFactor + percent * mAnimFactor
            }
        } else {
            if (mLeftView != null) {
                Log.d("TAG", "mLeftView=" + (1 - percent * mAnimFactor) + "; position=" + position)
                mLeftView.scaleX = 1 - percent * mAnimFactor
                mLeftView.scaleY = 1 - percent * mAnimFactor
            }
            if (mCurView != null) {
                Log.d("TAG", "mCurView=" + (1 - mAnimFactor + percent * mAnimFactor) + "; position=" + position)
                mCurView.scaleX = 1 - mAnimFactor + percent * mAnimFactor
                mCurView.scaleY = 1 - mAnimFactor + percent * mAnimFactor
            }
            if (mRightView != null) {
                Log.d("TAG", "mRightView=" + (1 - percent * mAnimFactor) + "; position=" + position)
                mRightView.scaleX = 1 - percent * mAnimFactor
                mRightView.scaleY = 1 - percent * mAnimFactor
            }
        }
    }


    /***
     * 从上到下的效果
     * @param recyclerView
     * @param position
     * @param percent
     */
    private fun setTopToBottomAnim(recyclerView: RecyclerView, position: Int, percent: Float) {
        val mCurView = recyclerView.layoutManager.findViewByPosition(position)       // 中间页
        val mRightView = recyclerView.layoutManager.findViewByPosition(position + 1) // 左边页
        val mLeftView = recyclerView.layoutManager.findViewByPosition(position - 1)  // 右边页

        if (percent <= 0.5) {
            if (mLeftView != null) {
                mLeftView.scaleX = 1 - percent * mAnimFactor
                mLeftView.scaleY = 1 - percent * mAnimFactor
            }
            if (mCurView != null) {
                mCurView.scaleX = 1 - mAnimFactor + percent * mAnimFactor
                mCurView.scaleY = 1 - mAnimFactor + percent * mAnimFactor
            }
            if (mRightView != null) {
                mRightView.scaleX = 1 - percent * mAnimFactor
                mRightView.scaleY = 1 - percent * mAnimFactor
            }

        } else {
            if (mLeftView != null) {
                mLeftView.scaleX = 1 - mAnimFactor + percent * mAnimFactor
                mLeftView.scaleY = 1 - mAnimFactor + percent * mAnimFactor
            }
            if (mCurView != null) {
                mCurView.scaleX = 1 - percent * mAnimFactor
                mCurView.scaleY = 1 - percent * mAnimFactor
            }
            if (mRightView != null) {
                mRightView.scaleX = 1 - mAnimFactor + percent * mAnimFactor
                mRightView.scaleY = 1 - mAnimFactor + percent * mAnimFactor
            }
        }
    }

    fun setmAnimFactor(mAnimFactor: Float) {
        this.mAnimFactor = mAnimFactor
    }

    fun setmAnimType(mAnimType: Int) {
        this.mAnimType = mAnimType
    }
}