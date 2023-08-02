package com.mozhimen.basick.utilk

import android.view.Display
import com.mozhimen.basick.utilk.kotlin.getStrPackage
import com.mozhimen.basick.utilk.kotlin.strPackage2clazz
import com.mozhimen.basick.utilk.kotlin.printlog
import org.junit.Test

/**
 * @ClassName TestUtilKClazz
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/29 22:15
 * @Version 1.0
 */
class TestUtilKClazz {
    @Test
    fun test() {
        "android.view.Display".strPackage2clazz().name.printlog()
        Display::class.java.getStrPackage().strPackage2clazz().getStrPackage().printlog()
    }
}