package com.mozhimen.underlayk.fpsk.commons


/**
 * @ClassName IFpsK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/15 11:40
 * @Version 1.0
 */
interface IFpsK {
    /**
     * 是否显示
     * @return Boolean
     */
    fun isOpen(): Boolean
    /**
     * 开/关
     */
    fun toggle()
    /**
     * 增加监听器
     * @param listener IFpsKListener
     */
    fun addListener(listener: IFpsKListener)
    /**
     * 清空监听器
     */
    fun removeListeners()
}