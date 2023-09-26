package com.mozhimen.componentk.navigatek.bar.helpers

import com.mozhimen.basick.utilk.google.gson.UtilKGson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type
import java.util.ArrayList

/**
 * @ClassName NavigateKBarHelper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/26 13:54
 * @Version 1.0
 */
object NavigateKBarHelper {
    suspend fun <IN, OUT> loadNavigateKBar(
        transDestination: (list: List<IN>) -> List<OUT>,
        loadRemoteConfig: suspend () -> String?,
        loadLocaleConfig: () -> List<OUT>,
        onLoadFinish: (List<OUT>) -> Unit,
        typeOf: Type
    ) {
        withContext(Dispatchers.IO) {
            //加载远程的配置
            val remoteConfigJson = loadRemoteConfig.invoke()
            //如果为空，判断本地配置是否为空，如果本地配置为空，加载默认配置
            //如果不为空，判断本地配置是否为空，如果本地配置为空，加载远程配置
            if (remoteConfigJson.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    onLoadFinish.invoke(
                        loadLocaleConfig()
                    )
                }
            } else {
                withContext(Dispatchers.Main) {
                    onLoadFinish.invoke(
                        createNavigateKBar(
                            remoteConfigJson,
                            transDestination,
                            loadLocaleConfig,
                            typeOf
                        )
                    )
                }
            }
        }
    }

    private fun <IN, OUT> createNavigateKBar(
        remoteConfigJson: String,
        createNavigationBar: (list: List<IN>) -> List<OUT>,
        loadDefaultConfig: () -> List<OUT>,
        typeOf: Type
    ): List<OUT> {
        val list = try {
            UtilKGson.gson.fromJson<ArrayList<IN>>(
                remoteConfigJson,
                typeOf
            )
        } catch (e: Exception) {
            null
        }

        //转换失败，返回默认的配置
        return if (list.isNullOrEmpty()) {
            loadDefaultConfig.invoke()
        } else createNavigationBar(list)
    }
}