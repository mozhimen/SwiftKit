package com.mozhimen.uicorektest.dialogk

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.permissionk.annors.APermissionKRequire
import com.mozhimen.basick.utilk.UtilKThread
import com.mozhimen.uicorektest.dialogk.temps.DialogKLoadingAnim
import com.mozhimen.uicorektest.dialogk.temps.DialogKLoadingAnimDrawable
import com.mozhimen.uicorektest.dialogk.temps.DialogKQues
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityDialogkBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@APermissionKRequire(CPermission.SYSTEM_ALERT_WINDOW)
class DialogKActivity : BaseActivityVB<ActivityDialogkBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.dialogkQues.setOnClickListener {
            genDialogKQues("你get到此用法了吗?", onSureClick = {})
        }

        vb.dialogkQuesAnim.setOnClickListener {
            genDialogKQuesAnim("带弹出动画的毛玻璃效果的弹框~")
        }

        vb.dialogkCustomAnimDrawable.setOnClickListener {
            showDialogLoadingAnimDrawable(true)
        }

        vb.dialogkCustomAnim.setOnClickListener {
            showDialogLoadingAnim(true)
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

    private var _dialogkLoadingAnimDrawable: DialogKLoadingAnimDrawable? = null

    fun showDialogLoadingAnimDrawable(cancelable: Boolean) {
        if (_dialogkLoadingAnimDrawable == null) {
            _dialogkLoadingAnimDrawable = DialogKLoadingAnimDrawable.create(this)
        }
        _dialogkLoadingAnimDrawable!!.setDialogCancelable(cancelable)
        if (!_dialogkLoadingAnimDrawable!!.isShowing) {
            lifecycleScope.launch(Dispatchers.Main) {
                _dialogkLoadingAnimDrawable!!.show()
            }
        }
    }

    fun dismissDialogLoadingAnimDrawable() {
        if (_dialogkLoadingAnimDrawable == null|| _dialogkLoadingAnimDrawable?.isShowing == false) return
        if (UtilKThread.isMainThread()) {
            _dialogkLoadingAnimDrawable!!.dismiss()
        } else {
            lifecycleScope.launch(Dispatchers.Main) {
                _dialogkLoadingAnimDrawable!!.dismiss()
            }
        }
    }

    private var _dialogkLoadingAnim: DialogKLoadingAnim? = null

    fun showDialogLoadingAnim(cancelable: Boolean) {
        if (_dialogkLoadingAnim == null) {
            _dialogkLoadingAnim = DialogKLoadingAnim.create(this)
        }
        _dialogkLoadingAnim!!.setDialogCancelable(cancelable)
        if (!_dialogkLoadingAnim!!.isShowing) {
            lifecycleScope.launch(Dispatchers.Main) {
                _dialogkLoadingAnim!!.show()
            }
        }
    }

    fun dismissDialogLoadingAnim() {
        if (_dialogkLoadingAnim == null|| _dialogkLoadingAnim?.isShowing == false) return
        if (UtilKThread.isMainThread()) {
            _dialogkLoadingAnim!!.dismiss()
        } else {
            lifecycleScope.launch(Dispatchers.Main) {
                _dialogkLoadingAnim!!.dismiss()
            }
        }
    }
}