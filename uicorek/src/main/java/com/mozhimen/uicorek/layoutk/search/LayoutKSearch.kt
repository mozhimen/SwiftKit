package com.mozhimen.uicorek.layoutk.search

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.mozhimen.uicorek.layoutk.commons.LayoutKRelative
import com.mozhimen.basick.utilk.exts.setPaddingHorizontal
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.textk.TextKIconFont
import com.mozhimen.basick.utilk.exts.setPadding
import com.mozhimen.basick.elemk.handler.commons.BaseWeakRefHandler
import com.mozhimen.uicorek.layoutk.search.helpers.AttrsParser
import com.mozhimen.uicorek.layoutk.search.commons.SearchTextWatcher
import com.mozhimen.uicorek.layoutk.search.mos.MSearchAttrs

/**
 * @ClassName LayoutKSearch
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/4 15:28
 * @Version 1.0
 */
class LayoutKSearch @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LayoutKRelative(context, attrs, defStyleAttr) {

    private val _attrs: MSearchAttrs =
        AttrsParser.parseSearchViewAttrs(context, attrs, defStyleAttr)
    private var _searchTextWatcher: SearchTextWatcher? = null
    private val _debounceRunnable = Runnable {
        if (_searchTextWatcher != null) _searchTextWatcher!!.afterTextChanged(_editText?.text)
    }
    private var _editText: EditText? = null
    private var _hintText: TextView? = null
    private var _keywordContainer: LinearLayout? = null
    private var _keywordIcon: TextKIconFont? = null
    private var _keywordText: TextView? = null
    private var _searchIcon: TextKIconFont? = null
    private var _searchIconHintContainer: LinearLayout? = null
    private var _clearIcon: TextKIconFont? = null
    private val _weakRefHandler = BaseWeakRefHandler(this)

    companion object {
        const val LEFT = 1
        const val CENTER = 0

        const val DEBOUNCE_TRIGGER_DURATION = 200L
    }

    init {
        //初始化editText
        initEditText()
        //初始化右侧删除按钮
        initClearIcon()
        //初始化默认的提示语和searchIcon
        initSearchIconHintContainer()
        //init view
        initView()
    }

    fun setDebounceTextChangedListener(searchTextWatcher: SearchTextWatcher) {
        this._searchTextWatcher = searchTextWatcher
    }

    fun setClearIconClickListener(listener: OnClickListener) {
        _clearIcon?.setOnClickListener {
            _editText?.text = null
            changeVisibility(_clearIcon, false)
            changeVisibility(_searchIcon, true)
            changeVisibility(_hintText, true)
            changeVisibility(_searchIconHintContainer, true)
            listener.onClick(it)
        }
    }

    fun setHintText(hintText: String) {
        _hintText?.text = hintText
    }

    fun getKeyword(): String {
        return _keywordText?.text.toString()
    }

    fun setKeyword(keyword: String?, listener: OnClickListener) {
        //当用户点击 联想词面板的时候，会调用该方法，把关键词设置到搜索框上面
        ensureKeywordContainer()
        toggleSearchViewsVisibility(true)

        _editText?.text = null
        _keywordText?.text = keyword
        _keywordIcon?.setOnClickListener {
            //点击了keywordk-clearicon ,此时应该恢复默认提示语views显示¬
            toggleSearchViewsVisibility(false)
            listener.onClick(it)
        }
    }

    private fun toggleSearchViewsVisibility(isShow: Boolean) {
        changeVisibility(_editText, !isShow)
        changeVisibility(_clearIcon, false)
        changeVisibility(_searchIconHintContainer, !isShow)
        changeVisibility(_searchIcon, !isShow)
        changeVisibility(_hintText, !isShow)
        changeVisibility(_keywordContainer, isShow)
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {}

    override fun initView() {
        background = _attrs.searchBackground
        _editText?.addTextChangedListener(object : SearchTextWatcher() {
            override fun afterTextChanged(p0: Editable?) {
                val hasContent = (p0?.trim()?.length ?: 0) > 0
                changeVisibility(_clearIcon, hasContent)
                changeVisibility(_searchIconHintContainer, !hasContent)
                if (_searchTextWatcher != null) {
                    _weakRefHandler.removeCallbacks(_debounceRunnable)
                    _weakRefHandler.postDelayed(_debounceRunnable, DEBOUNCE_TRIGGER_DURATION)
                }
            }
        })
    }

    private fun initSearchIconHintContainer() {
        //hintView
        _hintText = TextView(context)
        _hintText?.setTextColor(_attrs.hintTextColor)
        _hintText?.setTextSize(TypedValue.COMPLEX_UNIT_PX, _attrs.hintTextSize)
        _hintText?.isSingleLine = true
        _hintText?.text = _attrs.hintText
        _hintText?.id = R.id.layoutk_search_txt_hint

        //icon
        _searchIcon = TextKIconFont(context, null)
        _searchIcon?.setTextSize(TypedValue.COMPLEX_UNIT_PX, _attrs.searchTextSize)
        _searchIcon?.setTextColor(_attrs.hintTextColor)
        _searchIcon?.text = _attrs.searchIcon
        _searchIcon?.id = R.id.layoutk_search_txt_icon_search
        _searchIcon?.setPaddingHorizontal(_attrs.searchIconPadding)

        //hint icon container
        _searchIconHintContainer = LinearLayout(context)
        _searchIconHintContainer?.orientation = LinearLayout.HORIZONTAL
        _searchIconHintContainer?.gravity = Gravity.CENTER

        _searchIconHintContainer?.addView(_searchIcon)
        _searchIconHintContainer?.addView(_hintText)

        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(CENTER_VERTICAL)
        when (_attrs.hintGravity) {
            CENTER -> layoutParams.addRule(CENTER_IN_PARENT)
            LEFT -> layoutParams.addRule(ALIGN_PARENT_LEFT)
            else -> throw IllegalStateException("not support this gravity")
        }
        addView(_searchIconHintContainer, layoutParams)
    }

    private fun initClearIcon() {
        if (TextUtils.isEmpty(_attrs.clearIcon)) return
        _clearIcon = TextKIconFont(context, null)
        _clearIcon?.setTextSize(TypedValue.COMPLEX_UNIT_PX, _attrs.clearIconSize)
        _clearIcon?.text = _attrs.clearIcon
        _clearIcon?.setTextColor(_attrs.searchTextColor)
        _clearIcon?.setPadding(_attrs.searchIconPadding)

        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(CENTER_VERTICAL)
        layoutParams.addRule(ALIGN_PARENT_RIGHT)
        _clearIcon?.layoutParams = layoutParams
        //默认隐藏,只有当输入文字时才会显示
        changeVisibility(_clearIcon, false)
        _clearIcon?.id = R.id.layoutk_search_icon_clear
        addView(_clearIcon, layoutParams)
    }

    private fun initEditText() {
        _editText = EditText(context)
        _editText?.setTextColor(_attrs.searchTextColor)
        _editText?.setBackgroundColor(Color.TRANSPARENT)
        _editText?.setTextSize(TypedValue.COMPLEX_UNIT_PX, _attrs.searchTextSize)
        //防止文字输入过于贴近输入框的两边
        _editText?.setPadding(_attrs.searchIconPadding, 0, _attrs.searchIconPadding, 0)
        _editText?.id = R.id.layoutk_search_edit_search

        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        layoutParams.addRule(CENTER_VERTICAL)
        addView(_editText, layoutParams)
    }

    private fun changeVisibility(view: View?, show: Boolean) {
        view?.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun ensureKeywordContainer() {
        if (_keywordContainer != null) return
        if (!TextUtils.isEmpty(_attrs.keywordIcon)) {
            _keywordIcon = TextKIconFont(context, null)
            _keywordIcon?.setTextSize(TypedValue.COMPLEX_UNIT_PX, _attrs.keywordIconSize)
            _keywordIcon?.setTextColor(_attrs.keywordIconColor)
            _keywordIcon?.text = _attrs.keywordIcon
            _keywordIcon?.id = R.id.layoutk_search_txt_icon_keyword
            _keywordIcon?.setPadding(_attrs.searchIconPadding, _attrs.searchIconPadding / 2)
        }

        _keywordText = TextView(context)
        _keywordText?.setTextSize(TypedValue.COMPLEX_UNIT_PX, _attrs.keywordIconSize)
        _keywordText?.setTextColor(_attrs.keywordIconColor)
        _keywordText?.includeFontPadding = false
        _keywordText?.isSingleLine = true
        _keywordText?.ellipsize = TextUtils.TruncateAt.END
        _keywordText?.filters = arrayOf(InputFilter.LengthFilter(_attrs.keywordMaxLength))
        _keywordText?.id = R.id.layoutk_search_txt_keyword
        _keywordText?.setPadding(
            _attrs.searchIconPadding, _attrs.searchIconPadding / 2,
            if (_keywordIcon == null) _attrs.searchIconPadding else 0, _attrs.searchIconPadding / 2
        )

        _keywordContainer = LinearLayout(context)
        _keywordContainer?.orientation = LinearLayout.HORIZONTAL
        _keywordContainer?.gravity = Gravity.CENTER
        _keywordContainer?.background = _attrs.keywordBackground
        _keywordContainer?.addView(
            _keywordText,
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        )

        if (_keywordIcon != null) {
            _keywordContainer?.addView(
                _keywordIcon,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }

        val keywordLayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        keywordLayoutParams.addRule(CENTER_VERTICAL)
        keywordLayoutParams.addRule(ALIGN_PARENT_LEFT)
        keywordLayoutParams.leftMargin = _attrs.searchIconPadding
        keywordLayoutParams.rightMargin = _attrs.searchIconPadding
        addView(_keywordContainer, keywordLayoutParams)
    }

    override fun onDetachedFromWindow() {
        _weakRefHandler.removeCallbacks(_debounceRunnable)
        super.onDetachedFromWindow()
    }
}