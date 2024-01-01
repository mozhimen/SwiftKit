package com.mozhimen.basicktest.imagek

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.imagek.coil.loadImageCoil
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityImagekCoilBinding

class ImageKCoilActivity : BaseActivityVB<ActivityImagekCoilBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.imagekCoilImg1.loadImageCoil(R.mipmap.imagek_img_test)
    }
}