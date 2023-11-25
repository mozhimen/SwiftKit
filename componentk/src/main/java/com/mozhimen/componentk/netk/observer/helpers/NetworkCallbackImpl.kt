package com.mozhimen.componentk.netk.observer.helpers

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mozhimen.basick.elemk.android.content.bases.BaseConnectivityBroadcastReceiver
import com.mozhimen.basick.elemk.android.net.cons.CConnectivityManager
import com.mozhimen.basick.elemk.android.net.cons.CNetType
import com.mozhimen.basick.lintk.annors.ANetType
import com.mozhimen.basick.utilk.android.app.UtilKApplicationReflect
import com.mozhimen.basick.utilk.android.net.UtilKNetConn
import com.mozhimen.basick.utilk.android.net.eNetType2strNetType
import com.mozhimen.basick.utilk.android.net.networkCapabilities2netType
import com.mozhimen.basick.utilk.android.util.it
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.componentk.netk.observer.annors.ANetKObserver
import com.mozhimen.componentk.netk.observer.commons.INetKObserver
import java.lang.reflect.Method
import java.util.HashMap

/**
 * @ClassName NetworkCallbackImpl
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 10:52
 * @Version 1.0
 */
class NetworkCallbackImpl : ConnectivityManager.NetworkCallback(), IUtilK, INetKObserver {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    //////////////////////////////////////////////////////////////////////////////////

    private val _liveNetType: MutableLiveData<@ANetType String> = MutableLiveData()// 网络状态
    private val _checkManMap = HashMap<Any, Method>()// 观察者，key=类、value=方法
    private val _netStatusReceiver = NetStatusReceiver()// 网络状态广播监听

    //////////////////////////////////////////////////////////////////////////////////

    init {
        val intentFilter = IntentFilter().apply { addAction(CConnectivityManager.CONNECTIVITY_ACTION/*"android.net.conn.CONNECTIVITY_CHANGE"*/) }
        UtilKApplicationReflect.instance.applicationContext.registerReceiver(_netStatusReceiver, intentFilter)
        post(UtilKNetConn.getNetType().eNetType2strNetType())
    }

    //////////////////////////////////////////////////////////////////////////////////

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        "onAvailable: net connect success (网络已连接)".it(TAG)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        "onLost: net disconnect (网络已断开连接)".it(TAG)
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        val type = networkCapabilities.networkCapabilities2netType().eNetType2strNetType()
        "onCapabilitiesChanged: net status change (网络连接改变) $type".it(TAG)// 表明此网络连接成功验证
        if (type == _liveNetType.value) return
        post(type)
    }

    //////////////////////////////////////////////////////////////////////////////////

    override fun getNetType(): String =
        getLiveNetType().value ?: CNetType.UNKNOWN


    override fun getLiveNetType(): LiveData<String> =
        _liveNetType


    override fun register(obj: Any) {
        val clz = obj.javaClass
        if (!_checkManMap.containsKey(clz)) {
            val method = findAnnotationMethod(clz) ?: return
            _checkManMap[obj] = method
        }
    }

    override fun unRegister(obj: Any) {
        _checkManMap.remove(obj)
    }

    override fun unRegisterAll() {
        _checkManMap.clear()
    }

    //////////////////////////////////////////////////////////////////////////////////

    // 查找监听的方法
    private fun findAnnotationMethod(clazz: Class<*>): Method? {
        val methods = clazz.methods
        for (method in methods) {
            method.getAnnotation(ANetKObserver::class.java) ?: continue// 看是否有注解
            val genericReturnType = method.genericReturnType.toString()// 判断返回类型
            if ("void" != genericReturnType)
                throw RuntimeException("The return type of the method【${method.name}】 must be void!")// 方法的返回类型必须为void
            val parameterTypes = method.genericParameterTypes// 检查参数，必须有一个String型的参数
            if (parameterTypes.size != 1 || parameterTypes[0].toString() != "class ${String::class.java.name}")
                throw RuntimeException("The parameter types size of the method【${method.name}】must be one (type name must be java.lang.String)!")
            return method
        }
        return null
    }

    // 执行
    private fun post(str: @ANetType String) {
        _liveNetType.postValue(str)
        val set: Set<Any> = _checkManMap.keys
        for (obj in set) {
            val method = _checkManMap[obj] ?: continue
            invoke(obj, method, str)
        }
    }

    // 反射执行
    private fun invoke(obj: Any, method: Method, type: @ANetType String) {
        try {
            method.invoke(obj, type)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class NetStatusReceiver : BaseConnectivityBroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            context ?: return
            val type = UtilKNetConn.getNetType().eNetType2strNetType()
            if (type == _liveNetType.value) return
            post(type)
        }
    }

    private object INSTANCE {
        val holder = NetworkCallbackImpl()
    }
}