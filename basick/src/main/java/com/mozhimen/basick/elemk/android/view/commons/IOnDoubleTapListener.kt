package com.mozhimen.basick.elemk.android.view.commons

import android.view.GestureDetector
import android.view.MotionEvent
import com.mozhimen.basick.utilk.android.util.d
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName IOnDoubleTapListener
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/11 21:00
 * @Version 1.0
 */
interface IOnDoubleTapListener : GestureDetector.OnDoubleTapListener, IUtilK {
    override fun onDoubleTap(e: MotionEvent): Boolean = true.also { "onDoubleTap".d(TAG) }//表示发生双击行为
    override fun onDoubleTapEvent(e: MotionEvent): Boolean = true.also { "onDoubleTapEvent".d(TAG) }//双击"
    override fun onSingleTapConfirmed(e: MotionEvent): Boolean = true.also { "onSingleTapConfirmed".d(TAG) }//严格的单击
}