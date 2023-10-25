package com.mozhimen.uicorektest.textk

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.uicorek.popwink.PopwinKTextKBubbleBuilder
import com.mozhimen.uicorek.textk.progress.TextKProgress
import com.mozhimen.uicorektest.databinding.ActivityTextkBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TextKActivity : BaseActivityVB<ActivityTextkBinding>() {
    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        vb.textkBubbleBtn.setOnClickListener {
            genPopwinKBubbleText(it, "弹出了一个气泡提示")
        }
        lifecycleScope.launch{
            vb.textkProgress.setCurrentText("10%")
            vb.textkProgress.setProgress(0f)
            vb.textkProgress.setProgressState(TextKProgress.PROGRESS_STATE_IDLE)
            delay(1000)
            vb.textkProgress.setProgressState(TextKProgress.PROGRESS_STATE_LOADING)
            delay(1000)
            vb.textkProgress.setProgress(50f)
            delay(1000)
            vb.textkProgress.setProgress(100f)
            vb.textkProgress.setProgressState(TextKProgress.PROGRESS_STATE_FINISH)
        }

        lifecycleScope.launch{
            vb.textkProgress1.setProgressText("0%",0)
            delay(1000)
            vb.textkProgress1.setProgressText("20%",20)
            delay(1000)
            vb.textkProgress1.setProgressText("40%",40)
            delay(1000)
            vb.textkProgress1.setProgressText("60%",60)
            delay(1000)
            vb.textkProgress1.setProgressText("80%",80)
            delay(1000)
            vb.textkProgress1.setProgressText("100%",100)
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