package com.mozhimen.basick.lintk.annors

import android.widget.Toast
import androidx.annotation.IntDef

/**
 * @ClassName @AToastDuration
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/7/12 21:35
 * @Version 1.0
 */
@IntDef(value = [Toast.LENGTH_SHORT, Toast.LENGTH_LONG])
annotation class AToastDuration
