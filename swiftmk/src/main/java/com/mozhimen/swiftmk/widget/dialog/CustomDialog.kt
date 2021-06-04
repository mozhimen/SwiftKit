package com.mozhimen.swiftmk.widget.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.mozhimen.swiftmk.R

/**
 * @ClassName CustomDialog
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/4 16:47
 * @Version 1.0
 */

/**
 * 作用: 自定义弹框
 * 用法: val builder = CustomDialog.Builder(this)
 * builder.setPositiveButton(DialogInterface.OnClickListener { dialogInterface, _ ->
 *  dialogInterface.dismiss()
 *  //具体逻辑
 * })
 * builder.setNegativeButton(DialogInterface.OnClickListener { dialogInterface, _ ->
 *  dialogInterface.dismiss()
 *  //具体逻辑
 * })
 * builder.create().show()
 */
class CustomDialog : Dialog {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, theme: Int) : super(context!!, theme)

    class Builder(
        private val context: Context,
        private val theme: Int = R.style.CustomDialog,
        private val layout: Int = R.layout.dialog_default
    ) {
        private var content = "确定吗"
        private var positiveButtonText = "确定"
        private var negativeButtonText: String = "取消"
        private var positiveButtonClickListener: DialogInterface.OnClickListener? = null
        private var negativeButtonClickListener: DialogInterface.OnClickListener? = null

        fun setContent(content: String): Builder {
            this.content = content
            return this
        }

        fun setContent(resId: Int): Builder {
            this.content = context.getText(resId) as String
            return this
        }

        /**
         * 按钮部分
         */
        fun setPositiveButton(
            listener: DialogInterface.OnClickListener,
            positiveButtonText: String = "确定"
        ): Builder {
            this.positiveButtonClickListener = listener
            this.positiveButtonText = positiveButtonText
            return this
        }

        fun setPositiveButton(
            listener: DialogInterface.OnClickListener,
            resId: Int
        ): Builder {
            this.positiveButtonClickListener = listener
            this.positiveButtonText = context.getText(resId) as String
            return this
        }

        fun setNegativeButton(
            listener: DialogInterface.OnClickListener,
            negativeButtonText: String = "取消"
        ): Builder {
            this.negativeButtonClickListener = listener
            this.negativeButtonText = negativeButtonText
            return this
        }

        fun setNegativeButton(
            listener: DialogInterface.OnClickListener,
            resId: Int
        ): Builder {
            this.negativeButtonClickListener = listener
            this.negativeButtonText = context.getText(resId) as String
            return this
        }

        fun create(): CustomDialog {
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            //为自定义弹框设置主题
            val customDialog = CustomDialog(context, theme)
            val dialogView = layoutInflater.inflate(layout, null)
            customDialog.addContentView(
                dialogView, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
            //设置弹框内容
            (dialogView.findViewById(R.id.dialog_default_content) as TextView).text = content
            //设置弹框按钮文本
            (dialogView.findViewById(R.id.dialog_default_cancel) as TextView).text =
                negativeButtonText
            (dialogView.findViewById(R.id.dialog_default_sure) as TextView).text =
                positiveButtonText
            //设置弹框按钮
            positiveButtonClickListener?.let {
                (dialogView.findViewById(R.id.dialog_default_sure) as Button).setOnClickListener {
                    positiveButtonClickListener!!.onClick(
                        customDialog,
                        DialogInterface.BUTTON_POSITIVE
                    )
                }
            } ?: run {
                (dialogView.findViewById(R.id.dialog_default_sure) as Button).visibility = View.GONE
            }
            negativeButtonClickListener?.let {
                (dialogView.findViewById(R.id.dialog_default_cancel) as Button).setOnClickListener {
                    negativeButtonClickListener!!.onClick(
                        customDialog,
                        DialogInterface.BUTTON_NEGATIVE
                    )
                }
            } ?: run {
                (dialogView.findViewById(R.id.dialog_default_cancel) as Button).visibility =
                    View.GONE
            }
            customDialog.setContentView(dialogView)
            return customDialog
        }
    }
}