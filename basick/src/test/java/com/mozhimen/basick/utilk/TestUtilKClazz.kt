package com.mozhimen.basick.utilk

import android.view.Display
import com.mozhimen.basick.utilk.kotlin.getPackageStr
import com.mozhimen.basick.utilk.kotlin.packageStr2clazz
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
        "android.view.Display".packageStr2clazz().name.printlog()
        Display::class.java.getPackageStr().packageStr2clazz().getPackageStr().printlog()
    }
}