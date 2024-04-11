package com.mozhimen.basick.elemk.android.os.bases

import android.os.Looper
import java.lang.ref.WeakReference

/**
 * @ClassName UtilKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
open class BaseWeakRefMainHandler<T>(obj: T) : BaseWeakRefHandler<T>(WeakReference(obj), Looper.getMainLooper())