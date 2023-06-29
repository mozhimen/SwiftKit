package com.mozhimen.basick.utilk

import android.view.Display
import com.mozhimen.basick.utilk.kotlin.getPackageStr
import com.mozhimen.basick.utilk.kotlin.packageStr2Clazz
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
        "android.view.Display".packageStr2Clazz().name.printlog()
        Display::class.java.getPackageStr().packageStr2Clazz().getPackageStr().printlog()
    }
}