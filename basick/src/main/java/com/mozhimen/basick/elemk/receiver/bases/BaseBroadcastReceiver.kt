package com.mozhimen.basick.elemk.receiver.bases

import android.content.BroadcastReceiver


/**
 * @ClassName BaseBroadcastReceiver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/13 15:23
 * @Version 1.0
 */
abstract class BaseBroadcastReceiver : BroadcastReceiver() {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"
}