package com.mozhimen.basick.elemk.android.widget.annors

import androidx.annotation.IntDef
import com.mozhimen.basick.elemk.android.widget.cons.CToast

/**
 * @ClassName @AToastDuration
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/7/12 21:35
 * @Version 1.0
 */
@IntDef(value = [CToast.LENGTH_SHORT, CToast.LENGTH_LONG])
@Retention(AnnotationRetention.SOURCE)
annotation class AToastDuration
