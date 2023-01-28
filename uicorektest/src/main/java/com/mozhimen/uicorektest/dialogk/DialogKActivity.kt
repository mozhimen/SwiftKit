package com.mozhimen.uicorektest.dialogk

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.UtilKThread
import com.mozhimen.uicorektest.dialogk.temps.DialogKLoadingAnim
import com.mozhimen.uicorektest.dialogk.temps.DialogKLoadingAnimDrawable
import com.mozhimen.uicorektest.dialogk.temps.DialogKQues
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityDialogkBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
@APermissionCheck(CPermission.SYSTEM_ALERT_WINDOW)
class DialogKActivity : BaseActivityVB<ActivityDialogkBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.dialogkQues.setOnClickListener {
            genDialogKQues("你get到此用法了吗?", onSureClick = {})
        }

        vb.dialogkQuesAnim.setOnClickListener {
            genDialogKQuesAnim("带弹出动画的毛玻璃效果的弹框~")
        }

        vb.dialogkCustomAnimDrawable.setOnClickListener {
            showDialogLoadingAnimDrawable()
        }

        vb.dialogkCustomAnim.setOnClickListener {
            showDialogLoadingAnim()
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
    private var _dialogkLoadingAnim: DialogKLoadingAnim? = null

    fun showDialogLoadingAnimDrawable() {
        if (_dialogkLoadingAnimDrawable == null) {
            _dialogkLoadingAnimDrawable = DialogKLoadingAnimDrawable.create(this)
        }
        _dialogkLoadingAnimDrawable!!.show()
    }

    fun showDialogLoadingAnim() {
        if (_dialogkLoadingAnim == null) {
            _dialogkLoadingAnim = DialogKLoadingAnim.create(this)
        }
        _dialogkLoadingAnim!!.show()
    }
}