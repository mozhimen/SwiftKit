package com.mozhimen.app.uicorek.dialogk

import android.os.Bundle
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityDialogkBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.uicorek.dialogk.DialogKQues

class DialogKActivity : BaseKActivity<ActivityDialogkBinding, BaseKViewModel>(R.layout.activity_dialogk) {
    override fun initData(savedInstanceState: Bundle?) {
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
        val builder = DialogKQues.Builder(this).setQuestion(title = ques).setAnimStyleId(R.style.DialogKAnimStyleCustom)
        _dialogKQues = builder.create(onSureClick, onCancelClick)
        builder.genBackground {
            background.alpha = 200
        }
        _dialogKQues!!.show()
    }
}