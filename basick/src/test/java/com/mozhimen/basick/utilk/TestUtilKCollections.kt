package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.exts.combineElement2List
import com.mozhimen.basick.utilk.exts.getIndexFirst
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
    fun rotate() {
        var index = 0
        var current = 0f
        var temp = current
        while (index >= 8) {
            current += 45
            if (current == 360f) current = 0f
            temp += current + when {
                (temp - current) <= -180f -> 360f
                (temp - current) >= 180f -> -360f
                else -> 0f
            }
            temp.printlog()
            index++
        }
    }

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

    @Test
    fun getIndexFirst() {

        val elements = listOf(
            User("赵", 11),
            User("钱", 11),
            User("孙", 12),
            User("赵", 13),
        )
        elements.getIndexFirst { it.name == "赵" }.printlog()
        elements.getIndexFirst { it.name == "孙" }.printlog()
        elements.getIndexFirst { it.age == 10 }.printlog()
    }

    data class User(
        val name: String,
        val age: Int
    )
}