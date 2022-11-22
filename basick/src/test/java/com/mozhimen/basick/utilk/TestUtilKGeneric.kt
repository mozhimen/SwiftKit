package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.exts.printlog
import org.junit.Test

/**
 * @ClassName TestUtilKGeneric
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 22:48
 * @Version 1.0
 */
class TestUtilKGeneric : Person<Knife>() {
    @Test
    fun getClazzGenericType() {
        UtilKGeneric.getGenericType<TestUtilKGeneric>()?.printlog()
        UtilKGeneric.getParentGenericType<TestUtilKGeneric>()?.printlog()
        UtilKGeneric.getParentGenericTypeClazz<TestUtilKGeneric>()?.printlog()
    }
}

open class Tool

class Knife : Tool()

open class Person<T : Tool>