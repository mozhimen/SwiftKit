package com.mozhimen.uicoremk.searchmk

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.RelativeLayout

/**
 * @ClassName SearchMKView
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/4 15:28
 * @Version 1.0
 */
class SearchMKView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val _attrs: SearchMKAttrsParser.Attrs =
        SearchMKAttrsParser.parseSearchViewAttrs(context, attrs, defStyleAttr)
    var _editText: EditText? = null

    init {
        //初始化editText  --create-bind property --addview
        initEditText()
    }

    private fun initEditText() {
        _editText = EditText(context)
        _editText?.setTextColor()
    }
}