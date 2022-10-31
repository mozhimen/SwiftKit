package com.mozhimen.uicorek.textk.bubble.cons

import android.util.SparseArray


/**
 * @ClassName EArrowDirection
 * @Description
 * 箭头朝向定义
 * @property value Int
 * @property isLeft Boolean
 * @property isUp Boolean
 * @property isRight Boolean
 * @property isDown Boolean
 * @constructor
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/31 13:59
 * @Version 1.0
 */
enum class EArrowDirection(value: Int) {
    None(-1),//无箭头
    Auto(0), //自动确定指向
    Left(1),
    Up(2),
    Right(3),
    Down(4);

    companion object {
        private var int2TypeDict = SparseArray<EArrowDirection>()

        init {
            for (type in values()) {
                int2TypeDict.put(type.value, type)
            }
        }

        fun valueOf(value: Int): EArrowDirection {
            return int2TypeDict[value] ?: Auto
        }
    }

    var value = 0

    init {
        this.value = value
    }

    val isLeft: Boolean
        get() = this == Left
    val isUp: Boolean
        get() = this == Up
    val isRight: Boolean
        get() = this == Right
    val isDown: Boolean
        get() = this == Down
}