package com.mozhimen.basicktest.elemk.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityElemkAndroidBinding

class ElemKAndroidActivity : BaseActivityVB<ActivityElemkAndroidBinding>() {

    fun goElemKGesture(view: View) {
        startContext<ElemKGestureActivity>()
    }

    fun goElemKGestureFling(view: View) {
        startContext<ElemKGestureFlingActivity>()
    }

    fun goElemKReceiver(view: View) {
        startContext<ElemKReceiverActivity>()
    }

    fun goElemKService(view: View) {
        startContext<ElemKServiceActivity>()
    }
}