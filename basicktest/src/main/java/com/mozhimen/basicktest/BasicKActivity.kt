package com.mozhimen.basicktest

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.animk.AnimKActivity
import com.mozhimen.basicktest.cachek.CacheKActivity
import com.mozhimen.basicktest.stackk.StackKActivity
import com.mozhimen.basicktest.utilk.UtilKActivity
import com.mozhimen.basicktest.databinding.ActivityBasickBinding
import com.mozhimen.basicktest.elemk.ElemKActivity
import com.mozhimen.basicktest.imagek.ImageKActivity
import com.mozhimen.basicktest.manifestk.ManifestKActivity
import com.mozhimen.basicktest.postk.PostKActivity
import com.mozhimen.basicktest.taskk.TaskKActivity

class BasicKActivity : BaseActivityVB<ActivityBasickBinding>() {
    fun goAnimK(view: View) {
        startContext<AnimKActivity>()
    }

    fun goCacheK(view: View) {
        startContext<CacheKActivity>()
    }

    fun goElemK(view: View) {
        startContext<ElemKActivity>()
    }

    fun goImageK(view: View) {
        startContext<ImageKActivity>()
    }

    fun goManifestK(view: View) {
        startContext<ManifestKActivity>()
    }

    fun goPostK(view: View) {
        startContext<PostKActivity>()
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