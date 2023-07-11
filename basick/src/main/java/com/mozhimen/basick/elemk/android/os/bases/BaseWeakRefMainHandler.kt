package com.mozhimen.basick.elemk.android.os.bases

import android.os.Looper
import java.lang.ref.WeakReference

/**
 * @ClassName UtilKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 17:56
 * @Version 1.0
 */
open class BaseWeakRefMainHandler<T>(obj: T) : BaseWeakRefHandler<T>(Looper.getMainLooper(), WeakReference(obj))