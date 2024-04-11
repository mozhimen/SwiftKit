package com.mozhimen.basick.elemk.android.hardware.commons

import android.hardware.display.DisplayManager.DisplayListener

/**
 * @ClassName IDisplayListener
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Version 1.0
 */
interface IDisplayListener : DisplayListener {
    override fun onDisplayAdded(displayId: Int) {}
    override fun onDisplayRemoved(displayId: Int) {}
    override fun onDisplayChanged(displayId: Int) {}
}