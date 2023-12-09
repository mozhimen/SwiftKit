package com.mozhimen.uicorek.textk

import android.content.Context
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.commons.IUicoreK

/**
 * @ClassName TextKExpandable
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/7 17:20
 * @Version 1.0
 */
class TextKExpandable @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr), IUicoreK,
    View.OnClickListener {

    private val _expandable = false
    private var _maxLines = 3
    private var _strOrigin: CharSequence = ""//源文字
    private var _strFold: CharSequence? = null//收起的文字
    private var _strExpand: CharSequence? = null//展开的文字
    private var _textKExpandListener: IA_Listener<Boolean>? = null
    private var _textKIsExpandableListener: IA_Listener<Boolean>? = null//文字过短则不需要展开

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        initAttrs(attrs)
        this.post {
            setLastIndexForLimit(_strOrigin, width, _maxLines)
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    override fun initAttrs(attrs: AttributeSet?) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextKExpandable)
        _maxLines = typedArray.getInt(R.styleable.TextKExpandable_textKExpandable_maxLines, _maxLines)
        typedArray.recycle()
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    //折叠文本
    fun foldText() {
        if (!this.isSelected) return
        this.performClick()
    }

    fun expandText() {
        if (this.isSelected) return
        this.performClick()
    }

    fun toggleExpand() {
        this.performClick()
    }

    fun setTextKExpandListener(listener: IA_Listener<Boolean>) {
        _textKExpandListener = listener
    }

    fun setTextKIsExpandableListener(listener: IA_Listener<Boolean>) {
        _textKIsExpandableListener = listener
    }

    fun setExpandableText(text: CharSequence) {
        setExpandableText(text, _maxLines)
    }

    fun setExpandableText(text: CharSequence, maxLines: Int) {
        Log.d(TAG, "setExpandableText: maxLine $maxLines")
//        setLastIndexForLimit(text, maxLine)
        _maxLines = maxLines
        _strOrigin = text
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /*    private fun setLastIndexForLimit(foldLines: Int, content: CharSequence) {
            val paint = paint//获取TextView的画笔对象
            val width = resources.displayMetrics.widthPixels - 40f.dp2px.toInt()//每行文本的布局宽度
            val staticLayout = StaticLayout(content, paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false)//实例化StaticLayout 传入相应参数
            if (staticLayout.lineCount > foldLines) {//判断content是行数是否超过最大限制行数3行
    //            if (_expandable) {
    //////                val string1 = "$content    收起"
    ////                val notElipseString = SpannableString(content)//定义展开后的文本内容
    ////                //给收起两个字设成蓝色
    ////                notElipseString.setSpan(
    ////                    ForegroundColorSpan(Color.parseColor("#00A667")),
    ////                    string1.length - 2,
    ////                    string1.length,
    ////                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    ////                )
    //                _strExpand = notElipseString
    //            } else {
    //_strExpand = content
    //            }
                _strExpand = content

                val index = staticLayout.getLineStart(foldLines) - 1//获取到第三行最后一个文字的下标
                //定义收起后的文本内容
                val substring = content.substring(0, index - 2) + "展开"
                val elipseString = SpannableString(substring)
                //给查看全部设成蓝色
                elipseString.setSpan(
                    ForegroundColorSpan(Color.parseColor("#00A668")),
                    substring.length - 2,
                    substring.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                _strFold = elipseString
                //设置收起后的文本内容
                text = elipseString
                setOnClickListener(this)
                //将textview设成选中状态 true用来表示文本未展示完全的状态,false表示完全展示状态，用于点击时的判断
                isSelected = true
            } else {
                //没有超过 直接设置文本
                text = content
                setOnClickListener(null)
            }
        }*/

    private fun setLastIndexForLimit(content: CharSequence, width: Int, maxLine: Int) {
        val paint = paint//获取TextView的画笔对象
//        val width = resources.displayMetrics.widthPixels - 40f.dp2px.toInt()//每行文本的布局宽度
        val staticLayout = StaticLayout(content, paint, width /*width*/, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false)//实例化StaticLayout 传入相应参数
        if (staticLayout.lineCount > maxLine) {//判断content是行数是否超过最大限制行数3行
            _strExpand = content
            val position = staticLayout.getLineStart(maxLine) - 4//获取到第三行最后一个文字的下标-4是为了加...
            val strFold = content.substring(0, position) + "..."//定义收起后的文本内容
            _strFold = strFold
            Log.d(TAG, "setLastIndexForLimit: _strFold $_strFold")

            ///////////////////////////////////////////////////////////////////////////

            text = strFold//设置收起后的文本内容
            setOnClickListener(this)
            isSelected = true//将textview设成选中状态 true用来表示文本未展示完全的状态,false表示完全展示状态，用于点击时的判断
            _textKIsExpandableListener?.invoke(true)
        } else {
            text = content//没有超过 直接设置文本
            setOnClickListener(null)
            _textKIsExpandableListener?.invoke(false)
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View) {
        if (!isSelected) {//如果是收起的状态
            text = _strExpand
            isSelected = true
        } else {//如果是展开的状态
            text = _strFold
            isSelected = false
        }
        _textKExpandListener?.invoke(v.isSelected)
    }
}