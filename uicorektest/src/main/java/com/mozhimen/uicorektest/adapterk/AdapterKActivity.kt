package com.mozhimen.uicorektest.adapterk

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.uicorektest.databinding.ActivityAdapterkBinding

class AdapterKActivity : BaseActivityVB<ActivityAdapterkBinding>() {
    fun goAdapterKRecycler(view: View) {
        startContext<AdapterKRecyclerActivity>()
    }

    fun goAdapterKRecyclerStuffed(view: View) {
        startContext<AdapterKRecyclerStuffedActivity>()
    }

    fun goAdapterKRecyclerVb2(view: View) {
        startContext<AdapterKRecyclerVB2Activity>()
    }

    fun goAdapterKRecyclerVb(view: View) {
        startContext<AdapterKRecyclerVBActivity>()
    }
}