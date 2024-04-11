package com.mozhimen.basick.lintk.optins

/**
 * @ClassName AOptNeedBindLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
@RequiresOptIn("The api is must call < bindLifecycle() > to avoid memory leak. 需要绑定生命周期的API", RequiresOptIn.Level.WARNING)
annotation class OApiCall_BindLifecycle
