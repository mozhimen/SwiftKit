package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.exts.combineElement2List
import com.mozhimen.basick.utilk.exts.printlog
import org.junit.Test
import java.util.concurrent.CopyOnWriteArrayList


/**
 * @ClassName TestUtilKCollections
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/7 17:14
 * @Version 1.0
 */
class TestUtilKCollections {

    @Test
    fun min() {
        val list = mutableListOf<Float>()
        genList(list)
        list.printlog()
    }

    fun genList(list: MutableList<Float>) {
        list.add(0f)
        list.add(1f)
    }

    @Test
    fun combineElement() {

        val elements = listOf(
            User("赵", 11),
            User("钱", 11),
            User("孙", 12),
            User("赵", 13),
        )
        elements.combineElement2List { it.name }.printlog()
        elements.combineElement2List { it.age }.printlog()
    }

    data class User(
        val name: String,
        val age: Int
    )
}