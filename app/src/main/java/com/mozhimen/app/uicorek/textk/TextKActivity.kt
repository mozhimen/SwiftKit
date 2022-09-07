package com.mozhimen.app.uicorek.textk

import android.os.Bundle
import android.view.View
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityTextkBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.uicorek.popwink.PopwinKBubbleText

class TextKActivity : BaseKActivity<ActivityTextkBinding, BaseKViewModel>(R.layout.activity_textk) {
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.textkBubbleBtn.setOnClickListener {
            genPopwinKBubbleText(it, "弹出了一个气泡提示")
        }
    }

    private val _popwinKBubbleText: PopwinKBubbleText? = null
    fun genPopwinKBubbleText(view: View, tip: String, delayMillis: Long = 2000) {
        _popwinKBubbleText?.dismiss()
        val builder = PopwinKBubbleText.Builder(this)
        builder.apply {
            setTip(tip)
            setDismissDelay(delayMillis)
            create(view)
        }
    }
}