package com.mozhimen.uicorektest.dialogk.temps

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.animk.builder.temps.RotationRecyclerType
import com.mozhimen.basick.utilk.exts.stopAnim
import com.mozhimen.uicorek.dialogk.bases.BaseDialogK
import com.mozhimen.uicorek.dialogk.commons.IDialogKClickListener
import com.mozhimen.uicorektest.R


/**
 * @ClassName DialogKLoadingUpdate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/31 11:28
 * @Version 1.0
 */
class DialogKLoadingUpdate @JvmOverloads internal constructor(
    context: Context,
    private var _desc: String? = null,
    private var _descUpdate: String? = null
) : BaseDialogK<IDialogKClickListener>(context) {

    private var _imgProgress: ImageView? = null
    private var _txtDesc: TextView? = null
    private var _txtUpdateDesc: TextView? = null
    private val _rotateAnimation by lazy { AnimKBuilder.asAnimation().add(RotationRecyclerType()).setDuration(2000).build() }

    init {
        setCancelable(true)
        setOnDismissListener {
            _imgProgress?.stopAnim()
        }
        setOnShowListener {
            _imgProgress?.startAnimation(_rotateAnimation)
        }
    }

    companion object {
        @JvmOverloads
        fun create(context: Context, desc: String? = null, descUpdate: String? = null): DialogKLoadingUpdate {
            return DialogKLoadingUpdate(context, desc, descUpdate)
        }
    }

    override fun onCreateView(inflater: LayoutInflater): View {
        return inflater.inflate(R.layout.dialogk_loading_update, null)
    }

    override fun onFindView(dialogView: View) {
        _imgProgress = dialogView.findViewById(R.id.dialogk_loading_update_img)
        _txtDesc = dialogView.findViewById(R.id.dialogk_loading_update_txt)
        _txtUpdateDesc = dialogView.findViewById(R.id.dialogk_loading_update_txt1)
        setDesc(_desc)
        setUpdateDesc(_descUpdate)
    }

    fun setDesc(desc: String?) {
        if (!TextUtils.isEmpty(desc)) {
            _txtDesc?.text = desc.also { _desc = it }
        }
    }

    fun setUpdateDesc(desc: String?) {
        if (!TextUtils.isEmpty(desc)) {
            _txtUpdateDesc?.text = desc.also { _descUpdate = it }
        }
    }
}
