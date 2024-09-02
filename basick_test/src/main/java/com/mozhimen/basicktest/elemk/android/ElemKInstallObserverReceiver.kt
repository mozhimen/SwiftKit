package com.mozhimen.basicktest.elemk.android

import com.mozhimen.kotlin.elemk.android.content.bases.BasePackageBroadcastReceiver
import com.mozhimen.kotlin.lintk.optins.OApiTarget_AtV_25_71_N1
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_QUERY_ALL_PACKAGES


/**
 * @ClassName UpdateReceiver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/3 16:31
 * @Version 1.0
 */
@OptIn(OApiTarget_AtV_25_71_N1::class, OPermission_QUERY_ALL_PACKAGES::class)
class ElemKInstallObserverReceiver : BasePackageBroadcastReceiver()