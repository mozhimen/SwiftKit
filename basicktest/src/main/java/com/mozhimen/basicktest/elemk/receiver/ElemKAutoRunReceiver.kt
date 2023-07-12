package com.mozhimen.basicktest.elemk.receiver

import com.mozhimen.basick.elemk.android.content.bases.BaseAutoRunBroadcastReceiver
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basicktest.BasicKActivity

/**
 * @ClassName AutoRunReceiver
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/26 18:53
 * @Version 1.0
 */
@AManifestKRequire(CPermission.RECEIVE_BOOT_COMPLETED)
class ElemKAutoRunReceiver : BaseAutoRunBroadcastReceiver(BasicKActivity::class.java, 5000)