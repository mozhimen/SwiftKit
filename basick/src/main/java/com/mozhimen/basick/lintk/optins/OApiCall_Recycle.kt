package com.mozhimen.basick.lintk.optins

/**
 * @ClassName OApiCall_ViewReady
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/28
 * @Version 1.0
 */
@RequiresOptIn("The api is must call recycle when view detached from window. 需要View显示在前台的API", RequiresOptIn.Level.WARNING)
annotation class OApiCall_Recycle