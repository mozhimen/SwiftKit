package com.mozhimen.basick.elemk.android.security.cons

import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName KeyProperties
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/7
 * @Version 1.0
 */
object CKeyProperties {
    @RequiresApi(CVersCode.V_23_6_M)
    const val PURPOSE_ENCRYPT = KeyProperties.PURPOSE_ENCRYPT
    @RequiresApi(CVersCode.V_23_6_M)
    const val PURPOSE_DECRYPT = KeyProperties.PURPOSE_DECRYPT
    @RequiresApi(CVersCode.V_23_6_M)
    const val BLOCK_MODE_CBC = KeyProperties.BLOCK_MODE_CBC
    @RequiresApi(CVersCode.V_23_6_M)
    const val ENCRYPTION_PADDING_PKCS7 = KeyProperties.ENCRYPTION_PADDING_PKCS7
    @RequiresApi(CVersCode.V_23_6_M)
    const val KEY_ALGORITHM_AES = KeyProperties.KEY_ALGORITHM_AES
}