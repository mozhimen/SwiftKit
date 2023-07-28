package com.mozhimen.basick.lintk.optin

/**
 * @ClassName AOptLazyInit
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/7 14:43
 * @Version 1.0
 */
@RequiresOptIn("The api need init in application.", RequiresOptIn.Level.WARNING)
annotation class OptInApiInit_InApplication
