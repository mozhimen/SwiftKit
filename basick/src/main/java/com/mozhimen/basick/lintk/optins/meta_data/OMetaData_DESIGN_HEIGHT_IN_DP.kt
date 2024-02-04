package com.mozhimen.basick.lintk.optins.meta_data

import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CMetaData

/**
 * @ClassName OMetaData_DESIGN_WIDTH_IN_DP
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/4
 * @Version 1.0
 */
@AManifestKRequire(CMetaData.DESIGN_HEIGHT_IN_DP)
@RequiresOptIn("The api is must add this metadata to your AndroidManifest.xml. 需要声明此Metadata到你的AndroidManifest.xml", RequiresOptIn.Level.WARNING)
annotation class OMetaData_DESIGN_HEIGHT_IN_DP