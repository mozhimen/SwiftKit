package com.mozhimen.uicorektest.textk

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.uicorek.popwink.PopwinKBubbleText
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityTextkBinding

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
    private fun genPopwinKBubbleText(view: View, tip: String, delayMillis: Long = 4000) {
        _popwinKBubbleText?.dismiss()
        val builder = PopwinKBubbleText.Builder(this)
        builder.apply {
            setTip(tip)
            setDismissDelay(delayMillis)
            create(view)
        }
    }
}