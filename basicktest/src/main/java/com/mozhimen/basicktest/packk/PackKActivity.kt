package com.mozhimen.basicktest.packk

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityPackkBinding
import com.mozhimen.basicktest.packk.netk.conn.NetKConnActivity

class PackKActivity : BaseActivityVB<ActivityPackkBinding>() {
    fun goNetK(view: View) {
        startContext<NetKConnActivity>()
    }
}