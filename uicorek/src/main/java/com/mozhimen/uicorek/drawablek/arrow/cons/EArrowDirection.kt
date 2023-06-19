package com.mozhimen.uicorek.drawablek.arrow.cons

import android.util.SparseArray


/**
 * @ClassName EArrowDirection
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/14 0:37
 * @Version 1.0
 */
enum class EArrowDirection(val value: Int) {
    None(0),//无箭头
    Auto(1), //自动确定指向,和None相似
    Left(2),
    Up(3),
    Right(4),
    Down(5);

    val isLeft: Boolean
        get() = this == Left
    val isUp: Boolean
        get() = this == Up
    val isRight: Boolean
        get() = this == Right
    val isDown: Boolean
        get() = this == Down

    companion object {
        private var _int2TypeDict = SparseArray<EArrowDirection>()

        init {
            for (type in values()) {
                _int2TypeDict.put(type.value, type)
            }
        }

        fun get(value: Int): EArrowDirection {
            return _int2TypeDict[value] ?: Auto
        }
    }
}