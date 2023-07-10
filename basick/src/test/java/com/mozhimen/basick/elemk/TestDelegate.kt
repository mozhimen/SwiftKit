package com.mozhimen.basick.elemk

import com.mozhimen.basick.elemk.kotlin.properties.VarProperty_Set
import com.mozhimen.basick.elemk.kotlin.properties.VarProperty_SetSameNonnull
import com.mozhimen.basick.elemk.kotlin.properties.VarProperty_SetVaryNullable
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
    var varProperty_Set by VarProperty_Set("123") { _, value -> value.isNotEmpty() }
    var varDeleg_SetSameNonnull by VarProperty_SetSameNonnull<String?>("123") { _, value -> ("set:$value").printlog();true }
    var varDeleg_SetVaryNullable by VarProperty_SetVaryNullable<String?>("123") { _, value -> ("set:$value").printlog();true }

    @Test
    fun testReturn() {
        varProperty_Set = ""
        varProperty_Set.printlog()
        varProperty_Set = "234"
        varProperty_Set.printlog()
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