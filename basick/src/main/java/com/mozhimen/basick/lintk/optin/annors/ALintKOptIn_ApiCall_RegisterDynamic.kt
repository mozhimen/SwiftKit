package com.mozhimen.basick.lintk.optin.annors

/**
 * @ClassName AOptNeedBindLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/10 10:36
 * @Version 1.0
 */
@RequiresOptIn("The api is must call register dynamic 需要动态注册.", RequiresOptIn.Level.WARNING)
annotation class ALintKOptIn_ApiCall_RegisterDynamic
