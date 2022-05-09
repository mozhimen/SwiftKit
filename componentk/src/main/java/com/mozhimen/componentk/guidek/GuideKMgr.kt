package com.mozhimen.componentk.guidek

import androidx.lifecycle.MutableLiveData
import com.mozhimen.basicsk.cachek.CacheK
import com.mozhimen.basicsk.executork.ExecutorK
import com.mozhimen.basicsk.utilk.UtilKAssets
import com.mozhimen.basicsk.utilk.UtilKJson
import com.mozhimen.componentk.guidek.mos.*
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomMo
import java.util.*

/**
 * @ClassName GlideKMgr
 * @Description GlideKMgr
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/1 18:29
 * @Version 1.0
 */
class GuideKMgr {

    companion object {
        val instance = GlideKMgrProvider.holder
        private const val GUIDEK_CACHE_NAME_PKG_CONFIG = "guidek_cache_name_pkg_config"
    }

    private object GlideKMgrProvider {
        val holder = GuideKMgr()
    }

    @Volatile
    private var _currentConfig = MutableLiveData<GuideKPkgConfig>()

    fun init(defaultConfig: GuideKPkgConfig) {
        ExecutorK.execute({
            readDestinationsFromJson(defaultConfig)
        }, 0)
    }

    @Synchronized
    fun getConfig(): MutableLiveData<GuideKPkgConfig> = _currentConfig

    private fun readDestinationsFromJson(defaultConfig: GuideKPkgConfig) {
        if (!UtilKAssets.isFileExists(GuideKConstants.FILE_NAME_TAB_CONFIG)) {
            getPkgConfigFromCacheK()?.let { cacheConfig ->
                _currentConfig.postValue(cacheConfig)
            } ?: kotlin.run {
                _currentConfig.postValue(defaultConfig)
                savePkgConfig2CacheK(defaultConfig)
            }
        } else {
            val content = UtilKAssets.json2String(GuideKConstants.FILE_NAME_TAB_CONFIG)
            requireNotNull(content) { "parseFile guideKTabConfig.json fail!" }
            val destination = UtilKJson.fromJson(content, GuideKDestination::class.java)

            val jsonConfig = packageConfig(destination)
            getPkgConfigFromCacheK()?.let { cacheConfig ->
                if (jsonConfig.versionCode > cacheConfig.versionCode) {
                    _currentConfig.postValue(jsonConfig)
                    CacheK.saveCache(GUIDEK_CACHE_NAME_PKG_CONFIG, jsonConfig)
                } else {
                    _currentConfig.postValue(defaultConfig)
                }
            } ?: kotlin.run {
                _currentConfig.postValue(jsonConfig)
                savePkgConfig2CacheK(jsonConfig)
            }
        }
    }

    private fun packageConfig(destination: GuideKDestination): GuideKPkgConfig {
        val pkgPages = ArrayList<GuideKPkgPage>()
        for (index in destination.pages.indices) {
            val page = destination.pages.filter { page -> page.pageInfo.index == index }[0]
            if (!page.enable) continue
            var bottomMo: TabKBottomMo? = null
            var pageInfo: GuideKPageInfo?
            when (page.tabConfig.type) {
                GuideKConstants.ICONFONT_TEXT -> {
                    bottomMo = TabKBottomMo(
                        page.tabConfig.name,
                        page.tabConfig.iconFont,
                        page.tabConfig.iconNameDefault,
                        page.tabConfig.iconNameSelected,
                        page.tabConfig.iconColorDefault,
                        page.tabConfig.iconColorSelected
                    )
                }
                GuideKConstants.IMAGE -> {
                    bottomMo = TabKBottomMo(
                        page.tabConfig.name,
                        page.tabConfig.bitmapDefault,
                        page.tabConfig.bitmapSelected
                    )
                }
                GuideKConstants.IMAGE_TEXT -> {
                    bottomMo = TabKBottomMo(
                        page.tabConfig.name,
                        page.tabConfig.bitmapDefault,
                        page.tabConfig.bitmapSelected,
                        page.tabConfig.iconColorDefault,
                        page.tabConfig.iconColorSelected
                    )
                }
            }
            bottomMo?.let { mo ->
                pageInfo = GuideKPageInfo(
                    page.pageInfo.clazzName,
                    page.pageInfo.destType,
                    page.pageInfo.index,
                    page.pageInfo.id,
                    page.pageInfo.url
                )
                pkgPages.add(GuideKPkgPage(pageInfo!!, mo))
            }
        }
        return GuideKPkgConfig(destination.versionCode, destination.indexDefault, pkgPages)
    }

    private fun getPkgConfigFromCacheK(): GuideKPkgConfig? {
        return CacheK.getCache<GuideKPkgConfig>(GUIDEK_CACHE_NAME_PKG_CONFIG)
    }

    private fun savePkgConfig2CacheK(pkgConfig: GuideKPkgConfig) {
        return CacheK.saveCache(GUIDEK_CACHE_NAME_PKG_CONFIG, pkgConfig)
    }
}