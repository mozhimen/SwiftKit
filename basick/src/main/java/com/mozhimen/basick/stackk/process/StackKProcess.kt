package com.mozhimen.basick.stackk.process

import android.app.Activity
import com.mozhimen.basick.stackk.commons.IStackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.stackk.process.helpers.StackKProcessProxy
import java.lang.ref.WeakReference

/**
 * @ClassName StackKProcess
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/11 14:32
 * @Version 1.0
 */
class StackKProcess private constructor() : IStackK {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    private val _stackKProcessProxy by lazy { StackKProcessProxy() }

    /////////////////////////////////////////////////////////////////////////

    override fun init() {
        _stackKProcessProxy.init()
    }

    override fun getStackTopActivity(): Activity? =
        _stackKProcessProxy.getStackTopActivity()

    override fun getStackTopActivity(onlyAlive: Boolean): Activity? =
        _stackKProcessProxy.getStackTopActivity(onlyAlive)

    override fun addFrontBackListener(listener: IStackKListener) {
        _stackKProcessProxy.addFrontBackListener(listener)
    }

    override fun removeFrontBackListener(listener: IStackKListener) {
        _stackKProcessProxy.removeFrontBackListener(listener)
    }

    override fun getFrontBackListeners(): ArrayList<IStackKListener> =
        _stackKProcessProxy.getFrontBackListeners()

    override fun finishAllActivity() {
        _stackKProcessProxy.finishAllActivity()
    }

    override fun isFront(): Boolean =
        _stackKProcessProxy.isFront()

    override fun getActivityRefs(): ArrayList<WeakReference<Activity>> =
        _stackKProcessProxy.getActivityRefs()

    override fun getStackCount(): Int =
        _stackKProcessProxy.getStackCount()

    override fun getLaunchCount(): Int =
        _stackKProcessProxy.getLaunchCount()

    /////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = StackKProcess()
    }
}