package com.mozhimen.componentktest.netk

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.extsk.start
import com.mozhimen.componentktest.databinding.ActivityNetkBinding
import com.mozhimen.componentktest.netk.file.NetKFileActivity
import com.mozhimen.componentktest.netk.http.NetKHttpActivity

class NetKActivity : BaseActivityVB<ActivityNetkBinding>() {

    fun goNetKHttp(view: View) {
        start<NetKHttpActivity>()
    }

    fun goNetKFile(view: View) {
        start<NetKFileActivity>()
    }
}