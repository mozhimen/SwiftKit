package com.mozhimen.uicorektest.dialogk

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.uicorek.dialogk.DialogKQues
import com.mozhimen.uicorek.dialogk.bases.BaseDialog
import com.mozhimen.uicorek.dialogk.bases.commons.IDialogClickListener
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityDialogkBinding

class DialogKActivity : BaseActivityVB<ActivityDialogkBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.dialogkQues.setOnClickListener {
            genDialogKQues("你get到此用法了吗?", onSureClick = {})
        }

        vb.dialogkQuesAnim.setOnClickListener {
            genDialogKQuesAnim("带弹出动画的毛玻璃效果的弹框~")
        }
    }

    private var _dialogKQues: DialogKQues? = null
    private fun genDialogKQues(ques: String, onSureClick: () -> Unit, onCancelClick: (() -> Unit)? = null) {
        _dialogKQues?.dismiss()
        val builder = DialogKQues.Builder(this).setQuestion(title = ques)
        _dialogKQues = builder.create(onSureClick, onCancelClick)
        _dialogKQues!!.show()
    }

    private fun genDialogKQuesAnim(ques: String, onSureClick: (() -> Unit)? = null, onCancelClick: (() -> Unit)? = null) {
        _dialogKQues?.dismiss()
        val builder = DialogKQues.Builder(this)
        builder.apply {
            setQuestion(title = ques)
            animStyleId = R.style.DialogKAnim_Custom
        }
        _dialogKQues = builder.create(onSureClick, onCancelClick)
        builder.genBackground {
            background.alpha = 200
        }
        _dialogKQues!!.show()
    }

    class LoadingDialog(context: Context) : BaseDialog<IDialogClickListener>(context) {
        override fun onCreateView(inflater: LayoutInflater): View {
            return inflater.inflate(R.layout.dialogk_loading, null)
        }

        override fun onFindView(dialogView: View) {

        }

        override fun onInitMode(mode: Int) {

        }
    }
}