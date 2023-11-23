package com.mozhimen.uicorek.vhk.bases

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes

/**
 * @ClassName BaseVHK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/11/22 15:10
 * @Version 1.0
 */
class BaseVHK private constructor(context: Context, parent: ViewGroup, layoutId: Int) {

    private val _views: SparseArray<View?> = SparseArray()//存储ListView 的 item中的View

    /**
     * 获取当前条目//存放convertView
     */
    var itemView: View = LayoutInflater.from(context).inflate(layoutId, parent, false).apply {
        tag = this@BaseVHK
    }
        private set

    /**
     * 获取条目位置//游标
     */
    var itemPosition = 0
        private set

    ////////////////////////////////////////////////////////////////////////

    companion object {
        @JvmStatic
        fun bindView(context: Context, convertView: View?, parent: ViewGroup, layoutId: Int, position: Int): BaseVHK {//绑定ViewHolder与item
            val holder: BaseVHK
            if (convertView == null) {
                holder = BaseVHK(context, parent, layoutId)
            } else {
                holder = convertView.tag as BaseVHK
                holder.itemView = convertView
            }
            holder.itemPosition = position
            return holder
        }
    }

    ////////////////////////////////////////////////////////////////////////

    fun <T : View?> getView(id: Int): T? {
        var view = _views[id] as? T?
        if (view == null) {
            view = itemView.findViewById<View>(id) as T
            _views.put(id, view)
        }
        return view
    }

    /**
     * 设置文字
     */
    fun setText(id: Int, text: CharSequence?): BaseVHK {
        val view = getView<View>(id)!!
        if (view is TextView) {
            view.text = text
        }
        return this
    }

    /**
     * 设置图片
     */
    fun setImageResource(id: Int, @DrawableRes drawableId: Int): BaseVHK {
        val imageView = getView<View>(id)!!
        if (imageView is ImageView)
            imageView.setImageResource(drawableId)
        else
            imageView.setBackgroundResource(drawableId)
        return this
    }

    /**
     * 设置点击监听
     */
    fun setOnClickListener(id: Int, listener: View.OnClickListener): BaseVHK {
        getView<View>(id)!!.setOnClickListener(listener)
        return this
    }

    /**
     * 设置可见
     */
    fun setVisibility(id: Int, visible: Int): BaseVHK {
        getView<View>(id)!!.visibility = visible
        return this
    }

    /**
     * 设置标签
     */
    fun setTag(id: Int, obj: Any): BaseVHK {
        getView<View>(id)!!.tag = obj
        return this
    } //其他方法可自行扩展
}