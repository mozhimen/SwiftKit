package com.mozhimen.uicorektest.layoutk

import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.permissionk.annors.APermissionRequire
import com.mozhimen.uicorektest.databinding.ActivityLayoutkSquareBinding

/**
 * @ClassName LayoutKSquareActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/12/17 12:24
 * @Version 1.0
 */
@APermissionRequire(CPermission.SYSTEM_ALERT_WINDOW)
class LayoutKSquareActivity : BaseActivityVB<ActivityLayoutkSquareBinding>()