package com.mozhimen.componentktest

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.componentktest.databinding.ActivityComponentkBinding
import com.mozhimen.componentktest.mediak.MediaKActivity
import com.mozhimen.componentktest.navigatek.NavigateKActivity
import com.mozhimen.componentktest.netk.NetKActivity
import com.mozhimen.componentktest.pagingk.PagingKActivity

class ComponentKActivity : BaseActivityVB<ActivityComponentkBinding>() {

    fun goMediaK(view: View) {
        startContext<MediaKActivity>()
    }

    fun goNavigateK(view: View) {
        startContext<NavigateKActivity>()
    }

    fun goNetK(view: View) {
        startContext<NetKActivity>()
    }

    fun goPagingK(view: View) {
        startContext<PagingKActivity>()
    }
}