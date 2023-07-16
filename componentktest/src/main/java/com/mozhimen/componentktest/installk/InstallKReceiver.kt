package com.mozhimen.componentktest.installk

import com.mozhimen.basick.elemk.android.content.bases.BaseInstallObserverBroadcastReceiver
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiTarget_AtV_25_71_N1

/**
 * @ClassName InstallReceiver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/13 17:32
 * @Version 1.0
 */
@OptIn(ALintKOptIn_ApiTarget_AtV_25_71_N1::class)
class InstallKReceiver : BaseInstallObserverBroadcastReceiver()