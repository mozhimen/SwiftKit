package com.mozhimen.uicorektest.popwink.temps

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.animation.Animation
import com.mozhimen.basick.animk.builders.AnimKBuilder
import com.mozhimen.basick.animk.builders.temps.TranslationType
import com.mozhimen.uicorek.popwink.PopwinKLifecycle
import com.mozhimen.uicorektest.R


/**
 * @ClassName PopwinTest
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/31 13:09
 * @Version 1.0
 */
class PopwinKTest(
    context: Context,
    private val _constructor1: String,

    ) : PopwinKLifecycle(context) {
    interface IClickListener {
        fun onInvoke(log: String)
    }

    private var _variate1: String = "idel"
    private val _onClickListener = object : IClickListener {
        override fun onInvoke(log: String) {
            Log.d(TAG, "onInvoke: $log")
        }
    }

    init {
/*            _variate1 = "inited"
            _variate2 = "inited"
            _variate3 = "inited"
            _variate4 = "inited"*/
        setContentView(R.layout.popwink)
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        Log.d(TAG, "onViewCreated: _constructor1 $_constructor1")
        Log.d(TAG, "onViewCreated: _variate1 $_variate1")
        _onClickListener.onInvoke("onViewCreated")
    }

    override fun onBeforeShow(): Boolean {
        Log.d(TAG, "onBeforeShow: _constructor1 $_constructor1")
        Log.d(TAG, "onBeforeShow: _variate1 $_variate1")
        _onClickListener.onInvoke("onBeforeShow")
        return super.onBeforeShow()
    }

    override fun onShowing() {
        super.onShowing()
        Log.d(TAG, "onShowing: _constructor1 $_constructor1")
        Log.d(TAG, "onShowing: _variate1 $_variate1")
        _onClickListener.onInvoke("onShowing")
    }

    override fun onBeforeDismiss(): Boolean {
        Log.d(TAG, "onBeforeDismiss: _constructor1 $_constructor1")
        Log.d(TAG, "onBeforeDismiss: _variate1 $_variate1")
        _onClickListener.onInvoke("onBeforeDismiss")
        return super.onBeforeDismiss()
    }

    override fun onDismiss() {
        Log.d(TAG, "onDismiss: _constructor1 $_constructor1")
        Log.d(TAG, "onDismiss: _variate1 $_variate1")
        _onClickListener.onInvoke("onDismiss")
        super.onDismiss()
    }

    override fun onCreateShowAnimation(): Animation {
        return AnimKBuilder.asAnimation().add(TranslationType.FROM_TOP_SHOW).build()
    }

    override fun onCreateDismissAnimation(): Animation {
        return AnimKBuilder.asAnimation().add(TranslationType.TO_TOP_HIDE).build()
    }
}