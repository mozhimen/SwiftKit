package com.mozhimen.underlayk.fpsk

import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.underlayk.fpsk.commons.IFpsK
import com.mozhimen.underlayk.fpsk.commons.IFpsKListener
import com.mozhimen.underlayk.fpsk.helpers.FpsKProxy

/**
 * @ClassName FpsK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/31 17:12
 * @Version 1.0
 */
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
class FpsK : IFpsK {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    private val _fpsKProxy by lazy { FpsKProxy() }

    override fun isOpen(): Boolean {
        return _fpsKProxy.isOpen()
    }

    override fun toggle() {
        _fpsKProxy.toggle()
    }

    override fun addListener(listener: IFpsKListener) {
        _fpsKProxy.addListener(listener)
    }

    override fun removeListeners() {
        _fpsKProxy.removeListeners()
    }

    private object INSTANCE {
        val holder = FpsK()
    }
}