package com.mozhimen.uicorek.dialogk.temps

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.animk.builder.temps.RotationRecyclerType
import com.mozhimen.basick.utilk.exts.stopAnim
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.dialogk.bases.BaseDialog
import com.mozhimen.uicorek.dialogk.commons.IDialogKClickListener

/**
 * @ClassName LoadingDialog
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/19 15:37
 * @Version 1.0
 */
class DialogKLoadingAnim @JvmOverloads internal constructor(context: Context, desc: String? = null) : BaseDialog<IDialogKClickListener>(context) {
    private var _imgProgress: ImageView? = null
    private var _txtDesc: TextView? = null
    private var _descStr: String?
    private val _rotateAnimation by lazy { AnimKBuilder.asAnimation().add(RotationRecyclerType()).setDuration(1000).build() }

    init {
        _descStr = desc
        setOnDismissListener {
            _imgProgress?.stopAnim()
        }
        setOnShowListener {
            _imgProgress?.startAnimation(_rotateAnimation)
        }
    }

    companion object {
        @JvmOverloads
        fun create(context: Context, desc: String? = null): DialogKLoadingAnim {
            return DialogKLoadingAnim(context, desc)
        }
    }

    override fun onCreateView(inflater: LayoutInflater): View {
        return inflater.inflate(R.layout.dialogk_loading_anim, null)
    }

    override fun onFindView(dialogView: View) {
        _imgProgress = dialogView.findViewById(R.id.dialogk_loading_img_progress)
        _txtDesc = dialogView.findViewById(R.id.dialogk_loading_txt_desc)
        if (!TextUtils.isEmpty(_descStr)) {
            _txtDesc!!.text = _descStr
        }
    }

    fun setDesc(desc: String) {
        _descStr = desc
        if (this._txtDesc != null) {
            _txtDesc!!.text = desc
        }
    }

    override fun onInitMode(mode: Int) {}
}