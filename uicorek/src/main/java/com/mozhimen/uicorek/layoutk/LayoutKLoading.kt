package com.mozhimen.uicorek.layoutk

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.BlendMode
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatRatingBar
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.dp2px
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame

/**
 * @ClassName LayoutKLoading
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/16 15:33
 * @Version 1.0
 */
class LayoutKLoading @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseLayoutKFrame(context, attrs, defStyleAttr) {
    private var _loadColor: Int = 0
    private var _loadSize: Float = 40f.dp2px

    init {
        initAttrs(attrs)
        initView()
    }

    override fun initAttrs(attrs: AttributeSet?) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKLoading)
        _loadColor = typedArray.getColor(R.styleable.LayoutKLoading_layoutKLoading_loadColor, _loadColor)
        _loadSize = typedArray.getDimension(R.styleable.LayoutKLoading_layoutKLoading_loadSize, _loadSize)
        typedArray.recycle()
    }

    override fun initView() {
        val progressBar = ProgressBar(context)
        if (_loadColor != 0) {
            progressBar.indeterminateTintList = ColorStateList.valueOf(_loadColor)
            if (UtilKBuildVersion.isAfterV_29_10_Q()) {
                progressBar.indeterminateTintBlendMode = BlendMode.SRC_ATOP
            }

        }
        addView(progressBar, LayoutParams(_loadSize.toInt(), _loadSize.toInt()).apply { gravity = Gravity.CENTER })
    }
}