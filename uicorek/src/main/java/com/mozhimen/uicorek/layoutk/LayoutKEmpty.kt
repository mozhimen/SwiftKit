package com.mozhimen.uicorek.layoutk

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.mozhimen.basick.extsk.view.font
import com.mozhimen.basick.basek.BaseKLayoutLinear
import com.mozhimen.uicorek.R

/**
 * @ClassName LayoutKEmpty
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/22 18:28
 * @Version 1.0
 */
class LayoutKEmpty @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :
    BaseKLayoutLinear(context, attrs, defStyleAttr) {

    private var _imageResId: Int? = null
    private var _iconFont: String? = null
    private var _btnStr: String? = null
    private var _titleStr: String? = null
    private var _contentStr: String? = null
    private var _helpIconFont: String? = null

    private lateinit var _imageView: ImageView
    private lateinit var _iconView: TextView
    private lateinit var _titleView: TextView
    private lateinit var _txtView: TextView
    private lateinit var _btn: Button
    private lateinit var _helpView: TextView

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKEmpty)
        _imageResId = typedArray.getResourceId(R.styleable.LayoutKEmpty_layoutKEmpty_image, -1)
        _iconFont = typedArray.getString(R.styleable.LayoutKEmpty_layoutKEmpty_iconFont)
        _titleStr = typedArray.getString(R.styleable.LayoutKEmpty_layoutKEmpty_title)
        _contentStr = typedArray.getString(R.styleable.LayoutKEmpty_layoutKEmpty_content)
        _helpIconFont = typedArray.getString(R.styleable.LayoutKEmpty_layoutKEmpty_helpIconFont)
        _btnStr = typedArray.getString(R.styleable.LayoutKEmpty_layoutKEmpty_buttonTitle)
        typedArray.recycle()
    }

    override fun initView() {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        LayoutInflater.from(context).inflate(R.layout.layoutk_empty, this, true)

        _iconView = findViewById(R.id.layoutk_empty_icon_font)
        _imageView = findViewById(R.id.layoutk_empty_img)
        _titleView = findViewById(R.id.layoutk_empty_title)
        _txtView = findViewById(R.id.layoutk_empty_txt)
        _helpView = findViewById(R.id.layoutk_empty_help_icon_font)
        _btn = findViewById(R.id.layoutk_empty_btn)

        _iconFont?.let {
            setIcon(it)
        }
        if (_imageResId != -1) {
            setImage(_imageResId)
        }
        _titleStr?.let {
            setTitle(it)
        }
        _contentStr?.let {
            setContent(it)
        }
        _helpIconFont?.let {
            setHelpAction(it)
        }
        _btnStr?.let {
            setButton(_btnStr)
        }
    }

    /**
     * 设置icon，需要在string.xml中定义 iconfont.ttf中的unicode码
     * @param iconStr String?
     */
    fun setIcon(iconStr: String?) {
        _iconView.visibility = if (iconStr != null && !TextUtils.isEmpty(iconStr)) {
            _iconView.text = iconStr
            _iconView.font()
            VISIBLE
        } else GONE
    }

    /**
     * 设置图片
     * @param resId Int
     */
    fun setImage(resId: Int? = R.mipmap.layoutk_empty) {
        _imageView.visibility = if (resId != null) {
            _imageView.setImageResource(resId)
            VISIBLE
        } else GONE
    }

    /**
     * 设置标题
     * @param title String?
     */
    fun setTitle(title: String?) {
        _titleView.visibility = if (title != null && !TextUtils.isEmpty(title)) {
            _titleView.text = title
            VISIBLE
        } else GONE
    }

    /**
     * 设置正文
     * @param content String?
     */
    fun setContent(content: String?) {
        _txtView.visibility = if (content != null && !TextUtils.isEmpty(content)) {
            _txtView.text = content
            VISIBLE
        } else GONE
    }

    /**
     * 设置提示小图标
     * @param iconStr String?
     * @param listener OnClickListener?
     */
    fun setHelpAction(iconStr: String?, listener: OnClickListener? = null) {
        _helpView.visibility = if (iconStr != null && !TextUtils.isEmpty(iconStr)) {
            _helpView.text = iconStr
            listener?.let { _helpView.setOnClickListener(it) }
            VISIBLE
        } else GONE
    }

    /**
     * 设置按钮
     * @param text String?
     * @param listener OnClickListener?
     */
    fun setButton(text: String?, listener: OnClickListener? = null) {
        _btn.visibility = if (text != null && !TextUtils.isEmpty(text)) {
            _btn.text = text
            listener?.let { _btn.setOnClickListener(it) }
            VISIBLE
        } else {
            GONE
        }
    }
}