package com.mozhimen.uicorektest.dialogk.temps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.animk.builder.temps.AnimKRotationRecyclerType
import com.mozhimen.basick.utilk.android.view.stopAnim
import com.mozhimen.basick.utilk.android.widget.applyValueIfNotEmpty
import com.mozhimen.uicorek.dialogk.bases.BaseDialogK
import com.mozhimen.uicorek.dialogk.bases.commons.IDialogKClickListener
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
    private val _desc: String? = null,
    private val _descUpdate: String? = null
) : BaseDialogK<IDialogKClickListener>(context) {

    companion object {
        @JvmOverloads
        fun create(context: Context, desc: String? = null, descUpdate: String? = null): DialogKLoadingUpdate {
            return DialogKLoadingUpdate(context, desc, descUpdate)
        }
    }

    private var _imgProgress: ImageView? = null
    private var _txtDesc: TextView? = null
    private var _txtUpdateDesc: TextView? = null
    private val _rotateAnimation by lazy { AnimKBuilder.asAnimation().add(AnimKRotationRecyclerType()).setDuration(2000).build() }

    init {
        setCancelable(true)
        setOnDismissListener {
            _imgProgress?.stopAnim()
        }
        setOnShowListener {
            _imgProgress?.startAnimation(_rotateAnimation)
        }
    }

    override fun onCreateView(inflater: LayoutInflater): View {
        return inflater.inflate(R.layout.dialogk_loading_update, null)
    }

    override fun onViewCreated(view: View) {
        _imgProgress = view.findViewById(R.id.dialogk_loading_update_img)
        _txtDesc = view.findViewById(R.id.dialogk_loading_update_txt)
        _txtUpdateDesc = view.findViewById(R.id.dialogk_loading_update_txt1)

        setDesc(_desc)
        setUpdateDesc(_descUpdate)
    }

    fun setDesc(desc: String?) {
        _txtDesc?.applyValueIfNotEmpty(desc)
    }

    fun setUpdateDesc(desc: String?) {
        _txtUpdateDesc?.applyValueIfNotEmpty(desc)
    }
}
