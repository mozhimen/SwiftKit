package com.mozhimen.uicorek.layoutk

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ProgressBar
import com.mozhimen.basick.utilk.android.util.dp2px
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame

/**
 * @ClassName LayoutKLoading
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/16 15:33
 * @Version 1.0
 */
class LayoutKLoading @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseLayoutKFrame(context, attrs, defStyleAttr) {
    init {
        initView()
    }

    override fun initView() {
        val progressBar = ProgressBar(context)
        addView(progressBar, LayoutParams(45.dp2px().toInt(), 45.dp2px().toInt()).apply { gravity = Gravity.CENTER })
    }
}