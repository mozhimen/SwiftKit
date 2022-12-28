package com.mozhimen.uicorek.popwink.bases

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.mozhimen.basick.utilk.UtilKDrawable.isColorDrawableValid
import com.mozhimen.basick.utilk.view.UtilKView.setBackground
import com.mozhimen.uicorek.popwink.basepopwin.basepopup.BasePopupHelper


/**
 * @ClassName BasePopwinKBackgroundView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/27 11:52
 * @Version 1.0
 */
class BasePopwinKBackgroundView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private var _helper: BasePopupHelper? = null

    companion object{
        @JvmStatic
        fun create(context: Context, helper: BasePopupHelper): BasePopwinKBackgroundView? {
            val view = BasePopwinKBackgroundView(context)
            view.init(context, helper)
            return view
        }
    }

    fun destroy() {
        _helper = null
    }

    fun update() {
        if (_helper != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = _helper!!.popupBackground
            } else {
                setBackgroundDrawable(_helper!!.popupBackground)
            }
        }
    }

    private fun init(context: Context, helper: BasePopupHelper) {
        if (!isColorDrawableValid(helper.popupBackground)) {
            visibility = GONE
            return
        }
        this._helper = helper
        visibility = VISIBLE
        setBackground(this, helper.popupBackground)
    }
}