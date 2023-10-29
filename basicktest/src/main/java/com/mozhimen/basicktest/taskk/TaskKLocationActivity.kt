package com.mozhimen.basicktest.taskk

import android.annotation.SuppressLint
import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.taskk.temps.TaskKLocation
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basicktest.databinding.ActivityTaskkLocationBinding

class TaskKLocationActivity : BaseActivityVB<ActivityTaskkLocationBinding>() {
    @OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class)
    private val _taskKLocation by lazy { TaskKLocation().apply { bindLifecycle(this@TaskKLocationActivity) } }

    @SuppressLint("MissingPermission", "SetTextI18n")
    @OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        if (UtilKPermission.hasPermissions(arrayOf(CPermission.ACCESS_COARSE_LOCATION, CPermission.ACCESS_FINE_LOCATION)))
            _taskKLocation.startLocationTask(1000) { a1, a2 ->
                vb.taskkLocationTxt.text = "$a1, $a2"
            }
        else
            ManifestKPermission.requestPermissions(this, arrayOf(CPermission.ACCESS_COARSE_LOCATION, CPermission.ACCESS_FINE_LOCATION))
    }
}