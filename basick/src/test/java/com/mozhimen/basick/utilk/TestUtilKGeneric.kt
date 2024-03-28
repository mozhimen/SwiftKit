package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.kotlin.printlog
import com.mozhimen.basick.utilk.java.lang.UtilKReflectGenericKotlin
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
        UtilKReflectGenericKotlin.getGenericType<TestUtilKGeneric>()?.printlog()
        UtilKReflectGenericKotlin.getParentGenericType<TestUtilKGeneric>()?.printlog()
        UtilKReflectGenericKotlin.getParentGenericType_ofClazz<TestUtilKGeneric>()?.printlog()
    }
}

open class Tool

class Knife : Tool()

open class Person<T : Tool>