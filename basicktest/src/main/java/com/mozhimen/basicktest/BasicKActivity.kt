package com.mozhimen.basicktest

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.content.startContext
import com.mozhimen.basicktest.animk.AnimKActivity
import com.mozhimen.basicktest.cachek.CacheKActivity
import com.mozhimen.basicktest.stackk.StackKActivity
import com.mozhimen.basicktest.utilk.UtilKActivity
import com.mozhimen.basicktest.databinding.ActivityBasickBinding
import com.mozhimen.basicktest.elemk.ElemKActivity
import com.mozhimen.basicktest.manifestk.ManifestKActivity
import com.mozhimen.basicktest.netk.conn.NetKConnActivity
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

    fun goManifestK(view: View) {
        startContext<ManifestKActivity>()
    }

    fun goNetK(view: View) {
        startContext<NetKConnActivity>()
    }

    fun goStackK(view: View) {
        startContext<StackKActivity>()
    }

    fun goStatusBarK(view: View) {
        startContext<com.mozhimen.basicktest.statusbark.StatusBarKActivity>()
    }

    fun goTaskK(view: View) {
        startContext<TaskKActivity>()
    }

    fun goUtilK(view: View) {
        startContext<UtilKActivity>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}