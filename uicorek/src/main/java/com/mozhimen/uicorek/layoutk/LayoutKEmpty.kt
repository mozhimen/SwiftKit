package com.mozhimen.uicorek.layoutk

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.mozhimen.basick.elemk.commons.IExtension_Listener
import com.mozhimen.basick.utilk.android.view.asGone
import com.mozhimen.basick.utilk.android.view.asVisible
import com.mozhimen.basick.utilk.android.widget.applyIconFont
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKLinear
import com.mozhimen.uicorek.R

/**
 * @ClassName LayoutKEmpty
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/22 18:28
 * @Version 1.0
 */
class LayoutKEmpty @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseLayoutKLinear(context, attrs, defStyleAttr) {

    private lateinit var _bgView: View
    private var _imageResId: Int? = null
    private var _iconFont: String? = null
    private var _btnStr: String? = null
    private var _titleStr: String? = null
    private var _contentStr: String? = null
    private var _helpIconFont: String? = null

    private lateinit var _imageView: ImageView
    private lateinit var _iconView: TextView
    private lateinit var _titleView: TextView
    private lateinit var _contentView: TextView
    private lateinit var _btn: Button
    private lateinit var _helpView: TextView

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKEmpty)
            _imageResId = typedArray.getResourceId(R.styleable.LayoutKEmpty_layoutKEmpty_image, -1)
            _iconFont = typedArray.getString(R.styleable.LayoutKEmpty_layoutKEmpty_iconFont)
            _titleStr = typedArray.getString(R.styleable.LayoutKEmpty_layoutKEmpty_title)
            _contentStr = typedArray.getString(R.styleable.LayoutKEmpty_layoutKEmpty_content)
            _helpIconFont = typedArray.getString(R.styleable.LayoutKEmpty_layoutKEmpty_helpIconFont)
            _btnStr = typedArray.getString(R.styleable.LayoutKEmpty_layoutKEmpty_buttonTitle)
            typedArray.recycle()
        }
    }

    override fun initView() {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        _bgView = LayoutInflater.from(context).inflate(R.layout.layoutk_empty, this, true)
        _iconView = _bgView.findViewById(R.id.layoutk_empty_icon_font)
        _imageView = _bgView.findViewById(R.id.layoutk_empty_img)
        _titleView = _bgView.findViewById(R.id.layoutk_empty_title)
        _contentView = _bgView.findViewById(R.id.layoutk_empty_txt)
        _helpView = _bgView.findViewById(R.id.layoutk_empty_help_icon_font)
        _btn = _bgView.findViewById(R.id.layoutk_empty_btn)

        _iconFont?.let { setIcon(it) }
        if (_imageResId != -1 && _imageResId != null) setImage(_imageResId!!)
        _titleStr?.let { setTitle(it) }
        _contentStr?.let { setContent(it) }
        _helpIconFont?.let { setHelpAction(it) }
        _btnStr?.let { setButton(it) }
    }

    fun applyBgView(invoke: IExtension_Listener<View>) {
        _bgView.invoke()
    }

    /**
     * 设置icon，需要在string.xml中定义 iconfont.ttf中的unicode码
     * @param iconStr String?
     */
    fun setIcon(iconStr: String) {
        if (!TextUtils.isEmpty(iconStr)) {
            _iconView.text = iconStr
            _iconView.applyIconFont()
            _iconView.asVisible()
        } else _iconView.asGone()
    }

    /**
     * 设置图片
     * @param resId Int
     */
    fun setImage(resId: Int = R.mipmap.layoutk_empty) {
        if (resId != null) {
            _imageView.setImageResource(resId)
            _imageView.asVisible()
        } else _imageView.asGone()
    }

    fun applyImage(invoke: IExtension_Listener<ImageView>) {
        _imageView.invoke()
    }

    /**
     * 设置标题
     * @param title String?
     */
    fun setTitle(title: String) {
        if (!TextUtils.isEmpty(title)) {
            _titleView.text = title
            _titleView.asVisible()
        } else _titleView.asGone()
    }

    fun applyTitle(invoke: IExtension_Listener<TextView>) {
        _titleView.invoke()
    }

    /**
     * 设置正文
     * @param content String?
     */
    fun setContent(content: String) {
        if (!TextUtils.isEmpty(content)) {
            _contentView.text = content
            _contentView.asVisible()
        } else _contentView.asGone()
    }

    fun applyContent(invoke: IExtension_Listener<TextView>) {
        _contentView.invoke()
    }

    /**
     * 设置提示小图标
     * @param iconStr String?
     * @param listener OnClickListener?
     */
    fun setHelpAction(iconStr: String, listener: OnClickListener? = null) {
        if (!TextUtils.isEmpty(iconStr)) {
            _helpView.text = iconStr
            listener?.let { _helpView.setOnClickListener(it) }
            _helpView.asVisible()
        } else _helpView.asGone()
    }

    /**
     * 设置按钮
     * @param text String?
     */
    fun setButton(text: String, listener: OnClickListener? = null) {
        if (!TextUtils.isEmpty(text)) {
            _btn.text = text
            listener?.let { _btn.setOnClickListener(it) }
            _btn.asVisible()
        } else _btn.asGone()
    }

    fun applyButton(invoke: IExtension_Listener<Button>) {
        _btn.invoke()
    }
}