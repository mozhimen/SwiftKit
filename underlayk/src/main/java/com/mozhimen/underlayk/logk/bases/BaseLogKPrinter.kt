package com.mozhimen.underlayk.logk.bases

import androidx.annotation.CallSuper
import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.utilk.android.util.println
import com.mozhimen.underlayk.logk.commons.ILogKPrinter

/**
 * @ClassName BaseLogKPrinter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/26 20:14
 * @Version 1.0
 */
open class BaseLogKPrinter : ILogKPrinter {
    @CallSuper
    override fun print(config: BaseLogKConfig, level: Int, tag: String, printString: String) {
        (getName() + CMsg.PART_LINE).println(level, tag)
    }
}