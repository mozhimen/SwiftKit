package com.mozhimen.basick.elemk.android.view

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import com.mozhimen.basick.elemk.android.view.cons.CHapticFeedbackConstants
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName HapticTouchCallback
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/18
 * @Version 1.0
 */
class HapticOnTouchCallback : View.OnTouchListener {
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN ->
                v?.performHapticFeedback(CHapticFeedbackConstants.VIRTUAL_KEY)
            MotionEvent.ACTION_UP ->
                if (UtilKBuildVersion.isAfterV_27_81_O1()) {
                    v?.performHapticFeedback(CHapticFeedbackConstants.VIRTUAL_KEY_RELEASE)
                }
        }
        return false
    }
}