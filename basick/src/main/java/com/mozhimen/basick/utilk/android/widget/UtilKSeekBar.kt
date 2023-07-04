package com.mozhimen.basick.utilk.android.widget

import android.widget.SeekBar
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.view.bases.BaseOnSeekBarChangeListener

/**
 * @ClassName UtilKSeekBar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/25 13:21
 * @Version 1.0
 */
fun SeekBar.setOnSeekBarChangeObserver(onSeekBarChange: IA_Listener<Int>/*(progress: Int) -> Unit*/) {
    UtilKSeekBar.setOnSeekBarChangeObserver(this, onSeekBarChange)
}

fun SeekBar.setOnSeekBarFinishObserver(onSeekBarChange: IA_Listener<Int>/*(progress: Int) -> Unit*/) {
    UtilKSeekBar.setOnSeekBarFinishObserver(this, onSeekBarChange)
}

object UtilKSeekBar {
    @JvmStatic
    fun setOnSeekBarChangeObserver(bar: SeekBar, onSeekBarChange: IA_Listener<Int>/*(progress: Int) -> Unit*/) {
        bar.setOnSeekBarChangeListener(object : BaseOnSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                onSeekBarChange(progress)
            }
        })
    }

    @JvmStatic
    fun setOnSeekBarFinishObserver(bar: SeekBar, onSeekBarChange: IA_Listener<Int>/*(progress: Int) -> Unit*/) {
        bar.setOnSeekBarChangeListener(object : BaseOnSeekBarChangeListener() {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                onSeekBarChange(seekBar.progress)
            }
        })
    }
}