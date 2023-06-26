package com.mozhimen.uicorek.popwink.builder

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.popwink.bases.commons.IClearMemoryListener
import com.mozhimen.uicorek.popwink.builder.mos.PopwinKBuilderConfig

/**
 * @ClassName QuickPopupBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/28 22:45
 * @Version 1.0
 */
class PopwinKBuilder(obj: Any) : IClearMemoryListener {

    private var _config: PopwinKBuilderConfig = PopwinKBuilderConfig.generateDefault()
    private var _popupHost: Any = obj
    private var _width = 0
    private var _height = 0

    fun getConfig(): PopwinKBuilderConfig {
        return _config
    }

    fun getPopupHost(): Any {
        return _popupHost
    }

    fun getWidth(): Int {
        return _width
    }

    fun getHeight(): Int {
        return _height
    }

    companion object {
        fun with(context: Context): PopwinKBuilder {
            return PopwinKBuilder(context)
        }

        fun with(fragment: Fragment): PopwinKBuilder {
            return PopwinKBuilder(fragment)
        }

        fun with(dialog: Dialog): PopwinKBuilder {
            return PopwinKBuilder(dialog)
        }
    }

    fun setContentViewLayoutId(resId: Int): PopwinKBuilder {
        _config.setContentViewLayoutId(resId)
        return this
    }

    fun setWidth(width: Int): PopwinKBuilder {
        this._width = width
        return this
    }

    fun setHeight(height: Int): PopwinKBuilder {
        this._height = height
        return this
    }

    fun setConfig(quickPopwinKConfig: PopwinKBuilderConfig): PopwinKBuilder {
        if (quickPopwinKConfig != _config) {
            quickPopwinKConfig.setContentViewLayoutId(_config.getContentViewLayoutId())
        }
        _config = quickPopwinKConfig
        return this
    }

    fun build(): PopwinKBuilderProxy {
        if (_popupHost is Context) {
            return PopwinKBuilderProxy(_popupHost as Context, this)
        }
        if (_popupHost is Fragment) {
            return PopwinKBuilderProxy(_popupHost as Fragment, this)
        }
        if (_popupHost is Dialog) {
            return PopwinKBuilderProxy(_popupHost as Dialog, this)
        }
        throw NullPointerException(UtilKRes.getString(R.string.base_popwink_host_destroyed))
    }

    fun show(): PopwinKBuilderProxy {
        return show(null)
    }

    fun show(anchorView: View?): PopwinKBuilderProxy {
        val quickPopup = build()
        quickPopup.showPopupWindow(anchorView)
        return quickPopup
    }

    fun show(x: Int, y: Int): PopwinKBuilderProxy {
        val quickPopup = build()
        quickPopup.showPopupWindow(x, y)
        return quickPopup
    }

    override fun clear(destroy: Boolean) {
        _config.clear(destroy)
    }
}