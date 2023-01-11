package com.mozhimen.basicktest.elemk.receiver

import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.elemk.receiver.bases.BaseAutoRunReceiver
import com.mozhimen.basick.permissionk.annors.APermissionKRequire
import com.mozhimen.basicktest.BasicKActivity

/**
 * @ClassName AutoRunReceiver
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/26 18:53
 * @Version 1.0
 */
@APermissionKRequire(CPermission.RECEIVE_BOOT_COMPLETED)
class ElemKAutoRunReceiver : BaseAutoRunReceiver(BasicKActivity::class.java, 5000)