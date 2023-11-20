package com.mozhimen.componentk.navigatek.bar.helpers

import android.content.IntentSender.OnFinished
import androidx.annotation.WorkerThread
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.commons.ISuspend_AListener
import com.mozhimen.basick.utilk.google.gson.UtilKGson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type

/**
 * @ClassName NavigateKBarHelper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/26 13:54
 * @Version 1.0
 */
object NavigateKBarHelper {
    @WorkerThread
    @JvmStatic
    suspend fun <OUT> onGenNavTabsOnBack(
        onLoadOUTsRemote: ISuspend_AListener<List<OUT>?>,
        onLoadOUTsLocale: ISuspend_AListener<List<OUT>>,
        onFinished: IA_Listener<List<OUT>>
    ) {
        withContext(Dispatchers.IO) {
            val outsRemote = onLoadOUTsRemote.invoke()
            if (outsRemote.isNullOrEmpty()) {
                val outsLocale = onLoadOUTsLocale.invoke()
                withContext(Dispatchers.Main) {
                    onFinished.invoke(outsLocale)
                }
            } else {
                withContext(Dispatchers.Main) {
                    onFinished.invoke(outsRemote)
                }
            }
        }
    }

//    @WorkerThread
//    @JvmStatic
//    suspend fun <IN, OUT> onGenerateNavigateKBarOnBack(
//        onRemoteLoadStrJson: suspend () -> String?,
//        onRemoteTrans: (list: List<IN>) -> List<OUT>,
//        onLocaleLoadOUTs: () -> List<OUT>,
//        onFinish: (List<OUT>) -> Unit,
//        typeOf: Type
//    ) {
//        withContext(Dispatchers.IO) {
//            val strJsonRemote = onRemoteLoadStrJson.invoke()//加载远程的配置
//            if (strJsonRemote.isNullOrEmpty()) {//如果为空，判断本地配置是否为空，如果本地配置为空，加载默认配置
//                withContext(Dispatchers.Main) {
//                    onFinish.invoke(onLocaleLoadOUTs())
//                }
//            } else {//如果不为空，判断本地配置是否为空，如果本地配置为空，加载远程配置
//                val list = getNavigateKBarOUTsOnBack(strJsonRemote, onRemoteTrans, onLocaleLoadOUTs, typeOf)
//                withContext(Dispatchers.Main) {
//                    onFinish.invoke(list)
//                }
//            }
//        }
//    }
//
//    @WorkerThread
//    fun <IN, OUT> getNavigateKBarOUTsOnBack(
//        strJsonRemote: String,
//        onRemoteTrans: (List<IN>) -> List<OUT>,
//        onLocaleLoad: () -> List<OUT>,
//        typeOf: Type
//    ): List<OUT> {
//        val list: List<IN>? = try {
//            UtilKGson.gson.fromJson<List<IN>>(strJsonRemote, typeOf)
//        } catch (e: Exception) {
//            null
//        }//转换失败，返回默认的配置
//        return if (list.isNullOrEmpty())
//            onLocaleLoad.invoke()
//        else onRemoteTrans.invoke(list)
//    }
}