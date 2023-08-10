package com.mozhimen.uicorek.popwink.bases.helpers

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.mozhimen.basick.utilk.android.graphics.UtilKDrawable.isColorDrawableNormal
import com.mozhimen.basick.utilk.android.view.UtilKView
import com.mozhimen.basick.utilk.android.view.applyBackground


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
            view.init(helper)
            return view
        }
    }

    fun destroy() {
        _helper = null
    }

    fun update() {
        if (_helper != null) {
            applyBackground(_helper!!.popupBackground)
        }
    }

    private fun init(helper: BasePopupHelper) {
        if (!isColorDrawableNormal(helper.popupBackground)) {
            visibility = GONE
            return
        }
        this._helper = helper
        visibility = VISIBLE
        UtilKView.applyBackground(this, helper.popupBackground)
    }
}