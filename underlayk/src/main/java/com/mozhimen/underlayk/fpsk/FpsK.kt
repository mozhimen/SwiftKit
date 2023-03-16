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
    private val _fpsKProxy = FpsKProxy()

    /**
     * 是否显示
     * @return Boolean
     */
    override fun isShow(): Boolean {
        return _fpsKProxy.isShow()
    }

    /**
     * 开/关
     */
    override fun toggle() {
        _fpsKProxy.toggle()
    }

    /**
     * 增加监听器
     * @param listener IFpsKListener
     */
    override fun addListener(listener: IFpsKListener) {
        _fpsKProxy.addListener(listener)
    }

    /**
     * 清空监听器
     */
    override fun removeListeners() {
        _fpsKProxy.removeListeners()
    }

    companion object {
        @JvmStatic
        val instance = FpsKProvider.holder
    }

    private object FpsKProvider {
        val holder = FpsK()
    }
}