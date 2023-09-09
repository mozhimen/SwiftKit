package com.mozhimen.basick.elemk.android.app.bases

import android.app.Service
import android.os.RemoteCallbackList
import android.os.RemoteException
import com.mozhimen.basick.elemk.android.app.commons.IBaseServiceConnListener
import com.mozhimen.basick.elemk.android.app.commons.IBaseServiceResListener
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName BaseService
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/10 19:03
 * @Version 1.0
 */
abstract class BaseService : Service(), IA_Listener<Any>, IUtilK {
    protected val serviceResListeners = RemoteCallbackList<IBaseServiceResListener>()
    protected open val binder: IBaseServiceConnListener.Stub = BaseServiceBinder(serviceResListeners)

    override fun invoke(obj: Any) {
        val listenerSize = serviceResListeners.beginBroadcast()
        try {
            for (i in 0 until listenerSize) {
                when (obj) {
                    is Int -> {
                        serviceResListeners.getBroadcastItem(i).onResInt(obj)
                    }

                    is Long -> {
                        serviceResListeners.getBroadcastItem(i).onResLong(obj)
                    }

                    is Boolean -> {
                        serviceResListeners.getBroadcastItem(i).onResBoolean(obj)
                    }

                    is Float -> {
                        serviceResListeners.getBroadcastItem(i).onResFloat(obj)
                    }

                    is Double -> {
                        serviceResListeners.getBroadcastItem(i).onResDouble(obj)
                    }

                    is String -> {
                        serviceResListeners.getBroadcastItem(i).onResString(obj)
                    }

                    else -> {}
                }
            }
        } catch (e: RemoteException) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        serviceResListeners.finishBroadcast()
    }
}