package com.mozhimen.uicoremk.bannermk

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.mozhimen.uicoremk.R
import com.mozhimen.uicoremk.bannermk.commons.IBannerMKIndicator
import com.mozhimen.uicoremk.utilmk.UtilMKDisplay

/**
 * @ClassName CircleIndicatorK
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/23 12:52
 * @Version 1.0
 */
class CircleIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr), IBannerMKIndicator<FrameLayout?> {
    companion object {
        private const val VMC = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    /**
     * 正常状态下的指示器
     */
    @DrawableRes
    private val mPointNormal = R.drawable.bannermk_circle_indicator_point_normal

    /**
     * 选中状态下的指示器
     */
    @DrawableRes
    private val mPointSelected = R.drawable.bannermk_circle_indicator_point_selected

    /**
     * 指示器左右内间距
     */
    private var mPointLeftRightPadding = 0

    /**
     * 指示器上下内间距
     */
    private var mPointTopBottomPadding = 0

    init {
        mPointLeftRightPadding = UtilMKDisplay.dp2px(5f)
        mPointTopBottomPadding = UtilMKDisplay.dp2px(15f)
    }

    override fun get(): FrameLayout {
        return this
    }

    override fun onInflate(count: Int) {
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
            mPointLeftRightPadding,
            mPointTopBottomPadding,
            mPointLeftRightPadding,
            mPointTopBottomPadding
        )
        for (i in 0 until count) {
            imageView = ImageView(context)
            imageView.layoutParams = imageViewParams
            if (i == 0) {
                imageView.setImageResource(mPointSelected)
            } else {
                imageView.setImageResource(mPointNormal)
            }
            groupView.addView(imageView)
        }
        val groupViewParams = LayoutParams(VMC, VMC)
        groupViewParams.gravity = Gravity.CENTER or Gravity.BOTTOM
        addView(groupView, groupViewParams)
    }

    override fun onPointChange(current: Int, count: Int) {
        val viewGroup = getChildAt(0) as ViewGroup
        for (i in 0 until viewGroup.childCount) {
            val imageView = viewGroup.getChildAt(i) as ImageView
            if (i == current) {
                imageView.setImageResource(mPointSelected)
            } else {
                imageView.setImageResource(mPointNormal)
            }
            imageView.requestLayout()
        }
    }
}