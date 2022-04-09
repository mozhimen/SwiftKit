package com.mozhimen.uicorek.navk

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.StringRes
import com.mozhimen.basicsk.utilk.UtilKRes
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.btnk.BtnKIconFont
import com.mozhimen.uicorek.textk.TextKIconFont
import java.util.*

/**
 * @ClassName NavKBar
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/2 14:35
 * @Version 1.0
 */
class NavKBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var _titleView: TextKIconFont? = null
    private var _subTitleView: TextKIconFont? = null
    private var _titleContainer: LinearLayout? = null

    private var _leftLastViewId = View.NO_ID
    private var _rightLastViewId = View.NO_ID
    private val _leftViewList = ArrayList<View>()
    private val _rightViewList = ArrayList<View>()

    //属性解析获得对象
    private val _attrs: NavKAttrs by lazy { NavKAttrsParser.parseNavAttrs(context, attrs, defStyleAttr) }

    init {
        if (!TextUtils.isEmpty(_attrs.titleStr)) {
            setTitle(_attrs.titleStr!!)
        }

        if (!TextUtils.isEmpty(_attrs.subTitleStr)) {
            setSubTitle(_attrs.subTitleStr!!)
        }

        if (_attrs.lineWidth > 0) {
            addLineView()
        }
    }

    fun setNavListener(listener: OnClickListener) {
        if (!TextUtils.isEmpty(_attrs.iconStr)) {
            val navBackView = addLeftTextButton(_attrs.iconStr!!, R.id.navk_icon_left_back)
            navBackView.setTextSize(TypedValue.COMPLEX_UNIT_PX, _attrs.iconSize)
            navBackView.setTextColor(_attrs.iconColor)
            navBackView.setOnClickListener(listener)
        }
    }

    fun addLeftTextButton(@StringRes stringRes: Int, viewId: Int): Button {
        return addLeftTextButton(UtilKRes.getString(stringRes), viewId)
    }

    fun addLeftTextButton(buttonText: String, viewId: Int): Button {
        val button = generateTextButton()
        button.text = buttonText
        button.id = viewId
        if (_leftViewList.isEmpty()) {
            button.setPadding(_attrs.paddingHorizontal * 2, 0, _attrs.paddingHorizontal, 0)
        } else {
            button.setPadding(_attrs.paddingHorizontal, 0, _attrs.paddingHorizontal, 0)
        }

        addLeftView(button, generateTextButtonLayoutParams())
        return button
    }

    fun addLeftView(view: View, params: LayoutParams) {
        val viewId = view.id
        if (viewId == View.NO_ID) {
            throw IllegalStateException("left view must has an unique id.")
        }
        if (_leftLastViewId == View.NO_ID) {
            params.addRule(ALIGN_PARENT_LEFT, viewId)
        } else {
            params.addRule(RIGHT_OF, _leftLastViewId)
        }
        _leftLastViewId = viewId
        params.alignWithParent = true  //alignParentIfMissing
        _leftViewList.add(view)
        addView(view, params)
    }

    fun addRightTextButton(buttonText: String, viewId: Int): Button {
        val button = generateTextButton()
        button.text = buttonText
        button.id = viewId
        if (_rightViewList.isEmpty()) {
            button.setPadding(_attrs.paddingHorizontal, 0, _attrs.paddingHorizontal * 2, 0)
        } else {
            button.setPadding(_attrs.paddingHorizontal, 0, _attrs.paddingHorizontal, 0)
        }

        addRightView(button, generateTextButtonLayoutParams())
        return button
    }

    fun addRightView(view: View, params: LayoutParams) {
        val viewId = view.id
        if (viewId == View.NO_ID) {
            throw IllegalStateException("right view must has an unique id.")
        }
        if (_rightLastViewId == View.NO_ID) {
            params.addRule(ALIGN_PARENT_RIGHT, viewId)
        } else {
            params.addRule(LEFT_OF, _rightLastViewId)
        }
        _rightLastViewId = viewId
        params.alignWithParent = true  //alignParentIfMissing
        _rightViewList.add(view)
        addView(view, params)
    }

    fun setTitle(title: String) {
        ensureTitleView()
        _titleView?.text = title
        _titleView?.visibility = if (TextUtils.isEmpty(title)) View.GONE else View.VISIBLE
    }

    fun setSubTitle(subTitle: String) {
        ensureSubTitleView()
        updateTitleViewStyle()
        _subTitleView?.text = subTitle
        _subTitleView?.visibility = if (TextUtils.isEmpty(subTitle)) View.GONE else View.VISIBLE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (_titleContainer != null) {
            //计算出标题栏左侧已占用的空间
            var leftUseSpace = paddingLeft
            for (view in _leftViewList) {
                leftUseSpace += view.measuredWidth
            }


            //计算出标题栏右侧已占用的空间
            var rightUseSpace = paddingRight
            for (view in _rightViewList) {
                rightUseSpace += view.measuredWidth
            }

            //这里只是他想要的宽度 500，300
            val titleContainerWidth = _titleContainer!!.measuredWidth
            //为了让标题居中，左右空余距离一样
            val remainingSpace = measuredWidth - leftUseSpace.coerceAtLeast(rightUseSpace) * 2
            if (remainingSpace < titleContainerWidth) {
                val size =
                    MeasureSpec.makeMeasureSpec(remainingSpace, MeasureSpec.EXACTLY)
                _titleContainer!!.measure(size, heightMeasureSpec)
            }
        }
    }

    private fun addLineView() {
        val view = View(context)
        val params = LayoutParams(LayoutParams.MATCH_PARENT, _attrs.lineWidth)
        params.addRule(ALIGN_PARENT_BOTTOM)
        view.layoutParams = params
        view.setBackgroundColor(_attrs.lineColor)
        addView(view)
    }

    private fun ensureTitleView() {
        if (_titleView == null) {
            _titleView = TextKIconFont(context, null)
            _titleView?.apply {
                gravity = Gravity.CENTER
                isSingleLine = true
                ellipsize = TextUtils.TruncateAt.END
                setTextColor(_attrs.titleTextColor)

                updateTitleViewStyle()
                ensureTitleContainer()
                _titleContainer?.addView(_titleView, 0)
            }
        }
    }

    private fun ensureSubTitleView() {
        if (_subTitleView == null) {
            _subTitleView = TextKIconFont(context, null)
            _subTitleView?.apply {
                gravity = Gravity.CENTER
                isSingleLine = true
                ellipsize = TextUtils.TruncateAt.END
                setTextColor(_attrs.subTitleTextColor)
                textSize = _attrs.subTitleTextSize

                //添加到titleContainer
                ensureTitleContainer()
                _titleContainer?.addView(_subTitleView)
            }
        }
    }

    private fun updateTitleViewStyle() {
        if (_titleView != null) {
            if (_subTitleView == null || TextUtils.isEmpty(_subTitleView!!.text)) {
                _titleView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, _attrs.titleTextSize)
                _titleView?.typeface = Typeface.DEFAULT_BOLD
            } else {
                _titleView?.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    _attrs.titleTextSizeWithSubTitle
                )
                _titleView?.typeface = Typeface.DEFAULT
            }
        }
    }

    private fun ensureTitleContainer() {
        if (_titleContainer == null) {
            _titleContainer = LinearLayout(context)
            _titleContainer?.apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER

                val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
                params.addRule(CENTER_IN_PARENT)
                this@NavKBar.addView(_titleContainer, params)
            }
        }
    }

    private fun generateTextButtonLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
    }

    private fun generateTextButton(): Button {
        val button = BtnKIconFont(context)
        button.setBackgroundResource(0)
        button.minWidth = 0
        button.minimumWidth = 0
        button.minHeight = 0
        button.minHeight = 0
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, _attrs.textBtnTextSize)
        button.setTextColor(_attrs.textBtnTextColor)
        button.gravity = Gravity.CENTER
        button.includeFontPadding = false
        return button
    }
}