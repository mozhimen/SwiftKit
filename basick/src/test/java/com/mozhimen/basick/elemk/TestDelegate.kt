package com.mozhimen.basick.elemk

import com.mozhimen.basick.elemk.delegate.VarDeleg_Set
import com.mozhimen.basick.elemk.delegate.VarDeleg_SetSameNonnull
import com.mozhimen.basick.elemk.delegate.VarDeleg_SetVaryNullable
import com.mozhimen.basick.utilk.kotlin.printlog
import org.junit.Test

/**
 * @ClassName TestDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/19 20:49
 * @Version 1.0
 */
class TestDelegate {
    var varDeleg_Set by VarDeleg_Set("123") { _, value -> value.isNotEmpty() }
    var varDeleg_SetSameNonnull by VarDeleg_SetSameNonnull<String?>("123") { _, value -> ("set:$value").printlog();true }
    var varDeleg_SetVaryNullable by VarDeleg_SetVaryNullable<String?>("123") { _, value -> ("set:$value").printlog();true }

    @Test
    fun testReturn() {
        varDeleg_Set = ""
        varDeleg_Set.printlog()
        varDeleg_Set = "234"
        varDeleg_Set.printlog()
        varDeleg_SetSameNonnull = "123"
        varDeleg_SetSameNonnull.printlog()
        varDeleg_SetSameNonnull = null
        varDeleg_SetSameNonnull.printlog()
        varDeleg_SetVaryNullable = "123"
        varDeleg_SetVaryNullable.printlog()
        varDeleg_SetVaryNullable = null
        varDeleg_SetVaryNullable.printlog()
    }
}