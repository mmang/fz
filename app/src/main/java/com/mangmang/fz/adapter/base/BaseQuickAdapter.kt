package com.mangmang.fz.adapter.base

import android.animation.Animator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.view.animation.LinearInterpolator
import com.mangmang.fz.adapter.animation.AlphaInAnimation
import com.mangmang.fz.adapter.animation.BaseAnimation
import android.support.annotation.*
import android.support.annotation.IntRange
import android.view.View
import android.view.ViewGroup
import com.mangmang.fz.adapter.MultiTypeDelegate
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.annotation.IdRes
import com.mangmang.fz.adapter.IExpandable
import android.support.v7.widget.GridLayoutManager


/**
 * Created by mangmang on 2017/9/20.
 */
abstract class BaseQuickAdapter<T, K : BaseViewHolder> : RecyclerView.Adapter<K> {

    enum class AnimationType {
        ALPHAIN, SCALEIN, SLIDEIN_BOTTOM, SLIDEIN_LEFT, SLIDEIN_RIGHT
    }


    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null
    private var mOnItemChildClickListener: OnItemChildClickListener? = null
    private var mOnItemChildLongClickListener: OnItemChildLongClickListener? = null


    private var mFirstOnlyEnable = true
    private val mOpenAnimationEnable = false
    private val mInterpolator = LinearInterpolator()
    private var mDuration = 300
    private var mLastPosition = -1

    private val mCustomAnimation: BaseAnimation? = null
    private val mSelectAnimation = AlphaInAnimation()
    //header footer
    private val mHeaderLayout: LinearLayout? = null
    private val mFooterLayout: LinearLayout? = null
    //empty
    private var mEmptyLayout: FrameLayout? = null
    private var mIsUseEmpty = true
    private var mHeadAndEmptyEnable: Boolean = false
    private val mFootAndEmptyEnable: Boolean = false

    protected val TAG = BaseQuickAdapter::class.java.simpleName
    protected var mContext: Context? = null
    protected var mLayoutResId: Int = 0
    protected var mLayoutInflater: LayoutInflater? = null
    protected var mData: MutableList<T>? = null
    val EMPTY_VIEW = 0x00000555

    var recyclerView: RecyclerView? = null
    private var mMultiTypeDelegate: MultiTypeDelegate<T>? = null

    fun setMultiTypeDelegate(multiTypeDelegate: MultiTypeDelegate<T>) {
        mMultiTypeDelegate = multiTypeDelegate
    }

    fun getMultiTypeDelegate(): MultiTypeDelegate<T>? {
        return mMultiTypeDelegate
    }

    constructor(@LayoutRes layoutResId: Int, @Nullable data: MutableList<T>?) {
        this.mData = data ?: mutableListOf()
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId
        }
    }

    constructor(@Nullable data: MutableList<T>) : this(0, data)

    constructor(@LayoutRes layoutResId: Int) : this(layoutResId, null)

    private fun checkNoNull() {
        recyclerView ?: let {
            throw NullPointerException("recyclerView not null")
        }
    }

    fun bindToRecyclerView(recyclerView: RecyclerView) {
        recyclerView?.let {
            throw RuntimeException("don't bind twice")
        }
    }

    fun setDuration(duration: Int) {
        mDuration = duration
    }


    fun setNewData(data: MutableList<T>) {
        this.mData = data ?: mutableListOf()
        mLastPosition = -1
        notifyDataSetChanged()
    }


    fun addData(@IntRange(from = 0) position: Int, data: T) {
        mData?.add(position, data)
        notifyItemInserted(position)
    }

    fun addData(data: T) {
        mData?.add(data)
        notifyItemInserted(mData!!.size)
        compatibilityDataSizeChanged(1)
    }

    fun addData(@IntRange(from = 0) position: Int, newData: Collection<T>) {
        mData?.addAll(position, newData)
        notifyItemRangeInserted(position, newData.size)
        compatibilityDataSizeChanged(newData.size)
    }

    fun addData(newData: Collection<T>) {
        mData?.addAll(newData)
        notifyItemRangeInserted(mData!!.size - newData.size, newData.size)
        compatibilityDataSizeChanged(newData.size)
    }

    fun replaceData(data: Collection<T>) {
        if (data !== mData) {
            mData?.clear()
            mData?.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun remove(@IntRange(from = 0) position: Int) {
        mData?.removeAt(position)
        val internalPosition = position
        notifyItemRemoved(internalPosition)
        compatibilityDataSizeChanged(0)
        notifyItemRangeChanged(internalPosition, mData!!.size - internalPosition)
    }


    /**
     * change data
     */
    fun setData(@IntRange(from = 0) index: Int, data: T) {
        mData?.set(index, data)
        notifyItemChanged(index)
    }


    private fun compatibilityDataSizeChanged(size: Int) {
        val dataSize = if (mData == null) 0 else mData!!.size
        if (dataSize == size) {
            notifyDataSetChanged()
        }
    }

    fun getData(): MutableList<T>? {
        return mData
    }

    fun getItem(@IntRange(from = 0) position: Int): T? {
        return if (position < mData!!.size)
            mData?.get(position)
        else
            null
    }

    fun getEmptyViewCount(): Int {
        if (mEmptyLayout == null || mEmptyLayout?.childCount === 0) {
            return 0
        }
        if (!mIsUseEmpty) {
            return 0
        }
        return if (mData!!.size !== 0) {
            0
        } else 1
    }

    override fun getItemCount(): Int {
        return if (getEmptyViewCount() === 1) 1 else mData!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (true) {
            getEmptyViewCount() === 1 && mHeadAndEmptyEnable -> EMPTY_VIEW
            else -> getDefItemViewType(position)
        }

    }

    protected fun getDefItemViewType(position: Int): Int {
        return if (mMultiTypeDelegate != null) {
            mMultiTypeDelegate?.getDefItemViewType(mData!!, position)!!
        } else super.getItemViewType(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K {
        this.mContext = parent.context
        this.mLayoutInflater = LayoutInflater.from(mContext)
        var baseViewHolder: K = when (viewType) {
            EMPTY_VIEW -> createBaseViewHolder(mEmptyLayout!!)
            else -> {
                onCreateDefViewHolder(parent, viewType)
            }
        }

        bindViewClickListener(baseViewHolder)
        baseViewHolder.setAdapter(this@BaseQuickAdapter)
        return baseViewHolder
    }


    protected fun createBaseViewHolder(view: View): K {
        var temp: Class<*>? = javaClass
        var z: Class<*>? = null
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp)
            temp = temp.superclass
        }
        val k: K?
        // 泛型擦除会导致z为null
        if (z == null) {
            k = BaseViewHolder(view) as K
        } else {
            k = createGenericKInstance(z, view)
        }
        return k ?: BaseViewHolder(view) as K
    }

    protected fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): K {
        var layoutId = mLayoutResId
        if (mMultiTypeDelegate != null) {
            layoutId = mMultiTypeDelegate!!.getLayoutId(viewType)
        }
        return createBaseViewHolder(parent, layoutId)
    }

    protected fun createBaseViewHolder(parent: ViewGroup, layoutResId: Int): K {
        return createBaseViewHolder(getItemView(layoutResId, parent))
    }

    private fun bindViewClickListener(baseViewHolder: BaseViewHolder?) {
        if (baseViewHolder == null) {
            return
        }
        val view = baseViewHolder.itemView ?: return
        view.setOnClickListener {
            mOnItemClickListener?.onItemClick(this@BaseQuickAdapter, it, baseViewHolder.layoutPosition)
        }
        mOnItemLongClickListener?.let {
            view.setOnLongClickListener { v -> mOnItemLongClickListener!!.onItemLongClick(this@BaseQuickAdapter, v, baseViewHolder.layoutPosition) }
        }
    }

    protected fun getItemView(@LayoutRes layoutResId: Int, parent: ViewGroup): View {
        return mLayoutInflater!!.inflate(layoutResId, parent, false)
    }

    private fun getInstancedGenericKClass(z: Class<*>): Class<*>? {
        val type = z.genericSuperclass
        if (type is ParameterizedType) {
            val types = (type as ParameterizedType).getActualTypeArguments()
            for (temp in types) {
                if (temp is Class<*>) {
                    val tempClass = temp as Class<*>
                    if (BaseViewHolder::class.java.isAssignableFrom(tempClass)) {
                        return tempClass
                    }
                }
            }
        }
        return null
    }

    private fun createGenericKInstance(z: Class<*>, view: View): K? {
        try {
            val constructor: Constructor<K>
            // inner and unstatic class
            if (z.isMemberClass && !Modifier.isStatic(z.modifiers)) {
                constructor = z.getDeclaredConstructor(javaClass, View::class.java) as Constructor<K>
                constructor.setAccessible(true)
                return constructor.newInstance(this, view)
            } else {
                constructor = z.getDeclaredConstructor(View::class.java) as Constructor<K>
                constructor.setAccessible(true)
                return constructor.newInstance(view)
            }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        return null
    }


    override fun onViewAttachedToWindow(holder: K) {
        super.onViewAttachedToWindow(holder)
        val type = holder.itemViewType
        when (type) {
            EMPTY_VIEW -> setFullSpan(holder)
            else -> addAnimation(holder)
        }
    }


    override fun onBindViewHolder(holder: K, position: Int) {
        val viewType = holder.itemViewType
        when (viewType) {
            0 -> convert(holder, getItem(position)!!)
            else -> convert(holder, getItem(position)!!)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView!!.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val type = getItemViewType(position)
                    return when {
                        mSpanSizeLookup == null -> {
                            if (isFixedViewType(type)) manager.spanCount else 1
                        }
                        else -> {
                            if (isFixedViewType(type)) manager.spanCount else mSpanSizeLookup!!.getSpanSize(manager, position)
                        }
                    }
                }
            }
        }
    }

    protected fun isFixedViewType(type: Int): Boolean {
        return type == EMPTY_VIEW
    }

    protected fun setFullSpan(holder: RecyclerView.ViewHolder) {
        if (holder.itemView.layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            val params = holder
                    .itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            params.isFullSpan = true
        }
    }


    private var mSpanSizeLookup: SpanSizeLookup? = null

    interface SpanSizeLookup {
        fun getSpanSize(gridLayoutManager: GridLayoutManager, position: Int): Int
    }

    /**
     * @param spanSizeLookup instance to be used to query number of spans occupied by each item
     */
    fun setSpanSizeLookup(spanSizeLookup: SpanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup
    }

    fun addAnimation(holder: RecyclerView.ViewHolder) {
        if (mOpenAnimationEnable) {
            if (!mFirstOnlyEnable or (holder.layoutPosition > mLastPosition)) {
                var animation = mCustomAnimation ?: mSelectAnimation
                for (anim in animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.layoutPosition)
                }

            }

        }
    }

    protected fun startAnim(anim: Animator, index: Int) {
        anim.setDuration(mDuration.toLong()).start()
        anim.setInterpolator(mInterpolator)
    }


    fun setEmptyView(layoutResId: Int, viewGroup: ViewGroup) {
        val view = LayoutInflater.from(viewGroup.context).inflate(layoutResId, viewGroup, false)
        setEmptyView(view)
    }

    fun isFirstOnly(firstOnly: Boolean) {
        this.mFirstOnlyEnable = firstOnly
    }


    abstract fun convert(helper: K, item: T)

    fun setEmptyView(emptyView: View) {
        var insert = false
        if (mEmptyLayout == null) {
            mEmptyLayout = FrameLayout(emptyView.context)
            val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            val lp = emptyView.layoutParams
            if (lp != null) {
                layoutParams.width = lp.width
                layoutParams.height = lp.height
            }
            mEmptyLayout?.layoutParams = layoutParams
            insert = true
        }
        mEmptyLayout?.removeAllViews()
        mEmptyLayout?.addView(emptyView)
        mIsUseEmpty = true
        if (insert) {
            if (getEmptyViewCount() === 1) {
                var position = 0
                if (mHeadAndEmptyEnable) {
                    position++
                }
                notifyItemInserted(position)
            }
        }
    }


    fun getViewByPosition(position: Int, @IdRes viewId: Int): View? {
        checkNoNull()
        return getViewByPosition(recyclerView, position, viewId)
    }

    open fun getViewByPosition(recyclerView: RecyclerView?, position: Int, @IdRes viewId: Int): View? {
        if (recyclerView == null) {
            return null
        }
        val viewHolder = recyclerView.findViewHolderForLayoutPosition(position) as BaseViewHolder
        if (viewHolder == null)
            return null
        return viewHolder.getView(viewId)
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    //########### expand
    private fun recursiveExpand(position: Int, list: List<*>): Int {
        var count = 0
        var pos = position + list.size - 1
        var i = list.size - 1
        while (i >= 0) {
            if (list[i] is IExpandable<*>) {
                val item = list[i] as IExpandable<*>
                if (item.isExpanded() && hasSubItems(item)) {
                    val subList = item.getSubItems()
                    mData?.addAll(pos + 1, subList as Collection<T>)
                    val subItemCount = recursiveExpand(pos + 1, subList)
                    count += subItemCount
                }
            }
            i--
            pos--
        }
        return count

    }

    fun expand(position: Int, animate: Boolean, shouldNotify: Boolean): Int {
        val item = getExpandableItem(position)
        var subItemCount = 0
        return when {
            item == null -> 0
            !hasSubItems(item) -> {
                item.setExpanded(false)
                return 0
            }
            !item.isExpanded() -> {
                val subItems = item.getSubItems()
                mData?.addAll(position + 1, subItems)
                subItemCount += recursiveExpand(position + 1, subItems)

                item.setExpanded(true)
                subItemCount += subItems.size

                if (shouldNotify) {
                    if (animate) {
                        notifyItemChanged(position)
                        notifyItemRangeInserted(position + 1, subItemCount)
                    } else {
                        notifyDataSetChanged()
                    }
                }
                return subItemCount
            }
            else -> subItemCount
        }
    }

    fun hasSubItems(item: IExpandable<*>): Boolean {
        if (item == null)
            return false
        return item.getSubItems() != null && item.getSubItems().isNotEmpty()
    }

    fun isExpandable(item: T?): Boolean {
        return item != null && item is IExpandable<*>
    }

    fun getExpandableItem(position: Int): IExpandable<T>? {
        var item = getItem(position)
        return when (item) {
            isExpandable(item) -> item as IExpandable<T>
            else -> null
        }
    }

    fun expand(position: Int, animate: Boolean): Int {
        return expand(position, animate, true)
    }

    fun expand(@IntRange(from = 0) position: Int): Int {
        return expand(position, true, true)
    }


    fun expandAll(position: Int, animate: Boolean, notify: Boolean): Int {
        var endItem: T? = null
        if (position + 1 < this.mData!!.size) {
            endItem = getItem(position + 1)
        }


        val expandable = getExpandableItem(position)
        if (expandable == null || !hasSubItems(expandable)) {
            return 0
        }

        var count = expand(position, false, false)

        for (i in position + 1 until mData!!.size) {
            val item: T? = getItem(i)

            if (item == endItem) break

            if (isExpandable(item)) {
                count += expand(i, false, false)
            }
        }

        when {
            notify && animate -> {
                notifyItemRangeInserted(position + 1, count)
            }
            notify -> notifyDataSetChanged()
        }

        return count
    }


    fun expandAll(position: Int, init: Boolean): Int {
        return expandAll(position, true, !init)
    }

    fun expandAll() {
        for (i in mData!!.size - 1 downTo 0) {
            expandAll(i, false, false)
        }
    }

    fun recursiveCollapse(position: Int): Int {
        var item = getItem(position)
        if (!isExpandable(item)) {
            return 0
        }
        var expandable = item as IExpandable<T>
        var subItemCount = 0
        if (expandable.isExpanded()) {
            val subItems = expandable.getSubItems()
            for (i in subItems.size - 1 downTo 0) {
                val subItem = subItems.get(i)
                var pos = getItemPosition(subItem)
                if (pos < 0)
                    continue
                if (subItem is IExpandable<*>) {
                    subItemCount += recursiveCollapse(pos)
                }
                mData?.removeAt(pos)
                subItemCount++
            }
        }
        return subItemCount
    }


    fun collapse(position: Int, animate: Boolean, notify: Boolean): Int {
        val expandable = getExpandableItem(position)
        if (expandable == null)
            return 0
        val subItemCount = recursiveCollapse(position)
        expandable.setExpanded(false)
        if (notify) {
            if (animate) {
                notifyItemChanged(position)
                notifyItemRangeRemoved(position + 1, subItemCount)
            } else {
                notifyDataSetChanged()
            }
        }

        return subItemCount
    }

    fun collapse(position: Int): Int {
        return collapse(position, true, true)
    }


    fun getItemPosition(item: T): Int {
        return when {
            item != null && mData != null && mData!!.isNotEmpty() -> {
                mData!!.indexOf(item)
            }
            else -> -1
        }
    }


    fun getParentPosition(item: T): Int {
        val itemPosition = getItemPosition(item)
        if (itemPosition == -1) return -1

        var level = 0
        if (item is IExpandable<*>) {
            level = item.getLevel()
        } else {
            level = Int.MAX_VALUE
        }

        if (level == 0) return itemPosition
        if (level == -1) return -1

        for (i in itemPosition downTo 0) {
            val temp = mData!![i]
            if (temp is IExpandable<*>) {
//                if (temp.getLevel() > 0 && temp.getLevel() < level) {
                if (temp.getLevel() in 1 until level) {
                    return i
                }
            }
        }
        return -1
    }


    fun getOnItemLongClickListener(): OnItemLongClickListener? {
        return mOnItemLongClickListener
    }


    fun getOnItemClickListener(): OnItemClickListener? {
        return mOnItemClickListener
    }


    @Nullable
    fun getOnItemChildClickListener(): OnItemChildClickListener? {
        return mOnItemChildClickListener
    }


    @Nullable
    fun getOnItemChildLongClickListener(): OnItemChildLongClickListener? {
        return mOnItemChildLongClickListener
    }

    fun setOnItemClickListener(@Nullable listener: OnItemClickListener) {
        mOnItemClickListener = listener
    }


    fun setOnItemChildClickListener(listener: OnItemChildClickListener) {
        mOnItemChildClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        mOnItemLongClickListener = listener
    }


    fun setOnItemChildLongClickListener(listener: OnItemChildLongClickListener) {
        mOnItemChildLongClickListener = listener
    }

    interface OnItemClickListener {

        fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int)
    }

    interface OnItemLongClickListener {

        fun onItemLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int): Boolean
    }

    interface OnItemChildLongClickListener {
        /**
         * callback method to be invoked when an item in this view has been
         * click and held
         *
         * @param view     The childView whihin the itemView that was clicked and held.
         * @param position The position of the view int the adapter
         * @return true if the callback consumed the long click ,false otherwise
         */
        fun onItemChildLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int): Boolean?
    }

    interface OnItemChildClickListener {
        /**
         * callback method to be invoked when an item in this view has been
         * click and held
         *
         * @param view     The view whihin the ItemView that was clicked
         * @param position The position of the view int the adapter
         */
        fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int)
    }
}