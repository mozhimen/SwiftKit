package com.mozhimen.basicktest

import android.view.View
import com.mozhimen.kotlin.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.kotlin.utilk.android.content.startContext
import com.mozhimen.basicktest.animk.AnimKActivity
import com.mozhimen.basicktest.stackk.StackKActivity
import com.mozhimen.basicktest.utilk.UtilKActivity
import com.mozhimen.basicktest.databinding.ActivityBasickBinding
import com.mozhimen.basicktest.elemk.ElemKActivity
import com.mozhimen.basicktest.manifestk.ManifestKActivity
import com.mozhimen.basicktest.taskk.TaskKActivity

class BasicKActivity : BaseActivityVDB<ActivityBasickBinding>() {
    fun goAnimK(view: View) {
        startContext<AnimKActivity>()
    }

    fun goElemK(view: View) {
        startContext<ElemKActivity>()
    }

    fun goManifestK(view: View) {
        startContext<ManifestKActivity>()
    }

    fun goStackK(view: View) {
        startContext<StackKActivity>()
    }

    fun goTaskK(view: View) {
        startContext<TaskKActivity>()
    }

    fun goUtilK(view: View) {
        startContext<UtilKActivity>()
    }
}