package com.mozhimen.basick.utilk.exts

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import com.mozhimen.basick.utilk.graphics.UtilKDrawable

fun Drawable.applyColorFilter(colorResId: Int) {
    UtilKDrawable.applyColorFilter(this, colorResId)
}
