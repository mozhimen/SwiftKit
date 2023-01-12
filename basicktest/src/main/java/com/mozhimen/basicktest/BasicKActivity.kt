package com.mozhimen.basicktest

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
import com.mozhimen.basicktest.stackk.StackKActivity
import com.mozhimen.basicktest.utilk.UtilKActivity
import com.mozhimen.basicktest.databinding.ActivityBasickBinding
import com.mozhimen.basicktest.elemk.ElemKActivity
import com.mozhimen.basicktest.manifestk.ManifestKActivity
import com.mozhimen.basicktest.taskk.TaskKActivity

class BasicKActivity : BaseActivityVB<ActivityBasickBinding>() {

    fun goElemK(view: View) {
        start<ElemKActivity>()
    }

    fun goManifestK(view: View) {
        start<ManifestKActivity>()
    }

    fun goStackK(view: View) {
        start<StackKActivity>()
    }

    fun goTaskK(view: View) {
        start<TaskKActivity>()
    }

    fun goUtilK(view: View) {
        start<UtilKActivity>()
    }

}