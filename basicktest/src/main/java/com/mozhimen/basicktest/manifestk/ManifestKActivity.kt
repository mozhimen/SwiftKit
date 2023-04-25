package com.mozhimen.basicktest.manifestk

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.startContext
import com.mozhimen.basicktest.databinding.ActivityManifestkBinding
import com.mozhimen.basicktest.manifestk.permission.ManifestKPermissionActivity


/**
 * @ClassName ManifestKActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/12 14:17
 * @Version 1.0
 */
class ManifestKActivity : BaseActivityVB<ActivityManifestkBinding>() {
    fun goManifestKPermission(view: View) {
        startContext<ManifestKPermissionActivity>()
    }
}