package com.mozhimen.basicktest.utilk.javax

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.postk.crypto.PostKCryptoAES
import com.mozhimen.basick.postk.crypto.mos.MCryptoAESConfig
import com.mozhimen.basicktest.databinding.ActivityUtilkEncryptBinding


/**
 * @ClassName UtilKEncryptActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/9 10:41
 * @Version 1.0
 */
class UtilKEncryptActivity : BaseActivityVDB<ActivityUtilkEncryptBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        val content = "Lovelive9."
        var pwd = ""
        var res = ""
        vdb.utilkEncryptTxt1.text = content
        vdb.utilkEncryptBtnEncrypt.setOnClickListener {
            pwd = PostKCryptoAES.with(MCryptoAESConfig(secretKey = "saaierForTodoKey", ivString = "ihaierForTodo_Iv")).encryptWithBase64(content)
            vdb.utilkEncryptTxt2.text = pwd
        }
        vdb.utilkEncryptBtnDecrypt.setOnClickListener {
            res = PostKCryptoAES.with(MCryptoAESConfig(secretKey = "saaierForTodoKey", ivString = "ihaierForTodo_Iv")).decryptWithBase64(pwd)
            vdb.utilkEncryptTxt3.text = res
        }
    }
}