package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.java.lang.UtilKReflectGenericKotlin
import com.mozhimen.basick.utilk.kotlin.println
import com.mozhimen.basick.utilk.kotlin.printlog
import org.junit.Test

/**
 * @ClassName TestUtilKReflectGenericKotlin
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/22 23:56
 * @Version 1.0
 */
class TestUtilKReflectGenericKotlin {

    open class BaseReflect{
        companion object{
            @JvmStatic
            fun printXXX():String{
                return "xxx"
            }
        }
    }

    open class BaseReflectDisturb{
        companion object{
            @JvmStatic
            fun printYYY():String{
                return "yyy"
            }
        }
    }
    open class BaseClass<T:BaseReflect>

    open class BaseClassA: BaseClass<BaseReflect>(){
        @Test
        open fun getParentGenericTypeClazz(){
            UtilKReflectGenericKotlin.getParentGenericTypeClazz(this::class.java)?.run {
                (getDeclaredMethod("printXXX").invoke(null) as String).printlog()
            }?:"null".printlog()
        }
    }

    open class BaseClassB<T1:BaseReflectDisturb>: BaseClassA(){
        @Test
        override fun getParentGenericTypeClazz(){
            UtilKReflectGenericKotlin.getParentGenericTypeClazz(this::class.java)?.run {
                (getDeclaredMethod("printXXX").invoke(null) as String).printlog()
            }?:"null".printlog()
        }

        private val _disturb:T1? = null
        fun getDisturb():T1? = _disturb
    }

    open class BaseClassC: BaseClassB<BaseReflectDisturb>() {
        @Test
        override fun getParentGenericTypeClazz(){
            UtilKReflectGenericKotlin.getParentGenericTypeByTClazz(this::class.java,BaseReflect::class.java)?.run {
                (getDeclaredMethod("printXXX").invoke(null) as String).printlog()
            }?:"null".printlog()
        }
    }
}