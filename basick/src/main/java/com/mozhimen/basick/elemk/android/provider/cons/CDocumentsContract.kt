package com.mozhimen.basick.elemk.android.provider.cons

import android.os.Build
import android.provider.DocumentsContract
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName CDocumentsContract
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/10 23:07
 * @Version 1.0
 */
object CDocumentsContract {
    @RequiresApi(CVersCode.V_26_8_O)
    const val EXTRA_INITIAL_URI = DocumentsContract.EXTRA_INITIAL_URI
}