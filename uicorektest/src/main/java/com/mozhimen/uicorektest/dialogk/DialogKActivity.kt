package com.mozhimen.uicorektest.dialogk

import android.app.Dialog
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.UtilKThread
import com.mozhimen.uicorek.dialogk.temps.DialogKLoading
import com.mozhimen.uicorek.dialogk.temps.DialogKQues
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityDialogkBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogKActivity : BaseActivityVB<ActivityDialogkBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.dialogkQues.setOnClickListener {
            genDialogKQues("你get到此用法了吗?", onSureClick = {})
        }

        vb.dialogkQuesAnim.setOnClickListener {
            genDialogKQuesAnim("带弹出动画的毛玻璃效果的弹框~")
        }

        vb.dialogkCustom.setOnClickListener {
            showLoadingDialog(true)
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

    private var _dialogkLoading: Dialog? = null

    fun showLoadingDialog(cancelable: Boolean) {
        if (_dialogkLoading == null) {
            _dialogkLoading = createDialogKLoading()
        }
        _dialogkLoading!!.setCancelable(cancelable)
        if (!_dialogkLoading!!.isShowing) {
            lifecycleScope.launch(Dispatchers.Main) {
                _dialogkLoading!!.show()
            }
        }
    }

    fun dismissLoadingDialog() {
        if (_dialogkLoading == null) return
        if (UtilKThread.isMainThread()) {
            _dialogkLoading!!.dismiss()
        } else {
            lifecycleScope.launch(Dispatchers.Main) {
                _dialogkLoading!!.dismiss()
            }
        }
    }

    fun createDialogKLoading(): DialogKLoading {
        return DialogKLoading.create(this)
    }
}