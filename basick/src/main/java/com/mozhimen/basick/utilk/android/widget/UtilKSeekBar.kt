package com.mozhimen.basick.utilk.android.widget

import android.widget.SeekBar
import com.mozhimen.basick.elemk.view.commons.BaseOnSeekBarChangeListener

/**
 * @ClassName UtilKSeekBar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/25 13:21
 * @Version 1.0
 */
fun SeekBar.setOnSeekBarChangeObserver(seekBarChangeListener: (progress: Int) -> Unit) {
    UtilKSeekBar.setOnSeekBarChangeObserver(this, seekBarChangeListener)
}

fun SeekBar.setOnSeekBarFinishObserver(seekBarChangeListener: (progress: Int) -> Unit) {
    UtilKSeekBar.setOnSeekBarFinishObserver(this, seekBarChangeListener)
}

object UtilKSeekBar {
    @JvmStatic
    fun setOnSeekBarChangeObserver(bar: SeekBar, seekBarChangeListener: (progress: Int) -> Unit) {
        bar.setOnSeekBarChangeListener(object : BaseOnSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                seekBarChangeListener(progress)
            }
        })
    }

    @JvmStatic
    fun setOnSeekBarFinishObserver(bar: SeekBar, seekBarChangeListener: (progress: Int) -> Unit) {
        bar.setOnSeekBarChangeListener(object : BaseOnSeekBarChangeListener() {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                seekBarChangeListener(seekBar.progress)
            }
        })
    }
}