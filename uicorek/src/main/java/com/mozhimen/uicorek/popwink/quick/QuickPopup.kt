package com.mozhimen.uicorek.popwink.quick

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.mozhimen.uicorek.popwink.basepopwin.basepopup.BasePopupFlag
import com.mozhimen.uicorek.popwink.basepopwin.basepopup.BasePopupWindow
import com.mozhimen.uicorek.popwink.quick.commons.QuickPopupOnClickCallback
import java.lang.reflect.InvocationTargetException

/**
 * @ClassName QuickPopup
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/28 21:54
 * @Version 1.0
 */
class QuickPopup : BasePopupWindow {
    private var _config: QuickPopupConfig
    private var _builder: QuickPopupBuilder

    constructor(fragment: Fragment, builder: QuickPopupBuilder) : super(fragment, builder.getWidth(), builder.getHeight()) {
        _builder = builder
        _config = builder.getConfig()
        setContentView(_config.getContentViewLayoutId())
    }

    constructor(dialog: Dialog, builder: QuickPopupBuilder) : super(dialog, builder.getWidth(), builder.getHeight()) {
        _builder = builder
        _config = builder.getConfig()
        setContentView(_config.getContentViewLayoutId())
    }

    constructor(context: Context, builder: QuickPopupBuilder) : super(context, builder.getWidth(), builder.getHeight()) {
        _builder = builder
        _config = builder.getConfig()
        setContentView(_config.getContentViewLayoutId())
    }

    fun getConfig(): QuickPopupConfig {
        return _config
    }

    fun getBuilder(): QuickPopupBuilder {
        return _builder
    }

    private fun applyConfigSetting(config: QuickPopupConfig) {
        if (config.getPopupBlurOption() != null) {
            setBlurOption(config.getPopupBlurOption())
        } else {
            setBlurBackgroundEnable(config.getFlag() and BasePopupFlag.BLUR_BACKGROUND != 0, config.getOnBlurOptionInitListener())
        }
        isPopupFadeEnable = config.getFlag() and BasePopupFlag.FADE_ENABLE != 0
        for ((methodName, value) in config.getInvokeParams()) {
            val method = config.getMethod(methodName)
            if (method != null) {
                try {
                    method.invoke(this, value)
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }
            }
        }
        performClick()
    }

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        applyConfigSetting(_config)
    }

    override fun onDestroy() {
        _builder.clear(true)
        super.onDestroy()
    }

    private fun performClick() {
        val eventsMap = _config.getListenersHolderMap()
        if (eventsMap == null || eventsMap.isEmpty()) return
        for ((viewId, event) in eventsMap) {
            val view: View? = findViewById(viewId)
            if (view != null) {
                if (event.second) {
                    view.setOnClickListener { v ->
                        if (event.first != null) {
                            if (event.first is QuickPopupOnClickCallback) {
                                (event.first as QuickPopupOnClickCallback).quickPopup = this@QuickPopup
                            }
                            event.first!!.onClick(v)
                        }
                        dismiss()
                    }
                } else view.setOnClickListener(event.first)
            }
        }
    }
}