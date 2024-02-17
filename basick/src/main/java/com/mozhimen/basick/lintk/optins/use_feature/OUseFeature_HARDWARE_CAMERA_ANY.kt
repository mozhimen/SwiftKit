package com.mozhimen.basick.lintk.optins.use_feature

import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CUseFeature

/**
 * @ClassName OUseFeature_Camera
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/2/5 23:56
 * @Version 1.0
 */
@AManifestKRequire(CUseFeature.HARDWARE_CAMERA_ANY)
@RequiresOptIn("The api is must add this UseFeature to your AndroidManifest.xml. 需要声明此UseFeature到你的AndroidManifest.xml", RequiresOptIn.Level.WARNING)
annotation class OUseFeature_HARDWARE_CAMERA_ANY
