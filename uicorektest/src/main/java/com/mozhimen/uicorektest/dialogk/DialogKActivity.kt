package com.mozhimen.uicorektest.dialogk

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.uicorektest.dialogk.temps.DialogKLoadingAnim
import com.mozhimen.uicorektest.dialogk.temps.DialogKLoadingAnimDrawable
import com.mozhimen.uicorektest.dialogk.temps.DialogKQues
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityDialogkBinding
import com.mozhimen.uicorektest.dialogk.temps.DialogKLoadingUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
@APermissionCheck(CPermission.SYSTEM_ALERT_WINDOW)
class DialogKActivity : BaseActivityVB<ActivityDialogkBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        VB.dialogkQues.setOnClickListener {
            genDialogKQues("你get到此用法了吗?", onSureClick = {})
        }

        VB.dialogkQuesAnim.setOnClickListener {
            genDialogKQuesAnim("带弹出动画的毛玻璃效果的弹框~")
        }

        VB.dialogkCustomAnimDrawable.setOnClickListener {
            showDialogLoadingAnimDrawable()
        }

        VB.dialogkCustomAnim.setOnClickListener {
            showDialogLoadingAnim()
        }

        VB.dialogkCustomUpdate.setOnClickListener {
            showLoadingUpdateDialog("正在更新", "...")
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
            animStyleId = R.style.DialogKQues_Anim_Custom
        }
        _dialogKQues = builder.create(onSureClick, onCancelClick)
        builder.genBackground {
            background.alpha = 200
        }
        _dialogKQues!!.show()
    }

    private var _dialogkLoadingAnimDrawable: DialogKLoadingAnimDrawable? = null

    fun showDialogLoadingAnimDrawable() {
        if (_dialogkLoadingAnimDrawable == null) {
            _dialogkLoadingAnimDrawable = DialogKLoadingAnimDrawable.create(this)
        }
        _dialogkLoadingAnimDrawable!!.show()
    }

    private var _dialogkLoadingAnim: DialogKLoadingAnim? = null

    fun showDialogLoadingAnim() {
        if (_dialogkLoadingAnim == null) {
            _dialogkLoadingAnim = DialogKLoadingAnim.create(this)
        }
        _dialogkLoadingAnim!!.show()
    }

    private var _dialogKLoadingUpdate: DialogKLoadingUpdate? = null

    fun showLoadingUpdateDialog(desc: String, descUpdate: String) {
        if (_dialogKLoadingUpdate == null) {
            _dialogKLoadingUpdate = DialogKLoadingUpdate.create(this@DialogKActivity, desc, descUpdate).apply {
                setOnDismissListener {
                    // _isProcessingUpdate = false
                    Log.d(TAG, "showLoadingUpdateDialog: dismiss")
                }
            }
        } else {
            _dialogKLoadingUpdate!!.setDesc(desc)
            _dialogKLoadingUpdate!!.setUpdateDesc(descUpdate)
        }
        _dialogKLoadingUpdate!!.show()
    }

    fun updateLoadingUpdateDialog(str: String) {
        if (_dialogKLoadingUpdate != null && _dialogKLoadingUpdate!!.isShowing) {
            lifecycleScope.launch(Dispatchers.Main) {
                _dialogKLoadingUpdate!!.setUpdateDesc(str)
            }
        }
    }
}