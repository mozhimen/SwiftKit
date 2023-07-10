package com.mozhimen.uicorektest.textk

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.uicorek.popwink.PopwinKTextKBubbleBuilder
import com.mozhimen.uicorektest.databinding.ActivityTextkBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TextKActivity : BaseActivityVB<ActivityTextkBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.textkBubbleBtn.setOnClickListener {
            genPopwinKBubbleText(it, "弹出了一个气泡提示")
        }
    }

    private val _popwinKTextKBubbleBuilder: PopwinKTextKBubbleBuilder? = null
    private fun genPopwinKBubbleText(view: View, tip: String, delayMillis: Long = 4000) {
        _popwinKTextKBubbleBuilder?.dismiss()
        val builder = PopwinKTextKBubbleBuilder.Builder(this)
        builder.apply {
            setTip(tip)
            setDismissDelay(delayMillis)
            create(view)
        }
    }
}