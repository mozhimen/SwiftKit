package com.mozhimen.basicktest

import android.view.View
import com.mozhimen.mvvmk.bases.activity.databinding.BaseActivityVDB
import com.mozhimen.kotlin.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityBasickBinding
import com.mozhimen.basicktest.manifestk.ManifestKActivity
import com.mozhimen.basicktest.taskk.TaskKActivity

class BasicKActivity : BaseActivityVDB<ActivityBasickBinding>() {

    fun goManifestK(view: View) {
        startContext<ManifestKActivity>()
    }

    fun goTaskK(view: View) {
        startContext<TaskKActivity>()
    }
}