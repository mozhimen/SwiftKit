package com.mozhimen.componentk.navigatek.bar.helpers

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
    suspend fun <IN, OUT> loadNavigateKBar(
        onLoadRemote: suspend () -> String?,
        onTransRemote: (list: List<IN>) -> List<OUT>,
        onLoadLocale: () -> List<OUT>,
        onLoadFinish: (List<OUT>) -> Unit,
        typeOf: Type
    ) {
        withContext(Dispatchers.IO) {
            val strJsonRemote = onLoadRemote.invoke()//加载远程的配置
            if (strJsonRemote.isNullOrEmpty()) {//如果为空，判断本地配置是否为空，如果本地配置为空，加载默认配置
                withContext(Dispatchers.Main) {
                    onLoadFinish.invoke(onLoadLocale())
                }
            } else {//如果不为空，判断本地配置是否为空，如果本地配置为空，加载远程配置
                withContext(Dispatchers.Main) {
                    onLoadFinish.invoke(getLoadNavigationBar(strJsonRemote, onTransRemote, onLoadLocale, typeOf))
                }
            }
        }
    }

    fun <IN, OUT> getLoadNavigationBar(
        strJsonRemote: String,
        onTransRemote: (List<IN>) -> List<OUT>,
        onLoadLocale: () -> List<OUT>,
        typeOf: Type
    ): List<OUT> {
        val list: List<IN>? = try {
            UtilKGson.gson.fromJson<List<IN>>(strJsonRemote, typeOf)
        } catch (e: Exception) {
            null
        }//转换失败，返回默认的配置
        return if (list.isNullOrEmpty())
            onLoadLocale.invoke()
        else onTransRemote.invoke(list)
    }
}