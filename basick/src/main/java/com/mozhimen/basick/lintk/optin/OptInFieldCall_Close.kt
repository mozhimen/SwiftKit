package com.mozhimen.basick.lintk.optin

/**
 * @ClassName AOptNeedBindLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/10 10:36
 * @Version 1.0
 */
@RequiresOptIn("The field is must call < close() > to recycle.", RequiresOptIn.Level.WARNING)
annotation class OptInFieldCall_Close
