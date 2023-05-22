package com.mozhimen.basick.elemk

import com.mozhimen.basick.elemk.delegate.VarDelegate_SetFun_VaryNonnull
import com.mozhimen.basick.utilk.exts.printlog
import org.junit.Test

/**
 * @ClassName TestDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/19 20:49
 * @Version 1.0
 */
class TestDelegate {
    var test by VarDelegate_SetFun_VaryNonnull("") { field, value -> if (value == "123") return@VarDelegate_SetFun_VaryNonnull }

    @Test
    fun testReturn() {
        test = "123"
        test.printlog()
        test = "456"
        test.printlog()
    }
}