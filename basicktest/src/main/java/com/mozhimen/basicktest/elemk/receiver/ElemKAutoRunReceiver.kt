package com.mozhimen.basicktest.elemk.receiver

import com.mozhimen.basick.elemk.receiver.commons.BaseAutoRunReceiver
import com.mozhimen.basicktest.BasicKActivity

/**
 * @ClassName AutoRunReceiver
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/26 18:53
 * @Version 1.0
 */
class ElemKAutoRunReceiver : BaseAutoRunReceiver(5000, BasicKActivity::class.java)