package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.kotlin.printlog
import com.mozhimen.basick.utilk.squareup.moshi.strJson2tMoshi
import com.mozhimen.basick.utilk.squareup.moshi.t2strJsonMoshi
import org.junit.Test

/**
 * @ClassName UtilKJson
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/8 0:47
 * @Version 1.0
 */
class TestUtilKJson {
    @Test
    fun test() {
        """
            {
              "user": "DK234455",
              "pwd": "78e3396c576b4420b31acd20e412c5d9"
            }
        """.trimIndent().strJson2tMoshi<Bean>().printlog()

        Bean("123", "123").t2strJsonMoshi().printlog()
    }

    data class Bean(
        val user: String,
        val pwd: String
    )
}