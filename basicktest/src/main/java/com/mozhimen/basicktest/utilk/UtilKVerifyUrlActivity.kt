package com.mozhimen.basicktest.utilk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.utilk.UtilKVerifyUrl
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkVerifyUrlBinding

class UtilKVerifyUrlActivity : BaseKActivity<ActivityUtilkVerifyUrlBinding,BaseKViewModel>(R.layout.activity_utilk_verify_url) {
    override fun initData(savedInstanceState: Bundle?) {
        vb.utilkVerifyUrlBtnPort.setOnClickListener {
            "是否合法: ${UtilKVerifyUrl.isPortValid(vb.utilkVerifyUrlTxtPort.text.toString())}".showToast()
        }
    }
}