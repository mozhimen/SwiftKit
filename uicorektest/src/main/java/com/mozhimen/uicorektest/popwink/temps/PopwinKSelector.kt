package com.mozhimen.uicorektest.popwink.temps

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.animation.Animation
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.animk.builder.temps.TranslationType
import com.mozhimen.uicorek.popwink.bases.BasePopwinKLifecycle
import com.mozhimen.uicorek.viewk.wheel.ViewKWheel
import com.mozhimen.uicorek.viewk.wheel.temps.ArrayWheelAdapter
import com.mozhimen.uicorektest.R


/**
 * @ClassName PopwinSelector
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/31 13:10
 * @Version 1.0
 */
class PopwinKSelector(
    context: Context,
    private var _items: List<String>
) : BasePopwinKLifecycle(context) {
    private var _viewKWheel: ViewKWheel? = null

    init {
        setContentView(R.layout.popwink_selector)
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        _viewKWheel = contentView.findViewById(R.id.popwink_selector_wheel)
        _viewKWheel!!.apply {
            setCyclic(false)
            setItemSelectedListener { Log.d(TAG, "onViewCreated: $it") }
            setItems(_items)
        }
    }

    fun setItems(items: List<String>) {
        if (_viewKWheel == null || items.isEmpty()) return
        _viewKWheel!!.adapter = ArrayWheelAdapter(items.also { _items = items })
    }

    override fun onCreateShowAnimation(): Animation {
        return AnimKBuilder.asAnimation().add(TranslationType.FROM_BOTTOM_SHOW).build()
    }

    override fun onCreateDismissAnimation(): Animation {
        return AnimKBuilder.asAnimation().add(TranslationType.TO_BOTTOM_HIDE).build()
    }
}