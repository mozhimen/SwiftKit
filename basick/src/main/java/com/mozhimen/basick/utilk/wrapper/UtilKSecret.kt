package com.mozhimen.basick.utilk.wrapper

import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.security.cons.CKeyProperties
import com.mozhimen.basick.utilk.java.security.UtilKKeyStore
import com.mozhimen.basick.utilk.javax.crypto.UtilKKeyGenerator
import javax.crypto.SecretKey

/**
 * @ClassName UtilkSignature
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/7
 * @Version 1.0
 */
object UtilKSecret {
    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun generateKey(keyName: String): SecretKey =
        UtilKKeyStore.getKey(keyName, "AndroidKeyStore") ?: UtilKKeyGenerator.getKey(
            CKeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore", keyName, CKeyProperties.PURPOSE_ENCRYPT or CKeyProperties.PURPOSE_DECRYPT, CKeyProperties.BLOCK_MODE_CBC,
            arrayOf(CKeyProperties.ENCRYPTION_PADDING_PKCS7), true, false
        )
}