package com.mozhimen.componentktest.netk

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.componentk.netk.observer.NetKObserver
import com.mozhimen.componentktest.databinding.ActivityNetkBinding
import com.mozhimen.componentktest.netk.connection.NetKConnectionActivity
import com.mozhimen.componentktest.netk.file.NetKFileActivity
import com.mozhimen.componentktest.netk.http.NetKHttpActivity
import com.mozhimen.componentktest.netk.observer.NetKObserverActivity

class NetKActivity : BaseActivityVB<ActivityNetkBinding>() {

    fun goNetConn(view: View) {
        startContext<NetKConnectionActivity>()
    }

    fun goNetKHttp(view: View) {
        startContext<NetKHttpActivity>()
    }

    fun goNetKFile(view: View) {
        startContext<NetKFileActivity>()
    }

    fun goNetKObserver(view: View) {
        startContext<NetKObserverActivity>()
    }
}