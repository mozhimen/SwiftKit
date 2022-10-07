package com.mozhimen.underlayk.fpsk

import com.mozhimen.underlayk.fpsk.commons.IFpsKListener

/**
 * @ClassName FpsK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/31 17:12
 * @Version 1.0
 */
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