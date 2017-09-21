package com.mangmang.fz.adapter

import android.support.annotation.LayoutRes
import android.util.SparseIntArray



/**
 * Created by mangmang on 2017/9/20.
 */
abstract class MultiTypeDelegate<T> {
    val TYPE_NOT_FOUND = -404

    private var layouts: SparseIntArray? = null
    private var autoMode: Boolean = false
    private var selfMode: Boolean = false

    constructor(layouts: SparseIntArray) {
        this.layouts = layouts
    }

    constructor() {}

    fun getDefItemViewType(data: List<T>, position: Int): Int {
        val item = data[position]
        return if (item != null) getItemType(item) else DEFAULT_VIEW_TYPE
    }

    /**
     * get the item type from specific entity.
     *
     * @param t entity
     * @return item type
     */
    protected abstract fun getItemType(t: T): Int

    fun getLayoutId(viewType: Int): Int {
        return this.layouts!!.get(viewType, TYPE_NOT_FOUND)
    }

    private fun addItemType(type: Int, @LayoutRes layoutResId: Int) {
        if (this.layouts == null) {
            this.layouts = SparseIntArray()
        }
        this.layouts!!.put(type, layoutResId)
    }

    /**
     * auto increase type vale, start from 0.
     *
     * @param layoutResIds layout id arrays
     * @return MultiTypeDelegate
     */
    fun registerItemTypeAutoIncrease(@LayoutRes vararg layoutResIds: Int): MultiTypeDelegate<*> {
        autoMode = true
        checkMode(selfMode)
        for (i in layoutResIds.indices) {
            addItemType(i, layoutResIds[i])
        }
        return this
    }

    /**
     * set your own type one by one.
     *
     * @param type        type value
     * @param layoutResId layout id
     * @return MultiTypeDelegate
     */
    fun registerItemType(type: Int, @LayoutRes layoutResId: Int): MultiTypeDelegate<*> {
        selfMode = true
        checkMode(autoMode)
        addItemType(type, layoutResId)
        return this
    }

    private fun checkMode(mode: Boolean) {
        if (mode) {
            throw RuntimeException("Don't mess two register mode")
        }
    }

    companion object {

        private val DEFAULT_VIEW_TYPE = -0xff
    }
}