package com.mozhimen.basick.lintk.optins

/**
 * @ClassName AOptNeedBindLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
@RequiresOptIn("The api is must call register dynamic. 需要动态注册的API", RequiresOptIn.Level.WARNING)
annotation class OApiCall_RegisterDynamic
