package com.mozhimen.uicorek.popwink.builder

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.uicorek.popwink.bases.BasePopwinK
import com.mozhimen.uicorek.popwink.bases.cons.CFlag
import com.mozhimen.uicorek.popwink.builder.commons.PopwinKBuilderOnClickCallback
import com.mozhimen.uicorek.popwink.builder.mos.PopwinKBuilderConfig
import java.lang.reflect.InvocationTargetException

/**
 * @ClassName QuickPopup
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/28 21:54
 * @Version 1.0
 */
class PopwinKBuilderProxy : BasePopwinK {
    private var _config: PopwinKBuilderConfig
    private var _builder: PopwinKBuilder

    constructor(fragment: Fragment, builder: PopwinKBuilder) : super(fragment, builder.getWidth(), builder.getHeight()) {
        _builder = builder
        _config = builder.getConfig()
        setContentView(_config.getContentViewLayoutId())
    }

    constructor(dialog: Dialog, builder: PopwinKBuilder) : super(dialog, builder.getWidth(), builder.getHeight()) {
        _builder = builder
        _config = builder.getConfig()
        setContentView(_config.getContentViewLayoutId())
    }

    constructor(context: Context, builder: PopwinKBuilder) : super(context, builder.getWidth(), builder.getHeight()) {
        _builder = builder
        _config = builder.getConfig()
        setContentView(_config.getContentViewLayoutId())
    }

    fun getConfig(): PopwinKBuilderConfig {
        return _config
    }

    fun getBuilder(): PopwinKBuilder {
        return _builder
    }

    private fun applyConfigSetting(config: PopwinKBuilderConfig) {
        if (config.getPopupBlurOption() != null) {
            setBlurOption(config.getPopupBlurOption())
        } else {
            setBlurBackgroundEnable(config.getFlag() and CFlag.BLUR_BACKGROUND != 0, config.getOnBlurOptionInitListener())
        }
        isPopupFadeEnable = config.getFlag() and CFlag.FADE_ENABLE != 0
        for ((methodName, value) in config.getInvokeParams()) {
            val method = config.getMethod(methodName)
            if (method != null) {
                try {
                    method.invoke(this, value)
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                    e.message?.et(TAG)
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                    e.message?.et(TAG)
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
                            if (event.first is PopwinKBuilderOnClickCallback) {
                                (event.first as PopwinKBuilderOnClickCallback).popwinKBuilderProxy = this@PopwinKBuilderProxy
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