package com.mangmang.fz.adapter.base

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.graphics.Paint.SUBPIXEL_TEXT_FLAG
import android.graphics.Typeface
import android.text.util.Linkify
import android.view.animation.AlphaAnimation
import android.os.Build
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.widget.*


/**
 * Created by mangmang on 2017/9/20.
 */
class BaseViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView) {

    private val views: SparseArray<View>

    fun getNestViews(): Set<Int>? {
        return nestViews
    }

    private val nestViews: HashSet<Int>

    private val childClickViewIds: LinkedHashSet<Int>

    private val itemChildLongClickViewIds: LinkedHashSet<Int>
    private var adapter: BaseQuickAdapter<*, *>? = null
    private var associatedObject: Any? = null

    init {
        views = SparseArray()
        childClickViewIds = LinkedHashSet()
        itemChildLongClickViewIds = LinkedHashSet()
        nestViews = HashSet()
    }

    private fun getClickPosition(): Int {
        if (layoutPosition >= 0) {
            return layoutPosition
        }
        return 0
    }

    fun getItemChildLongClickViewIds(): HashSet<Int> {
        return itemChildLongClickViewIds
    }

    fun getChildClickViewIds(): HashSet<Int> {
        return childClickViewIds
    }

    fun setText(@IdRes viewId: Int, value: CharSequence): BaseViewHolder {
        val view = getView<TextView>(viewId)
        view.text = value
        return this
    }


    fun setText(@IdRes viewId: Int, @StringRes strId: Int): BaseViewHolder {
        val view = getView<TextView>(viewId)
        view!!.setText(strId)
        return this
    }

    /**
     * Will set the image of an ImageView from a resource id.
     *
     * @param viewId     The view id.
     * @param imageResId The image resource id.
     * @return The BaseViewHolder for chaining.
     */
    fun setImageResource(@IdRes viewId: Int, @DrawableRes imageResId: Int): BaseViewHolder {
        val view = getView<View>(viewId) as ImageView
        view.setImageResource(imageResId)
        return this
    }

    /**
     * Will set background color of a view.
     *
     * @param viewId The view id.
     * @param color  A color, not a resource id.
     * @return The BaseViewHolder for chaining.
     */
    fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): BaseViewHolder {
        val view = getView<View>(viewId)
        view!!.setBackgroundColor(color)
        return this
    }

    /**
     * Will set background of a view.
     *
     * @param viewId        The view id.
     * @param backgroundRes A resource to use as a background.
     * @return The BaseViewHolder for chaining.
     */
    fun setBackgroundRes(@IdRes viewId: Int, @DrawableRes backgroundRes: Int): BaseViewHolder {
        val view = getView<View>(viewId)
        view!!.setBackgroundResource(backgroundRes)
        return this
    }

    /**
     * Will set text color of a TextView.
     *
     * @param viewId    The view id.
     * @param textColor The text color (not a resource id).
     * @return The BaseViewHolder for chaining.
     */
    fun setTextColor(@IdRes viewId: Int, @ColorInt textColor: Int): BaseViewHolder {
        val view = getView<TextView>(viewId)
        view!!.setTextColor(textColor)
        return this
    }


    /**
     * Will set the image of an ImageView from a drawable.
     *
     * @param viewId   The view id.
     * @param drawable The image drawable.
     * @return The BaseViewHolder for chaining.
     */
    fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable): BaseViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageDrawable(drawable)
        return this
    }

    /**
     * Add an action to set the image of an image view. Can be called multiple times.
     */
    fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap): BaseViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageBitmap(bitmap)
        return this
    }

    /**
     * Add an action to set the alpha of a view. Can be called multiple times.
     * Alpha between 0-1.
     */
    fun setAlpha(@IdRes viewId: Int, value: Float): BaseViewHolder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView<View>(viewId)!!.setAlpha(value)
        } else {
            // Pre-honeycomb hack to set Alpha value
            val alpha = AlphaAnimation(value, value)
            alpha.duration = 0
            alpha.fillAfter = true
            getView<View>(viewId)!!.startAnimation(alpha)
        }
        return this
    }

    /**
     * Set a view visibility to VISIBLE (true) or GONE (false).
     *
     * @param viewId  The view id.
     * @param visible True for VISIBLE, false for GONE.
     * @return The BaseViewHolder for chaining.
     */
    fun setGone(@IdRes viewId: Int, visible: Boolean): BaseViewHolder {
        val view = getView<View>(viewId)
        view!!.setVisibility(if (visible) View.VISIBLE else View.GONE)
        return this
    }

    /**
     * Set a view visibility to VISIBLE (true) or INVISIBLE (false).
     *
     * @param viewId  The view id.
     * @param visible True for VISIBLE, false for INVISIBLE.
     * @return The BaseViewHolder for chaining.
     */
    fun setVisible(@IdRes viewId: Int, visible: Boolean): BaseViewHolder {
        val view = getView<View>(viewId)
        view!!.setVisibility(if (visible) View.VISIBLE else View.INVISIBLE)
        return this
    }

    /**
     * Add links into a TextView.
     *
     * @param viewId The id of the TextView to linkify.
     * @return The BaseViewHolder for chaining.
     */
    fun linkify(@IdRes viewId: Int): BaseViewHolder {
        val view = getView<TextView>(viewId)
        Linkify.addLinks(view!!, Linkify.ALL)
        return this
    }

    /**
     * Apply the typeface to the given viewId, and enable subpixel rendering.
     */
    fun setTypeface(@IdRes viewId: Int, typeface: Typeface): BaseViewHolder {
        val view = getView<TextView>(viewId)
        view!!.setTypeface(typeface)
        view!!.setPaintFlags(view!!.getPaintFlags() or Paint.SUBPIXEL_TEXT_FLAG)
        return this
    }

    /**
     * Apply the typeface to all the given viewIds, and enable subpixel rendering.
     */
    fun setTypeface(typeface: Typeface, vararg viewIds: Int): BaseViewHolder {
        for (viewId in viewIds) {
            val view = getView<TextView>(viewId)
            view!!.setTypeface(typeface)
            view!!.setPaintFlags(view!!.getPaintFlags() or Paint.SUBPIXEL_TEXT_FLAG)
        }
        return this
    }

    /**
     * Sets the progress of a ProgressBar.
     *
     * @param viewId   The view id.
     * @param progress The progress.
     * @return The BaseViewHolder for chaining.
     */
    fun setProgress(@IdRes viewId: Int, progress: Int): BaseViewHolder {
        val view = getView<ProgressBar>(viewId)
        view!!.setProgress(progress)
        return this
    }

    /**
     * Sets the progress and max of a ProgressBar.
     *
     * @param viewId   The view id.
     * @param progress The progress.
     * @param max      The max value of a ProgressBar.
     * @return The BaseViewHolder for chaining.
     */
    fun setProgress(@IdRes viewId: Int, progress: Int, max: Int): BaseViewHolder {
        val view = getView<ProgressBar>(viewId)
        view!!.setMax(max)
        view!!.setProgress(progress)
        return this
    }

    /**
     * Sets the range of a ProgressBar to 0...max.
     *
     * @param viewId The view id.
     * @param max    The max value of a ProgressBar.
     * @return The BaseViewHolder for chaining.
     */
    fun setMax(@IdRes viewId: Int, max: Int): BaseViewHolder {
        val view = getView<ProgressBar>(viewId)
        view!!.setMax(max)
        return this
    }

    /**
     * Sets the rating (the number of stars filled) of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @return The BaseViewHolder for chaining.
     */
    fun setRating(@IdRes viewId: Int, rating: Float): BaseViewHolder {
        val view = getView<RatingBar>(viewId)
        view!!.setRating(rating)
        return this
    }

    /**
     * Sets the rating (the number of stars filled) and max of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @param max    The range of the RatingBar to 0...max.
     * @return The BaseViewHolder for chaining.
     */
    fun setRating(@IdRes viewId: Int, rating: Float, max: Int): BaseViewHolder {
        val view = getView<RatingBar>(viewId)
        view!!.setMax(max)
        view!!.setRating(rating)
        return this
    }

    /**
     * Sets the on click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on click listener;
     * @return The BaseViewHolder for chaining.
     */
    @Deprecated("")
    fun setOnClickListener(@IdRes viewId: Int, listener: View.OnClickListener): BaseViewHolder {
        val view = getView<View>(viewId)
        view.setOnClickListener(listener)
        return this
    }

    fun addOnClickListener(@IdRes viewId: Int): BaseViewHolder {
        childClickViewIds.add(viewId)
        val view = getView<View>(viewId)
        if (!view?.isClickable()) {
            view?.setClickable(true)
        }
        view?.setOnClickListener(View.OnClickListener { v ->
            adapter?.getOnItemChildClickListener()?.onItemChildClick(adapter!!, v, getClickPosition())
        })
        return this
    }


    fun setNestView(@IdRes viewId: Int): BaseViewHolder {
        addOnClickListener(viewId)
        addOnLongClickListener(viewId)
        nestViews.add(viewId)
        return this
    }

    fun addOnLongClickListener(@IdRes viewId: Int): BaseViewHolder {
        itemChildLongClickViewIds.add(viewId)
        val view = getView<View>(viewId)
        if (!view?.isLongClickable()) {
            view?.setLongClickable(true)
        }
        view?.setOnLongClickListener(View.OnLongClickListener { v ->
            adapter?.getOnItemChildLongClickListener() != null &&
                    adapter?.getOnItemLongClickListener()
                            ?.onItemLongClick(adapter!!, v, getClickPosition())!!
        })
        return this
    }


    /**
     * Sets the on touch listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on touch listener;
     * @return The BaseViewHolder for chaining.
     */
    @Deprecated("")
    fun setOnTouchListener(@IdRes viewId: Int, listener: View.OnTouchListener): BaseViewHolder {
        val view = getView<View>(viewId)
        view?.setOnTouchListener(listener)
        return this
    }

    /**
     * Sets the on long click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on long click listener;
     * @return The BaseViewHolder for chaining.
     * Please use [.addOnLongClickListener] (adapter.setOnItemChildLongClickListener(listener))}
     */
    @Deprecated("")
    fun setOnLongClickListener(@IdRes viewId: Int, listener: View.OnLongClickListener): BaseViewHolder {
        val view = getView<View>(viewId)
        view?.setOnLongClickListener(listener)
        return this
    }

    /**
     * Sets the listview or gridview's item click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item on click listener;
     * @return The BaseViewHolder for chaining.
     * Please use [.addOnClickListener] (int)} (adapter.setOnItemChildClickListener(listener))}
     */
    @Deprecated("")
    fun setOnItemClickListener(@IdRes viewId: Int, listener: AdapterView.OnItemClickListener): BaseViewHolder {
        val view = getView<AdapterView<*>>(viewId)
        view!!.setOnItemClickListener(listener)
        return this
    }

    /**
     * Sets the listview or gridview's item long click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item long click listener;
     * @return The BaseViewHolder for chaining.
     */
    fun setOnItemLongClickListener(@IdRes viewId: Int, listener: AdapterView.OnItemLongClickListener): BaseViewHolder {
        val view = getView<AdapterView<*>>(viewId)
        view!!.setOnItemLongClickListener(listener)
        return this
    }

    /**
     * Sets the listview or gridview's item selected click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item selected click listener;
     * @return The BaseViewHolder for chaining.
     */
    fun setOnItemSelectedClickListener(@IdRes viewId: Int, listener: AdapterView.OnItemSelectedListener): BaseViewHolder {
        val view = getView<AdapterView<*>>(viewId)
        view!!.setOnItemSelectedListener(listener)
        return this
    }

    /**
     * Sets the on checked change listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The checked change listener of compound button.
     * @return The BaseViewHolder for chaining.
     */
    fun setOnCheckedChangeListener(@IdRes viewId: Int, listener: CompoundButton.OnCheckedChangeListener): BaseViewHolder {
        val view = getView<CompoundButton>(viewId)
        view!!.setOnCheckedChangeListener(listener)
        return this
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param tag    The tag;
     * @return The BaseViewHolder for chaining.
     */
    fun setTag(@IdRes viewId: Int, tag: Any): BaseViewHolder {
        val view = getView<View>(viewId)
        view!!.setTag(tag)
        return this
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param key    The key of tag;
     * @param tag    The tag;
     * @return The BaseViewHolder for chaining.
     */
    fun setTag(@IdRes viewId: Int, key: Int, tag: Any): BaseViewHolder {
        val view = getView<View>(viewId)
        view!!.setTag(key, tag)
        return this
    }

    /**
     * Sets the checked status of a checkable.
     *
     * @param viewId  The view id.
     * @param checked The checked status;
     * @return The BaseViewHolder for chaining.
     */
    fun setChecked(@IdRes viewId: Int, checked: Boolean): BaseViewHolder {
        val view = getView<View>(viewId)
        // View unable cast to Checkable
        if (view is Checkable) {
            (view as Checkable).isChecked = checked
        }
        return this
    }

    /**
     * Sets the adapter of a adapter view.
     *
     * @param viewId  The view id.
     * @param adapter The adapter;
     * @return The BaseViewHolder for chaining.
     */
    fun setAdapter(@IdRes viewId: Int, adapter: Adapter): BaseViewHolder {
        val view = getView<AdapterView<*>>(viewId)
        view.adapter = adapter
        return this
    }

    /**
     * Sets the adapter of a adapter view.
     *
     * @param adapter The adapter;
     * @return The BaseViewHolder for chaining.
     */
    protected fun setAdapter(adapter: BaseQuickAdapter<*, *>): BaseViewHolder {
        this.adapter = adapter
        return this
    }

    fun <T : View> getView(@IdRes viewId: Int): T {
        var view = views.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as T
    }


    fun getAssociatedObject(): Any? {
        return associatedObject
    }

    /**
     * Should be called during convert
     */
    fun setAssociatedObject(associatedObject: Any) {
        this.associatedObject = associatedObject
    }


}