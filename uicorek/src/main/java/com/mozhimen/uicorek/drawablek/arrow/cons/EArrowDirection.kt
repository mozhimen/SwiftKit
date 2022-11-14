package com.mozhimen.uicorek.drawablek.arrow.cons

/**
 * @ClassName EArrowDirection
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/14 0:37
 * @Version 1.0
 */
enum class EArrowDirection {
    None,//无箭头
    Auto, //自动确定指向
    Left,
    Up,
    Right,
    Down;

    val isLeft: Boolean
        get() = this == Left
    val isUp: Boolean
        get() = this == Up
    val isRight: Boolean
        get() = this == Right
    val isDown: Boolean
        get() = this == Down
}