package com.mozhimen.uicorektest.textk

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.uicorek.popwink.PopwinKTextKBubbleBuilder
import com.mozhimen.uicorektest.databinding.ActivityTextkBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TextKActivity : BaseActivityVB<ActivityTextkBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        VB.textkBubbleBtn.setOnClickListener {
            genPopwinKBubbleText(it, "弹出了一个气泡提示")
        }
        lifecycleScope.launch(Dispatchers.Main) {
            VB.textkProgress.setProgress(10)
            delay(2000)
            VB.textkProgress.setProgress(20)
            delay(2000)
            VB.textkProgress.setProgress(30)
            delay(2000)
            VB.textkProgress.setProgress(40)
            delay(2000)
            VB.textkProgress.setProgress(50)
            delay(2000)
            VB.textkProgress.setProgress(60)
            delay(2000)
            VB.textkProgress.setProgress(70)
            delay(2000)
            VB.textkProgress.setProgress(80)
            delay(2000)
            VB.textkProgress.setProgress(90)
            delay(2000)
            VB.textkProgress.setProgress(100)
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