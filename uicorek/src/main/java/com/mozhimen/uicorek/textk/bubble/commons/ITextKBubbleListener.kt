package com.mozhimen.uicorek.textk.bubble.commons

/**
 * @ClassName ITextKBubbleListener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/5 14:57
 * @Version 1.0
 */
interface ITextKBubbleListener {
    fun setSuperPadding(left: Int, top: Int, right: Int, bottom: Int)

    fun getSuperPaddingLeft(): Int

    fun getSuperPaddingTop(): Int

    fun getSuperPaddingRight(): Int

    fun getSuperPaddingBottom(): Int
}