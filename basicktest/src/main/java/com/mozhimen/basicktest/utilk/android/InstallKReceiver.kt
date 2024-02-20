package com.mozhimen.basicktest.utilk.android

import com.mozhimen.basick.elemk.android.content.bases.BasePackageBroadcastReceiver
import com.mozhimen.basick.lintk.optins.OApiTarget_AtV_25_71_N1
import com.mozhimen.basick.lintk.optins.permission.OPermission_QUERY_ALL_PACKAGES

/**
 * @ClassName InstallReceiver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/13 17:32
 * @Version 1.0
 */
@OptIn(OApiTarget_AtV_25_71_N1::class, OPermission_QUERY_ALL_PACKAGES::class)
class InstallKReceiver : BasePackageBroadcastReceiver()