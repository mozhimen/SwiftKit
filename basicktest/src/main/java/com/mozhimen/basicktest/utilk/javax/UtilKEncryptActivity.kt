package com.mozhimen.basicktest.utilk.javax

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.javax.crypto.UtilKAES
import com.mozhimen.basicktest.databinding.ActivityUtilkEncryptBinding


/**
 * @ClassName UtilKEncryptActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/9 10:41
 * @Version 1.0
 */
class UtilKEncryptActivity : BaseActivityVB<ActivityUtilkEncryptBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        val content = "Lovelive9."
        var pwd: String = ""
        var res: String = ""
        vb.utilkEncryptTxt1.text = content
        vb.utilkEncryptBtnEncrypt.setOnClickListener {
            pwd = UtilKAES.with(secretKey = "saaierForTodoKey", ivString = "ihaierForTodo_Iv").encryptWithBase64(content)
            vb.utilkEncryptTxt2.text = pwd
        }
        vb.utilkEncryptBtnDecrypt.setOnClickListener {
            res = UtilKAES.with(secretKey = "saaierForTodoKey", ivString = "ihaierForTodo_Iv").decryptWithBase64(pwd)
            vb.utilkEncryptTxt3.text = res
        }
    }
}