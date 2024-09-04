package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import com.mozhimen.mvvmk.bases.activity.databinding.BaseActivityVDB
import com.mozhimen.kotlin.utilk.android.util.dp2px
import com.mozhimen.kotlin.utilk.androidx.core.UtilKRoundedBitmapDrawable
import com.mozhimen.basicktest.databinding.ActivityUtilkGraphicsBinding

/**
 * @ClassName UtilKGraphicsActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/1
 * @Version 1.0
 */
class UtilKGraphicsActivity : BaseActivityVDB<ActivityUtilkGraphicsBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vdb.utilkGraphicsImg.setImageDrawable(UtilKRoundedBitmapDrawable.get(this, com.mozhimen.basicktest.R.mipmap.utilk_img, 16f.dp2px()))
    }
}