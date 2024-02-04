package com.mozhimen.basick.lintk.optins

/**
 * @ClassName AOptLazyInit
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/7 14:43
 * @Version 1.0
 */
@RequiresOptIn("The api need declare in < AndroidManifest,xml >. 需要声明在AndroidManifest中", RequiresOptIn.Level.WARNING)
annotation class OApiDeclare_InManifest
