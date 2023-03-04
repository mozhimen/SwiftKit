package com.mozhimen.basicktest.utilk

import android.view.MotionEvent
import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.view.keyboard.UtilKKeyBoard
import com.mozhimen.basicktest.databinding.ActivityUtilkKeyboardBinding


/**
 * @ClassName UtilKKeyBoardActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/2 17:49
 * @Version 1.0
 */
class UtilKKeyBoardActivity : BaseActivityVB<ActivityUtilkKeyboardBinding>() {
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val focusView: View? = currentFocus
            if (focusView != null && UtilKKeyBoard.isShouldHide(focusView, event)) {
                UtilKKeyBoard.hide(this)
            }
        }
        return super.dispatchTouchEvent(event)
    }
}