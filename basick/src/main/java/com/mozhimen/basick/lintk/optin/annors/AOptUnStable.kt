package com.mozhimen.basick.lintk.optin.annors

/**
 * @ClassName AOptInUnstableApi
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/7 11:27
 * @Version 1.0
 */
@RequiresOptIn("The api is not stable.", RequiresOptIn.Level.WARNING)
annotation class AOptUnStable