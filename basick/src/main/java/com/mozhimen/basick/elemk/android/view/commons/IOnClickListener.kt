package com.mozhimen.basick.elemk.android.view.commons

/**
 * @ClassName IOnClickListener
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/11 21:16
 * @Version 1.0
 */
interface IOnClickListener {
    fun onSingleClick(x: Float, y: Float)//点击
    fun onDoubleClick(x: Float, y: Float)//双击
    fun onLongClick(x: Float, y: Float)//长按
}