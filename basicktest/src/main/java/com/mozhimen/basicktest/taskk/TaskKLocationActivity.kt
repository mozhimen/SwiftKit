package com.mozhimen.basicktest.taskk

import android.annotation.SuppressLint
import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.taskk.temps.TaskKLocation
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basicktest.databinding.ActivityTaskkLocationBinding

class TaskKLocationActivity : BaseActivityVB<ActivityTaskkLocationBinding>() {
    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    private val _taskKLocation by lazy { TaskKLocation().apply { bindLifecycle(this@TaskKLocationActivity) } }

    @SuppressLint("MissingPermission", "SetTextI18n")
    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        if (UtilKPermission.hasPermissions(arrayOf(CPermission.ACCESS_COARSE_LOCATION, CPermission.ACCESS_FINE_LOCATION)))
            _taskKLocation.startLocationTask(1000) { a1, a2 ->
                vb.taskkLocationTxt.text = "$a1, $a2"
            }
        else
            ManifestKPermission.requestPermissions(this, arrayOf(CPermission.ACCESS_COARSE_LOCATION, CPermission.ACCESS_FINE_LOCATION))
    }
}