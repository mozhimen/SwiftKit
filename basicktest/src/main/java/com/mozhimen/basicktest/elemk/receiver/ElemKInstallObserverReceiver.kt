package com.mozhimen.basicktest.elemk.receiver

import com.mozhimen.basick.elemk.android.content.bases.BaseInstallObserverBroadcastReceiver
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiTargetVersion_V_25_71_N1


/**
 * @ClassName UpdateReceiver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/3 16:31
 * @Version 1.0
 */
@OptIn(ALintKOptIn_ApiTargetVersion_V_25_71_N1::class)
class ElemKInstallObserverReceiver : BaseInstallObserverBroadcastReceiver()