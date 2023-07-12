package com.mozhimen.basick.lintk.optin.annors

/**
 * @ClassName AOptNeedBindLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/10 10:36
 * @Version 1.0
 */
@RequiresOptIn("The api you should set your target sdk as 25, because android 8 later all limited.", RequiresOptIn.Level.WARNING)
annotation class ALintKOptIn_ApiTargetVersion_V_25_71_N1
