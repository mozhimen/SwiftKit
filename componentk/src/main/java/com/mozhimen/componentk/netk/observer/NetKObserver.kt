package com.mozhimen.componentk.netk.observer

import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import com.mozhimen.basick.utilk.android.app.UtilKApplicationReflect
import com.mozhimen.basick.utilk.android.net.UtilKConnectivityManager
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.componentk.netk.observer.commons.INetKObserver
import com.mozhimen.componentk.netk.observer.helpers.NetworkCallbackImpl

/**
 * @ClassName NetKObserver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 14:08
 * @Version 1.0
 */
class NetKObserver : BaseUtilK(), INetKObserver {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private val _networkCallbackImpl = NetworkCallbackImpl()

    ////////////////////////////////////////////////////////////////////////////////////////////

    init {
        UtilKConnectivityManager.registerNetworkCallback(_context, NetworkRequest.Builder().build(), _networkCallbackImpl)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    fun init() {
        //无操作, 初始化
    }

    override fun getNetType(): String =
        _networkCallbackImpl.getNetType()

    override fun getLiveNetType(): LiveData<String> =
        _networkCallbackImpl.getLiveNetType()

    override fun register(obj: Any) {
        _networkCallbackImpl.register(obj)
    }

    override fun unRegister(obj: Any) {
        _networkCallbackImpl.unRegister(obj)
    }

    override fun unRegisterAll() {
        _networkCallbackImpl.unRegisterAll()
        UtilKConnectivityManager.unregisterNetworkCallback(UtilKApplicationReflect.instance.applicationContext, _networkCallbackImpl)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = NetKObserver()
    }
}