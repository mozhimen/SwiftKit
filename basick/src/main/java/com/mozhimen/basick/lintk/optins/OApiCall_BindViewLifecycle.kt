package com.mozhimen.basick.lintk.optins

/**
 * @ClassName AOptNeedBindLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/10 10:36
 * @Version 1.0
 */
@RequiresOptIn("The api is must call < bindLifecycle() > to avoid memory leak. 需要绑定生命周期的API (注意你如果使用的是Frgment, 你需要绑定的是viewLifecycle)", RequiresOptIn.Level.WARNING)
annotation class OApiCall_BindViewLifecycle
