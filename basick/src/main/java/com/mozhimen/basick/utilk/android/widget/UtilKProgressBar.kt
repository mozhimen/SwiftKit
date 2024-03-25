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
fun ProgressBar.applyProgress(progress: Int) {
    UtilKProgressBar.applyProgress(this, progress)
}

//////////////////////////////////////////////////////////////////////////

object UtilKProgressBar {
    @JvmStatic
    fun applyProgress(progressBar: ProgressBar, progress: Int) {
        applyProgress(progressBar, progress, true)
    }

    @JvmStatic
    fun applyProgress(progressBar: ProgressBar, progress: Int, animate: Boolean) {
        if (UtilKBuildVersion.isAfterV_24_7_N())
            progressBar.setProgress(progress, animate)
        else
            progressBar.setProgress(progress)
    }
}