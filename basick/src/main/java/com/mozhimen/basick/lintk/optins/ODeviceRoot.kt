package com.mozhimen.basick.lintk.optins

/**
 * @ClassName AOptNeedBindLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/10 10:36
 * @Version 1.0
 */
@RequiresOptIn("The api is need your device has rooted. 需要设备被root的API", RequiresOptIn.Level.WARNING)
annotation class ODeviceRoot
