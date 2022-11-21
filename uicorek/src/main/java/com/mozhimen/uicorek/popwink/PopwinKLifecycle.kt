package com.mozhimen.uicorek.popwink

import android.content.Context
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.OnLifecycleEvent
import com.mozhimen.uicorek.popwink.basepopwin.basepopup.BasePopupWindow

/**
 * @ClassName PopwinKTip
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/31 23:09
 * @Version 1.0
 */
open class PopwinKLifecycle(context: Context) : BasePopupWindow(context), LifecycleOwner {
    private var _lifecycleRegistry: LifecycleRegistry? = null

    init {
        _lifecycleRegistry = LifecycleRegistry(this)
        _lifecycleRegistry?.currentState = Lifecycle.State.CREATED
    }

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        _lifecycleRegistry?.currentState = Lifecycle.State.STARTED
    }

    override fun onShowing() {
        super.onShowing()
        _lifecycleRegistry?.currentState = Lifecycle.State.RESUMED
    }

    override fun onDismiss() {
        super.onDismiss()
        _lifecycleRegistry?.currentState = Lifecycle.State.CREATED
    }

    override fun onDestroy() {
        _lifecycleRegistry?.currentState = Lifecycle.State.DESTROYED
        super.onDestroy()
    }

    override fun getLifecycle(): Lifecycle {
        return _lifecycleRegistry!!
    }
}