package com.mozhimen.uicorek.btnk

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.mozhimen.basick.utilk.exts.setIconFont
import com.mozhimen.basick.utilk.view.UtilKTextView
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.commons.IUicoreK

/**
 * @ClassName BtnKIconFont
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/2 16:28
 * @Version 1.0
 */
class BtnKIconFont @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatButton(context, attrs, defStyleAttr), IUicoreK {

    private var _iconFontPath = "icons/iconfont.ttf"

    init {
        initAttrs(attrs)
        setIconFontPath(_iconFontPath)
    }

    override fun initAttrs(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BtnKIconFont)
            _iconFontPath = typedArray.getString(R.styleable.BtnKIconFont_btnKIconFont_fontPath) ?: _iconFontPath
            typedArray.recycle()
        }
    }

    fun setIconFontPath(iconFontPath: String) {
        UtilKTextView.setIconFont(this, iconFontPath.also { _iconFontPath = it })
    }

    fun getIconFontPath(): String =
        _iconFontPath
}