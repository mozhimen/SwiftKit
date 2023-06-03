package com.mozhimen.basicktest.cachek

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.cachek.datastore.CacheKDS
import com.mozhimen.basick.cachek.shared_preferences.CacheKSP
import com.mozhimen.basick.cachek.shared_preferences.temps.CacheKSPDelegateString
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.content.startContext
import com.mozhimen.basicktest.databinding.ActivityCachekBinding


/**
 * @ClassName CacheKActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:56
 * @Version 1.0
 */
class CacheKActivity : BaseActivityVB<ActivityCachekBinding>() {

    fun goCachekDS(view: View) {
        startContext<CacheKDSActivity>()
    }

    fun goCachekRM(view: View) {
        startContext<CacheKRMActivity>()
    }

    fun goCachekSP(view: View) {
        startContext<CacheKSPActivity>()
    }
}