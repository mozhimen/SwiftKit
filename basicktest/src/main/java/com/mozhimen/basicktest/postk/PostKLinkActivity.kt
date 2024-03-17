package com.mozhimen.basicktest.postk

import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basicktest.databinding.ActivityPostkLinkBinding
import android.content.Intent
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper

/**
 * @ClassName PostKLinkActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/11/17 10:38
 * @Version 1.0
 */
class PostKLinkActivity : BaseActivityVDB<ActivityPostkLinkBinding>() {
    override fun initData(savedInstanceState: android.os.Bundle?) {
        super.initData(savedInstanceState)
        val action = intent.getAction()
        if (action != null && action == Intent.ACTION_VIEW) {
            val data = intent.getData()
            if (data != null) {
                val name = data.getQueryParameter("type")
                val age = data.getQueryParameter("id")
                UtilKLogWrapper.dt(TAG, "getQueryParameter type=" + name + "id=" + age)
            }
        }
        intent.getStringExtra("type")?.also {
            UtilKLogWrapper.dt(TAG, "initData: type $it")
        }
        intent.getStringExtra("id")?.also {
            UtilKLogWrapper.dt(TAG, "initData: id $it")
        }
    }
}