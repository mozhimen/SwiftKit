package com.mozhimen.basicktest.utilk.java

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.java.io.encrypt.UtilKAES
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
        VB.utilkEncryptTxt1.text = content
        VB.utilkEncryptBtnEncrypt.setOnClickListener {
            pwd = UtilKAES.with(secretKey = "saaierForTodoKey", ivString = "ihaierForTodo_Iv").encryptWithBase64(content)
            VB.utilkEncryptTxt2.text = pwd
        }
        VB.utilkEncryptBtnDecrypt.setOnClickListener {
            res = UtilKAES.with(secretKey = "saaierForTodoKey", ivString = "ihaierForTodo_Iv").decryptWithBase64(pwd)
            VB.utilkEncryptTxt3.text = res
        }
    }
}