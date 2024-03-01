package com.mozhimen.basick.utilk.android.widget

import android.widget.ProgressBar
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKProgressBar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/1
 * @Version 1.0
 */
fun ProgressBar.applyProgressAnimate(progress: Int) {
    UtilKProgressBar.applyProgressAnimate(this, progress)
}

//////////////////////////////////////////////////////////////////////////

object UtilKProgressBar {
    @JvmStatic
    fun applyProgressAnimate(progressBar: ProgressBar, progress: Int) {
        if (UtilKBuildVersion.isAfterV_24_7_N())
            progressBar.setProgress(progress, true)
        else
            progressBar.setProgress(progress)
    }
}