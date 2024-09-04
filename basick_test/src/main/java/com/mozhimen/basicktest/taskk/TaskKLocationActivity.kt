package com.mozhimen.basicktest.taskk

import android.annotation.SuppressLint
import android.os.Bundle
import com.mozhimen.mvvmk.bases.activity.databinding.BaseActivityVDB
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_ACCESS_COARSE_LOCATION
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_ACCESS_FINE_LOCATION
import com.mozhimen.kotlin.elemk.android.cons.CPermission
import com.mozhimen.manifestk.permission.ManifestKPermission
import com.mozhimen.taskk.temps.TaskKLocation
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM.lazy_ofNone
import com.mozhimen.kotlin.utilk.wrapper.UtilKPermission
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