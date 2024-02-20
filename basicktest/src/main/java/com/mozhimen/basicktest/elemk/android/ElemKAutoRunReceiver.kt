package com.mozhimen.basicktest.elemk.android

import com.mozhimen.basick.elemk.android.content.bases.BaseBootBroadcastReceiver
import com.mozhimen.basick.lintk.optins.permission.OPermission_RECEIVE_BOOT_COMPLETED
import com.mozhimen.basicktest.BasicKActivity

/**
 * @ClassName AutoRunReceiver
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/26 18:53
 * @Version 1.0
 */
@OptIn(OPermission_RECEIVE_BOOT_COMPLETED::class)
class ElemKAutoRunReceiver : BaseBootBroadcastReceiver(BasicKActivity::class.java, 5000)