package com.mozhimen.basicktest.taskk

import android.annotation.SuppressLint
import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_COARSE_LOCATION
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_FINE_LOCATION
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.taskk.temps.TaskKLocation
import com.mozhimen.basick.utilk.wrapper.UtilKPermission
import com.mozhimen.basicktest.databinding.ActivityTaskkLocationBinding

class TaskKLocationActivity : BaseActivityVDB<ActivityTaskkLocationBinding>() {
    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    private val _taskKLocation by lazy_ofNone { TaskKLocation().apply { bindLifecycle(this@TaskKLocationActivity) } }

    @SuppressLint("MissingPermission", "SetTextI18n")
    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OPermission_ACCESS_FINE_LOCATION::class, OPermission_ACCESS_COARSE_LOCATION::class)
    override fun initView(savedInstanceState: Bundle?) {
        if (UtilKPermission.isSelfGranted(arrayOf(CPermission.ACCESS_COARSE_LOCATION, CPermission.ACCESS_FINE_LOCATION)))
            _taskKLocation.startLocationTask(1000) { a1, a2 ->
                vdb.taskkLocationTxt.text = "$a1, $a2"
            }
        else
            ManifestKPermission.requestPermissions(this, arrayOf(CPermission.ACCESS_COARSE_LOCATION, CPermission.ACCESS_FINE_LOCATION))
    }
}