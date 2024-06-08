package com.mozhimen.basick.utilk.android.security

import android.annotation.SuppressLint
import android.security.keystore.KeyGenParameterSpec
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKKeyGenParameterSpec
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/7
 * @Version 1.0
 */
object UtilKKeyGenParameterSpec {

    @SuppressLint("WrongConstant")
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    fun get(
        keyName: String,
        purposes: Int,
        blockModes: String?,
        encryptionPaddings: Array<String>?,
        userAuthenticationRequired: Boolean?,
        invalidatedByBiometricEnrollment: Boolean?
    ): KeyGenParameterSpec =
        KeyGenParameterSpec.Builder(keyName, purposes /*CKeyProperties.PURPOSE_ENCRYPT or CKeyProperties.PURPOSE_DECRYPT*/).run {
            blockModes?.let {
                setBlockModes(blockModes/*CKeyProperties.BLOCK_MODE_CBC*/)
            }
            encryptionPaddings?.let {
                setEncryptionPaddings(*encryptionPaddings/*CKeyProperties.ENCRYPTION_PADDING_PKCS7*/)
            }
            userAuthenticationRequired?.let {
                setUserAuthenticationRequired(userAuthenticationRequired/*true*/)
            }
            invalidatedByBiometricEnrollment?.let {
                if (UtilKBuildVersion.isAfterV_24_7_N()) {
                    // 在Android版本为N(24)以上时，可以使用此API
                    // true 表示用户录入新的生物识别信息后使当前的秘钥无效
                    setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment/*false*/)
                }
            }
            build()


        }
}