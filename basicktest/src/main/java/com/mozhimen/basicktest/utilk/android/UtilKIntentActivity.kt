package com.mozhimen.basicktest.utilk.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.elemk.android.media.cons.CMediaFormat
import com.mozhimen.basick.utilk.android.content.UtilKContextStart
import com.mozhimen.basick.utilk.android.content.UtilKIntentWrapper
import com.mozhimen.basicktest.databinding.ActivityUtilkIntentBinding

/**
 * @ClassName UtilKIntentActivity
 * @Description TODO
 * @Author 83524
 * @Date 2023/4/16 20:51
 * @Version 1.0
 */
class UtilKIntentActivity : BaseActivityVB<ActivityUtilkIntentBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        val activityResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            vb.utilkIntentImg.setImageURI(it)
        }
        vb.utilkIntentBtn.setOnClickListener {
            UtilKContextStart.startActivityForResult(this, 0, UtilKIntentWrapper.getPickUriImage())
        }
        vb.utilkIntentBtn2.setOnClickListener {
            activityResultLauncher.launch(CMediaFormat.MIMETYPE_IMAGE_ALL)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            val uri = data?.data
            if (uri != null) {
                vb.utilkIntentImg.setImageURI(uri)
            }
        }
    }
}