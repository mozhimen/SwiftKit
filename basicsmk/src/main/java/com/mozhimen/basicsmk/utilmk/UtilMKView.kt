package com.mozhimen.basicsmk.utilmk

import android.R
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.util.*

/**
 * @ClassName UtilMKView
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 16:50
 * @Version 1.0
 */
object UtilMKView {
    /**
     * 根据View的高度和宽高比，设置高度
     */
    @JvmStatic
    @BindingAdapter("widthHeightRatio")
    fun setWidthHeightRatio(view: View, ratio: Float) {
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val height: Int = view.height
                if (height > 0) {
                    view.layoutParams.width = (height * ratio).toInt()
                    view.invalidate()
                    view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
            }
        })
    }

    /**
     * Databinding加载图片
     */
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView?, url: String?) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(view?.context!!).load(url).dontAnimate().placeholder(R.color.white)
                .into(view)
        }
    }

    /**
     * 获取指定类型的子View
     *
     * @param group
     * @param cls
     * @param <T>
     * @return
    </T> */
    fun <T> findTypeView(group: ViewGroup?, cls: Class<T>): T? {
        if (group == null) {
            return null
        }
        val deque: Deque<View> = ArrayDeque()
        deque.add(group)
        while (!deque.isEmpty()) {
            val node = deque.removeFirst()
            if (cls.isInstance(node)) {
                return cls.cast(node)
            } else if (node is ViewGroup) {
                var i = 0
                val count = node.childCount
                while (i < count) {
                    deque.add(node.getChildAt(i))
                    i++
                }
            }
        }
        return null
    }
}

//region # image view
fun ImageView.load(url: String) {
    Glide.with(this).load(url).into(this)
}

fun ImageView.loadCircle(url: String) {
    Glide.with(this).load(url).transform(CircleCrop()).into(this)
}

fun ImageView.loadCircleBorder(url: String, borderWidth: Float = 0f, borderColor: Int) {
    Glide.with(this).load(url).transform(CircleBorderTransform(borderWidth, borderColor)).into(this)
}

fun ImageView.loadCorner(url: String, cornerRadius: Int) {
    Glide.with(this).load(url).transform(CenterCrop(), RoundedCorners(cornerRadius)).into(this)
}

class CircleBorderTransform(private val borderWidth: Float, borderColor: Int) : CircleCrop() {
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        borderPaint.color = borderColor
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidth
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val transform = super.transform(pool, toTransform, outWidth, outHeight)
        val canvas = Canvas(transform)
        val radiusWidth = outWidth / 2f
        val radiusHeight = outHeight / 2f
        canvas.drawCircle(
            radiusWidth,
            radiusHeight,
            radiusWidth.coerceAtMost(radiusHeight) - borderWidth / 2f,
            borderPaint
        )
        canvas.setBitmap(null)
        return transform
    }
}
//endregion