package com.mozhimen.xmlk.optins

import com.mozhimen.kotlin.lintk.annors.AManifestRequire
import com.mozhimen.xmlk.cons.CTheme

/**
 * @ClassName OTheme_ThemeMaterial3DayNoAction
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/3 23:16
 * @Version 1.0
 */
@AManifestRequire(CTheme.ThemeMaterial3DayNoAction)
@RequiresOptIn("The api is must add this theme to your AndroidManifest.xml. 需要声明此主题到你的AndroidManifest.xml", RequiresOptIn.Level.WARNING)
annotation class OTheme_ThemeMaterial3DayNoAction