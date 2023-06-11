package com.mozhimen.basick.stackk.cb


import android.app.Activity
import com.mozhimen.basick.stackk.cb.helpers.StackKCbProxy
import com.mozhimen.basick.stackk.commons.IStackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.lang.ref.WeakReference

/**
 * @ClassName StackKMgr
 * @Description 提供前后台状态监听 以及栈顶activity的服务
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/20 21:58
 * @Version 1.0
 */
class StackKCb private constructor() : IStackK {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    private val _stackKCbProxy by lazy { StackKCbProxy() }

    override fun init() {
        _stackKCbProxy.init()
    }

    override fun getStackTopActivity(): Activity? =
        _stackKCbProxy.getStackTopActivity()

    override fun getStackTopActivity(onlyAlive: Boolean): Activity? =
        _stackKCbProxy.getStackTopActivity(onlyAlive)

    override fun addFrontBackListener(listener: IStackKListener) {
        _stackKCbProxy.addFrontBackListener(listener)
    }

    override fun removeFrontBackListener(listener: IStackKListener) {
        _stackKCbProxy.removeFrontBackListener(listener)
    }

    override fun getFrontBackListeners(): ArrayList<IStackKListener> =
        _stackKCbProxy.getFrontBackListeners()

    override fun finishAllActivity() {
        _stackKCbProxy.finishAllActivity()
    }

    override fun isFront(): Boolean =
        _stackKCbProxy.isFront()

    override fun getActivityRefs(): ArrayList<WeakReference<Activity>> =
        _stackKCbProxy.getActivityRefs()

    override fun getStackCount(): Int =
        _stackKCbProxy.getStackCount()

    override fun getLaunchCount(): Int =
        _stackKCbProxy.getLaunchCount()

    /////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = StackKCb()
    }
}