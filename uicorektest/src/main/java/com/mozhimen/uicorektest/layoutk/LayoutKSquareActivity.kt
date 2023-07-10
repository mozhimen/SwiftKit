package com.mozhimen.uicorektest.layoutk

import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.uicorektest.databinding.ActivityLayoutkSquareBinding

/**
 * @ClassName LayoutKSquareActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/12/17 12:24
 * @Version 1.0
 */
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
@APermissionCheck(CPermission.SYSTEM_ALERT_WINDOW)
class LayoutKSquareActivity : BaseActivityVB<ActivityLayoutkSquareBinding>()