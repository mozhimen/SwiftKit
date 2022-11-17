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
open class PopwinKLifecycle(context: Context) : BasePopupWindow(context) {
    //private var _lifecycleRegistry: LifecycleRegistry? = null

//    init {
//        _lifecycleRegistry = LifecycleRegistry(this)
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    override fun onViewCreated(contentView: View) {
//        super.onViewCreated(contentView)
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    override fun onShowing() {
//        super.onShowing()
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    override fun onDismiss() {
//        super.onDismiss()
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//    override fun onDestroy() {
//        super.onDestroy()
//    }
//
//    override fun getLifecycle(): Lifecycle {
//        return _lifecycleRegistry!!
//    }
}