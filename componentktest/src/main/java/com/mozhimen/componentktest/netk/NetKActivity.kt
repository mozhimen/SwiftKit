package com.mozhimen.componentktest.netk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.start
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityNetkBinding
import com.mozhimen.componentktest.netk.file.NetKFileActivity
import com.mozhimen.componentktest.netk.http.NetKHttpActivity

class NetKActivity : BaseKActivityVB<ActivityNetkBinding>() {
    override fun initData(savedInstanceState: Bundle?) {

    }

    fun goNetKHttp(view: View) {
        start<NetKHttpActivity>()
    }

    fun goNetKFile(view: View) {
        start<NetKFileActivity>()
    }
}