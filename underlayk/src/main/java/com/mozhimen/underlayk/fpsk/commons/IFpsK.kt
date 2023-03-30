package com.mozhimen.underlayk.fpsk.commons


/**
 * @ClassName IFpsK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/15 11:40
 * @Version 1.0
 */
interface IFpsK {
    fun isOpen(): Boolean
    fun toggle()
    fun addListener(listener: IFpsKListener)
    fun removeListeners()
}