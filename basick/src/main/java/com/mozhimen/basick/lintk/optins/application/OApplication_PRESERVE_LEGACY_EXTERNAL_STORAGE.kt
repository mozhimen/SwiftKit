package com.mozhimen.basick.lintk.optins.application

import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication

/**
 * @ClassName OApplication_REQUEST_LEGACY_EXTERNAL_STORAGE
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/4
 * @Version 1.0
 */
@AManifestKRequire(CApplication.PRESERVE_LEGACY_EXTERNAL_STORAGE)
@RequiresOptIn("The api is must add this application property to your AndroidManifest.xml. 需要声明此application属性到你的AndroidManifest.xml application下", RequiresOptIn.Level.WARNING)
annotation class OApplication_PRESERVE_LEGACY_EXTERNAL_STORAGE