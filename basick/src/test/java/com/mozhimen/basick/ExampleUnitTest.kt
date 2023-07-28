package com.mozhimen.basick

import com.mozhimen.basick.utilk.kotlin.printlog
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun finalize() {
//        var lastTime = System.currentTimeMillis()
//        var time = 0
//        thread {
//            while (time < 11) {
//                if (System.currentTimeMillis() - lastTime > 1000) {
//                    time++
//                    time.printlog()
//                    lastTime = System.currentTimeMillis()
//                }
//            }
//        }.start()
        val a = 0b010
        val b = 0b100
        //read(a or b, 8)
    }

    fun read(num: Int, bit: Int) {
        val binaryString = String.format("%0${bit}s", Integer.toBinaryString(num)).apply { printlog() }
        for (i in binaryString.indices) {
            val b = binaryString[i]
            "Bit at position $i is $b".printlog()
        }
    }
}