package com.mozhimen.basick.lintk.optins

/**
 * @ClassName AOptLazyInit
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/7 14:43
 * @Version 1.0
 */
@RequiresOptIn("The api need init in application. 需要在Application初始化的API", RequiresOptIn.Level.WARNING)
annotation class OApiInit_InApplication
