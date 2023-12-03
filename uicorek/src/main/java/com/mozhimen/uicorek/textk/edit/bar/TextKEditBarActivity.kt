package com.mozhimen.uicorek.textk.edit.bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basick.utilk.android.widget.value
import com.mozhimen.uicorek.textk.edit.bar.bases.BaseEditBarHolder
import com.mozhimen.uicorek.textk.edit.bar.commons.ITextKEditBarListener
import com.mozhimen.uicorek.textk.edit.bar.cons.CTextKEditBar
import com.mozhimen.uicorek.textk.edit.bar.helpers.TextKEditBarHolderHelper
import com.mozhimen.uicorek.textk.edit.bar.mos.MTextKEditBarConfig
import java.util.regex.Pattern

/**
 * @ClassName FloatEditorActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/3 16:11
 * @Version 1.0
 */

class TextKEditBarActivity : FragmentActivity(), View.OnClickListener {
    private var _viewCancel: View? = null
    private var _viewSubmit: View? = null
    private var _edit: EditText? = null

    private var _editBarHolder: BaseEditBarHolder? = null
    private var _mTextKEditBarConfig: MTextKEditBarConfig? = null
    private var _isClicked = false

    ///////////////////////////////////////////////////////////////////////////

    companion object {
        private var _textKEditBarListener: ITextKEditBarListener? = null

        @JvmStatic
        fun openEditBar(context: Context, listener: ITextKEditBarListener?, holder: BaseEditBarHolder?) {
            openEditBar(context, listener, holder, null)
        }

        @JvmStatic
        fun openEditBarDefault(context: Context, listener: ITextKEditBarListener?, config: MTextKEditBarConfig?) {
            openEditBar(context, listener, TextKEditBarHolderHelper.createDefaultEditBarHolder(), config)
        }

        @JvmStatic
        fun openEditBar(context: Context, listener: ITextKEditBarListener?, holder: BaseEditBarHolder?, config: MTextKEditBarConfig?) {
            val intent = Intent(context, TextKEditBarActivity::class.java)
            intent.putExtra(CTextKEditBar.EXTRA_EDIT_BAR_HOLDER, holder)
            intent.putExtra(CTextKEditBar.EXTRA_EDIT_BAR_CONFIG, config)
            _textKEditBarListener = listener
            context.startContext(intent)
        }
    }

    ///////////////////////////////////////////////////////////////////////////

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _editBarHolder =
            intent.getSerializableExtra(CTextKEditBar.EXTRA_EDIT_BAR_HOLDER) as? BaseEditBarHolder?
        _mTextKEditBarConfig =
            intent.getSerializableExtra(CTextKEditBar.EXTRA_EDIT_BAR_CONFIG) as? MTextKEditBarConfig?

        if (_editBarHolder == null) throw RuntimeException("EditorHolder params not found!")
        setContentView(_editBarHolder!!.layoutId)

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.BOTTOM)
        _textKEditBarListener?.onAttached(window.decorView as ViewGroup)

        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!_isClicked)
            _textKEditBarListener?.onCancel()
        _textKEditBarListener = null
    }


    override fun onClick(v: View) {
        when (v.id) {
            _editBarHolder!!.viewIdCancel -> {
                _textKEditBarListener?.onCancel()
            }

            _editBarHolder!!.viewIdSubmit -> {
                if (_mTextKEditBarConfig != null && !(_mTextKEditBarConfig!!.minLength == 0 && _mTextKEditBarConfig!!.maxLength == 0)) {
                    if (onCheck()) {
                        _isClicked = true
                        _textKEditBarListener?.onSubmit(_edit!!.value)
                        finish()
                    }
                    return
                }
                _textKEditBarListener?.onSubmit(_edit!!.value)
            }
        }
        _isClicked = true
        finish()
    }

    private fun onCheck(): Boolean {
        val content = _edit!!.value
        if (TextUtils.isEmpty(content) || content.length < _mTextKEditBarConfig!!.minLength) {
            _textKEditBarListener?.onIllegal()
//            Toast.makeText(this, getString(R.string.view_component_limit_min_warn, _mTextKEditBarConfig.minLength), Toast.LENGTH_SHORT).show()
            return false
        }
        if (content.length > _mTextKEditBarConfig!!.maxLength) {
            _textKEditBarListener?.onIllegal()
//            Toast.makeText(this, getString(R.string.view_component_limit_max_warn, _mTextKEditBarConfig.maxLength), Toast.LENGTH_SHORT).show()
            return false
        }
        if (!TextUtils.isEmpty(_mTextKEditBarConfig!!.regexRule)) {
            _mTextKEditBarConfig!!.regexRule?.let {
                val pattern = Pattern.compile(it)
                val matcher = pattern.matcher(content)
                if (!matcher.matches()) {
                    _textKEditBarListener?.onIllegal()
                    return false
                }
            }

        }
        return true
    }

    private fun initView() {
        _editBarHolder?.viewIdCancel?.let {
            _viewCancel = findViewById(it)
        }
        _editBarHolder?.viewIdSubmit?.let {
            _viewSubmit = findViewById(it)
        }
        _editBarHolder?.editId?.let {
            _edit = findViewById(it)
        }
        _viewCancel?.setOnClickListener(this)
        _viewSubmit?.setOnClickListener(this)
    }
}

