package com.mozhimen.basick.utilk.exts

import android.widget.SeekBar
import com.mozhimen.basick.utilk.view.bar.UtilKSeekBar

fun SeekBar.setOnSeekBarChangeObserver(seekBarChangeListener: (progress: Int) -> Unit) {
    UtilKSeekBar.setOnSeekBarChangeObserver(this, seekBarChangeListener)
}

fun SeekBar.setOnSeekBarFinishObserver(seekBarChangeListener: (progress: Int) -> Unit) {
    UtilKSeekBar.setOnSeekBarFinishObserver(this, seekBarChangeListener)
}