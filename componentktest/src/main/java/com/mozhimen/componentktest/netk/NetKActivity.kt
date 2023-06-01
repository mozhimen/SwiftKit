package com.mozhimen.componentktest.netk

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.content.startContext
import com.mozhimen.componentktest.databinding.ActivityNetkBinding
import com.mozhimen.componentktest.netk.file.NetKFileActivity
import com.mozhimen.componentktest.netk.http.NetKHttpActivity

class NetKActivity : BaseActivityVB<ActivityNetkBinding>() {

    fun goNetKHttp(view: View) {
        startContext<NetKHttpActivity>()
    }

    fun goNetKFile(view: View) {
        startContext<NetKFileActivity>()
    }
}