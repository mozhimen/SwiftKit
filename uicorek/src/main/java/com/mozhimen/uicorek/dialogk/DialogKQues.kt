package com.mozhimen.uicorek.dialogk

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.mozhimen.uicorek.dialogk.commons.IDialogKBuilder
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.uicorek.R

/**
 * @ClassName DialogKQues
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/31 14:19
 * @Version 1.0
 */
class DialogKQues @JvmOverloads constructor(context: Context, themeId: Int = 0) : Dialog(context, themeId) {

    class Builder(private val _context: Context) : IDialogKBuilder {

        //region # variate
        private lateinit var _bg: FrameLayout
        private lateinit var _titleView: TextView
        private lateinit var _btnCancel: MaterialButton
        private lateinit var _btnSure: MaterialButton

        private var _title: String = "请替换你的问题!"
        //endregion

        override var layoutId: Int = R.layout.dialogk_ques
        override var width: Float = 300f
        override var height: Float = 120f
        override var layoutParams: ViewGroup.LayoutParams =
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        override var animStyleId: Int? = null
        override var styleId: Int = R.style.DialogKStyle_Theme
        override var cancelable: Boolean = true

        fun setQuestion(title: String): Builder {
            this._title = title
            return this
        }

        fun genBackground(block: FrameLayout.() -> Unit) {
            _bg.block()
        }

        fun genTitle(block: TextView.() -> Unit) {
            _titleView.block()
        }

        fun genBtnCancel(block: MaterialButton.() -> Unit) {
            _btnCancel.block()
        }

        fun genBtnSure(block: MaterialButton.() -> Unit) {
            _btnSure.block()
        }

        fun create(onSureClick: (() -> Unit)? = null, onCancelClick: (() -> Unit)? = null): DialogKQues {
            val layoutInflater = _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogKQues = DialogKQues(_context, styleId)
            val view = layoutInflater.inflate(layoutId, null)
            dialogKQues.addContentView(
                view, layoutParams
            )

            _bg = view.findViewById(R.id.dialogk_ques_bg)
            _titleView = view.findViewById(R.id.dialogk_ques_title)
            _titleView.text = _title
            _btnCancel = view.findViewById(R.id.dialogk_ques_btn_cancel)
            _btnSure = view.findViewById(R.id.dialogk_ques_btn_sure)

            _btnCancel.setOnClickListener {
                dialogKQues.dismiss()
                onCancelClick?.invoke()
            }
            _btnSure.setOnClickListener {
                dialogKQues.dismiss()
                onSureClick?.invoke()
            }
            dialogKQues.setContentView(view)
            dialogKQues.setCancelable(cancelable)
            dialogKQues.window?.setWindowAnimations(animStyleId ?: R.style.DialogKAnim_Alpha)
            dialogKQues.window?.setLayout(width.dp2px(), height.dp2px())
            return dialogKQues
        }
    }
}