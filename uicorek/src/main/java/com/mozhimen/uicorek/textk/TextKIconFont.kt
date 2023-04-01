package com.mozhimen.uicorek.textk

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.mozhimen.basick.utilk.exts.setIconFont
import com.mozhimen.basick.utilk.view.UtilKTextView
import com.mozhimen.uicorek.R

/**
 * @ClassName TextKIconFont
 * @Description 用以支持全局iconfont资源的引用,可以在布局文件中直接设置text
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/18 12:50
 * @Version 1.0
 */
class TextKIconFont @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    private var _iconfontPath = "icons/iconfont.ttf"

    init {
        initAttrs(attrs)
        setIconFont(_iconfontPath)
    }

    fun initAttrs(attrs: AttributeSet?) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextKIconFont)
        _iconfontPath = typedArray.getString(R.styleable.TextKIconFont_textKIconFont_fontPath) ?: _iconfontPath
        typedArray.recycle()
    }

    fun setIconFontPath(iconFontPath: String) {
        UtilKTextView.setIconFont(this, iconFontPath.also { _iconfontPath = it })
    }

    fun getIconFontPath() = _iconfontPath
}