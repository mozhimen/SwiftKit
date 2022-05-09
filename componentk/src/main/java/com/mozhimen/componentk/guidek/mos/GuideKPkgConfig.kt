package com.mozhimen.componentk.guidek.mos

import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomMo
import java.io.Serializable

/**
 * @ClassName GuideKPkgConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/3 10:26
 * @Version 1.0
 */
data class GuideKPkgConfig(
    val versionCode: Int,
    val indexDefault: Int,
    val pkgPages: List<GuideKPkgPage>
) : Serializable

data class GuideKPkgPage(
    val pageInfo: GuideKPageInfo,
    val tabKBottomMo: TabKBottomMo
) : Serializable
