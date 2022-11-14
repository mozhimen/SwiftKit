package com.mozhimen.basicktest.prefabk

import com.mozhimen.basick.prefabk.receiver.PrefabKReceiverAutoRun
import com.mozhimen.basicktest.BasicKActivity

/**
 * @ClassName AutoRunReceiver
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/26 18:53
 * @Version 1.0
 */
class AutoRunReceiver : PrefabKReceiverAutoRun(5000, BasicKActivity::class.java)