package com.mozhimen.uicorek.bannerk.temps

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.mozhimen.basicsk.extsk.dp2px
import com.mozhimen.basicsk.basek.BaseKLayoutFrame
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.bannerk.commons.IBannerKIndicator

/**
 * @ClassName CircleIndicatorK
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/23 12:52
 * @Version 1.0
 */
class PointIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    BaseKLayoutFrame(context, attrs, defStyleAttr), IBannerKIndicator<FrameLayout> {
    companion object {
        private const val VMC = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    @DrawableRes
    private val _pointNormal = R.drawable.bannerk_indicator_circle_point_normal//正常状态下的指示器

    @DrawableRes
    private val _pointSelected = R.drawable.bannerk_indicator_circle_point_selected//选中状态下的指示器

    private var _pointHorizontalMargin = 0//指示器左右内间距

    private var _pointVerticalMargin = 0//指示器上下内间距

    init {
        _pointHorizontalMargin = 5f.dp2px()
        _pointVerticalMargin = 15f.dp2px()
    }

    override fun get(): FrameLayout = this

    override fun inflate(count: Int) {
        removeAllViews()
        if (count <= 0) {
            return
        }
        val groupView = LinearLayout(context)
        groupView.orientation = LinearLayout.HORIZONTAL

        var imageView: ImageView
        val imageViewParams = LinearLayout.LayoutParams(VMC, VMC)
        imageViewParams.gravity = Gravity.CENTER_VERTICAL
        imageViewParams.setMargins(
            _pointHorizontalMargin,
            _pointVerticalMargin,
            _pointHorizontalMargin,
            _pointVerticalMargin
        )
        for (i in 0 until count) {
            imageView = ImageView(context)
            imageView.layoutParams = imageViewParams
            if (i == 0) {
                imageView.setImageResource(_pointSelected)
            } else {
                imageView.setImageResource(_pointNormal)
            }
            groupView.addView(imageView)
        }
        val groupViewParams = LayoutParams(VMC, VMC)
        groupViewParams.gravity = Gravity.CENTER or Gravity.BOTTOM
        addView(groupView, groupViewParams)
    }

    override fun onItemChange(current: Int, count: Int) {
        val viewGroup = getChildAt(0) as ViewGroup
        for (i in 0 until viewGroup.childCount) {
            val imageView = viewGroup.getChildAt(i) as ImageView
            if (i == current) {
                imageView.setImageResource(_pointSelected)
            } else {
                imageView.setImageResource(_pointNormal)
            }
            imageView.requestLayout()
        }
    }
}