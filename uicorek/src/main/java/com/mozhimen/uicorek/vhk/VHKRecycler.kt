package com.mozhimen.uicorek.vhk

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress
import com.mozhimen.basick.utilk.android.view.applyVisibleIfElseGone
import com.mozhimen.basick.utilk.android.view.applyVisibleIfElseInVisible

/**
 * @ClassName RecyclerKViewHolder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/25 15:35
 * @Version 1.0
 */
open class VHKRecycler(containerView: View) : RecyclerView.ViewHolder(containerView) {

    private var _viewCaches = SparseArray<View>()

    //////////////////////////////////////////////////////////////////////

    fun <T : View> findViewById(@IdRes viewId: Int): T {
        val view = findViewByIdOrNull<T>(viewId)
        checkNotNull(view) { "No view found with id $viewId" }
        return view
    }

    /**
     * 根据资源ID找到View
     */
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VIEW : View> findViewByIdOrNull(@IdRes viewId: Int): VIEW? {
        var view = _viewCaches.get(viewId)
        if (view == null) {
            view = itemView.findViewById<VIEW>(viewId)
            _viewCaches.put(viewId, view)
        }
        return view as? VIEW?
    }

    //////////////////////////////////////////////////////////////////////

    fun setText(@IdRes viewId: Int, value: CharSequence?): VHKRecycler {
        findViewById<TextView>(viewId).text = value
        return this
    }

    fun setText(@IdRes viewId: Int, @StringRes strId: Int): VHKRecycler {
        findViewById<TextView>(viewId).setText(strId)
        return this
    }

    fun setTextColor(@IdRes viewId: Int, @ColorInt colorInt: Int): VHKRecycler {
        findViewById<TextView>(viewId).setTextColor(colorInt)
        return this
    }

    fun setTextColorId(@IdRes viewId: Int, @ColorRes colorId: Int): VHKRecycler {
        findViewById<TextView>(viewId).setTextColor(itemView.resources.getColor(colorId))
        return this
    }

    fun setImageResource(@IdRes viewId: Int, @DrawableRes drawableId: Int): VHKRecycler {
        findViewById<ImageView>(viewId).setImageResource(drawableId)
        return this
    }

    fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable): VHKRecycler {
        findViewById<ImageView>(viewId).setImageDrawable(drawable)
        return this
    }

    fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap): VHKRecycler {
        findViewById<ImageView>(viewId).setImageBitmap(bitmap)
        return this
    }

    fun setBackgroundColor(@IdRes viewId: Int, @ColorInt colorInt: Int): VHKRecycler {
        findViewById<View>(viewId).setBackgroundColor(colorInt)
        return this
    }

    fun setBackgroundResource(@IdRes viewId: Int, @DrawableRes backgroundRes: Int): VHKRecycler {
        findViewById<View>(viewId).setBackgroundResource(backgroundRes)
        return this
    }

    fun setVisible(@IdRes viewId: Int, isVisible: Boolean): VHKRecycler {
        findViewById<View>(viewId).applyVisibleIfElseInVisible(isVisible)
        return this
    }

    fun setGone(@IdRes viewId: Int, isGone: Boolean): VHKRecycler {
        findViewById<View>(viewId).applyVisibleIfElseGone(!isGone)
        return this
    }

    fun setEnabled(@IdRes viewId: Int, isEnabled: Boolean): VHKRecycler {
        findViewById<View>(viewId).isEnabled = isEnabled
        return this
    }
}