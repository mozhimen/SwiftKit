package com.mozhimen.basick.lintk.optin.annors

/**
 * @ClassName AOptLazyInit
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/7 14:43
 * @Version 1.0
 */
@RequiresOptIn("The api need declare in < AndroidManifest,xml >.", RequiresOptIn.Level.WARNING)
annotation class ALintKOptIn_ApiDeclare_InManifest
