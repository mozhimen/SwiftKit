package com.mozhimen.basick.utilk.view.bar

import android.widget.SeekBar
import com.mozhimen.basick.elemk.view.commons.BaseOnSeekBarChangeListener

/**
 * @ClassName UtilKSeekBar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/25 13:21
 * @Version 1.0
 */
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