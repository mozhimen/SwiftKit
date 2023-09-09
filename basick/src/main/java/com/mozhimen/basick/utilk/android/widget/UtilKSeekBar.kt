package com.mozhimen.basick.utilk.android.widget

import android.widget.SeekBar
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.android.view.commons.IOnSeekBarChangeListener

/**
 * @ClassName UtilKSeekBar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/25 13:21
 * @Version 1.0
 */
fun SeekBar.applySeekBarChangeObserver(onSeekBarChange: IA_Listener<Int>/*(progress: Int) -> Unit*/) {
    UtilKSeekBar.applySeekBarChangeObserver(this, onSeekBarChange)
}

fun SeekBar.applySeekBarFinishObserver(onSeekBarChange: IA_Listener<Int>/*(progress: Int) -> Unit*/) {
    UtilKSeekBar.applySeekBarFinishObserver(this, onSeekBarChange)
}

object UtilKSeekBar {
    @JvmStatic
    fun applySeekBarChangeObserver(bar: SeekBar, onSeekBarChange: IA_Listener<Int>/*(progress: Int) -> Unit*/) {
        bar.setOnSeekBarChangeListener(object : IOnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                onSeekBarChange(progress)
            }
        })
    }

    @JvmStatic
    fun applySeekBarFinishObserver(bar: SeekBar, onSeekBarChange: IA_Listener<Int>/*(progress: Int) -> Unit*/) {
        bar.setOnSeekBarChangeListener(object : IOnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                onSeekBarChange(seekBar.progress)
            }
        })
    }
}