package com.mozhimen.basick.lintk.optins

/**
 * @ClassName AOptInUnstableApi
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
@RequiresOptIn("The api is not stable. 不稳定的API, 随时可能变更", RequiresOptIn.Level.WARNING)
annotation class OApiUnStable