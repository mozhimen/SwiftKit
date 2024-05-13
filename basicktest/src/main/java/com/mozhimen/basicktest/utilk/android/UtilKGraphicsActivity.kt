package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.utilk.android.util.dp2px
import com.mozhimen.basick.utilk.androidx.core.UtilKRoundedBitmapDrawable
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