package com.mozhimen.uicorektest.imagek

import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.uicorektest.databinding.ActivityImagekBindingAdapterBinding


/**
 * @ClassName ImageKBindingAdapterActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/11 14:12
 * @Version 1.0
 */
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
@APermissionCheck(CPermission.SYSTEM_ALERT_WINDOW)
class ImageKBindingAdapterActivity: BaseActivityVB<ActivityImagekBindingAdapterBinding>()