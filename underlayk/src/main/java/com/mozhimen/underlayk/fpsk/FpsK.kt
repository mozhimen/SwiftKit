package com.mozhimen.underlayk.fpsk

import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.permissionk.annors.APermissionKRequire
import com.mozhimen.underlayk.fpsk.commons.IFpsKListener
import com.mozhimen.underlayk.fpsk.helpers.FpsKView

/**
 * @ClassName FpsK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/31 17:12
 * @Version 1.0
 */
@APermissionKRequire(CPermission.SYSTEM_ALERT_WINDOW)
class FpsK {
    private val _fpsKViewer by lazy { FpsKView() }

    fun toggleView() {
        _fpsKViewer.toggle()
    }

    fun setOnFrameListener(listener: IFpsKListener) {
        _fpsKViewer.addListener(listener)
    }

    companion object {
        @JvmStatic
        val instance = FpsKProvider.holder
    }

    private object FpsKProvider {
        val holder = FpsK()
    }
}