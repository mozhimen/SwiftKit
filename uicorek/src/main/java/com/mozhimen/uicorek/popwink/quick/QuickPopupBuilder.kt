package com.mozhimen.uicorek.popwink.quick

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.popwink.bases.commons.ClearMemoryObject

/**
 * @ClassName QuickPopupBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/28 22:45
 * @Version 1.0
 */
class QuickPopupBuilder(obj: Any) : ClearMemoryObject {

    private var _config: QuickPopupConfig = QuickPopupConfig.generateDefault()
    private var _popupHost: Any = obj
    private var _width = 0
    private var _height = 0

    fun getConfig(): QuickPopupConfig {
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
        fun with(context: Context): QuickPopupBuilder {
            return QuickPopupBuilder(context)
        }

        fun with(fragment: Fragment): QuickPopupBuilder {
            return QuickPopupBuilder(fragment)
        }

        fun with(dialog: Dialog): QuickPopupBuilder {
            return QuickPopupBuilder(dialog)
        }
    }

    fun setContentViewLayoutId(resId: Int): QuickPopupBuilder {
        _config.setContentViewLayoutId(resId)
        return this
    }

    fun setWidth(width: Int): QuickPopupBuilder {
        this._width = width
        return this
    }

    fun setHeight(height: Int): QuickPopupBuilder {
        this._height = height
        return this
    }

    fun setConfig(quickPopupConfig: QuickPopupConfig): QuickPopupBuilder {
        if (quickPopupConfig != _config) {
            quickPopupConfig.setContentViewLayoutId(_config.getContentViewLayoutId())
        }
        _config = quickPopupConfig
        return this
    }

    fun build(): QuickPopup {
        if (_popupHost is Context) {
            return QuickPopup(_popupHost as Context, this)
        }
        if (_popupHost is Fragment) {
            return QuickPopup(_popupHost as Fragment, this)
        }
        if (_popupHost is Dialog) {
            return QuickPopup(_popupHost as Dialog, this)
        }
        throw NullPointerException(UtilKRes.getString(R.string.basepopup_host_destroyed))
    }

    fun show(): QuickPopup {
        return show(null)
    }

    fun show(anchorView: View?): QuickPopup {
        val quickPopup = build()
        quickPopup.showPopupWindow(anchorView)
        return quickPopup
    }

    fun show(x: Int, y: Int): QuickPopup {
        val quickPopup = build()
        quickPopup.showPopupWindow(x, y)
        return quickPopup
    }

    override fun clear(destroy: Boolean) {
        _config.clear(destroy)
    }
}