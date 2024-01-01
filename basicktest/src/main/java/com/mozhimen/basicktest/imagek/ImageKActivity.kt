package com.mozhimen.basicktest.imagek

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityImagekBinding

class ImageKActivity : BaseActivityVB<ActivityImagekBinding>() {
    fun goImageKCoil(view: View) {
        startContext<ImageKCoilActivity>()
    }
}