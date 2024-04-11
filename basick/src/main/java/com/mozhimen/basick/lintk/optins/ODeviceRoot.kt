package com.mozhimen.basick.lintk.optins

/**
 * @ClassName AOptNeedBindLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
@RequiresOptIn("The api is need your device has rooted. 需要设备被root的API", RequiresOptIn.Level.WARNING)
annotation class ODeviceRoot
