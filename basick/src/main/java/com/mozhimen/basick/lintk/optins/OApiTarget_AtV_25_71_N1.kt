package com.mozhimen.basick.lintk.optins

/**
 * @ClassName AOptNeedBindLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
@RequiresOptIn("The api you should set your target sdk as 25, because android 8 later all limited. 需要将目标SDK版本设置未25的API", RequiresOptIn.Level.WARNING)
annotation class OApiTarget_AtV_25_71_N1
