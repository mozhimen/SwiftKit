package com.mozhimen.basick.lintk.optins

/**
 * @ClassName AOptNeedBindLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
@RequiresOptIn("The field is must call < close() > to recycle. 需要使用结束主动调用close的Field", RequiresOptIn.Level.WARNING)
annotation class OFieldCall_Close
