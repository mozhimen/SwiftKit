package com.mozhimen.uicorek.popwink

import android.annotation.SuppressLint
import android.app.ActionBar
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @ClassName PopwinKTip
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/31 23:09
 * @Version 1.0
 */
class PopwinKTip {
    /*private var popWindowTempOK: PopupWindow? = null

    @SuppressLint("SetTextI18n")
    @Synchronized
    fun operateTempOKDialog(isOK: Boolean, temp: Double) {
        //showPopWindow
        popWindowTempOK?.dismiss()
        val contentView =
            LayoutInflater.from(this).inflate(R.layout.layout_temp_tip, null)
        if (isOK) {
            contentView.findViewById<MaterialCardView>(R.id.temp_tip_bg)
                .setBackgroundResource(R.color.ThemeGreen)
            contentView.findViewById<TextView>(R.id.temp_tip_title).text = "体温正常:"
        } else {
            contentView.findViewById<MaterialCardView>(R.id.temp_tip_bg)
                .setBackgroundResource(R.color.ThemeRed)
            contentView.findViewById<TextView>(R.id.temp_tip_title).text = "体温异常:"
        }
        contentView.findViewById<TextView>(R.id.temp_tip_temp).text = "${vm.getDecimal2String(temp)}°"
        popWindowTempOK = PopupWindow(
            contentView,
            ActionBar.LayoutParams.MATCH_PARENT,
            resources.getDimension(R.dimen.dp_120).toInt()
        )
        popWindowTempOK?.apply {
            isOutsideTouchable = false //点击外部区域不消失
            isTouchable = false
            setBackgroundDrawable(null);
            inputMethodMode = PopupWindow.INPUT_METHOD_NOT_NEEDED
            softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        }

        GlobalScope.launch(Dispatchers.Main) {
            popWindowTempOK?.showAtLocation(
                vb.root, Gravity.TOP or Gravity.CENTER_HORIZONTAL,
                0, 0
            ) //指定顶部位置
            delay(1000)
            if (popWindowTempOK?.isShowing == true) {
                popWindowTempOK?.dismiss()
            }
            popWindowTempOK = null
        }
    }*/
}