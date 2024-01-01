package com.mozhimen.uicorektest.textk

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
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
        lifecycleScope.launch {
            vb.textkProgress.setProgress(0f)
            vb.textkProgress.setCurrentText("0%")
            vb.textkProgress.setProgressState(TextKProgress.PROGRESS_STATE_LOADING)
            delay(1000)
            vb.textkProgress.setProgress(50f)
            vb.textkProgress.setCurrentText("10%")
            delay(1000)
            vb.textkProgress.setProgress(100f)
            vb.textkProgress.setCurrentText("100%")
            vb.textkProgress.setProgressState(TextKProgress.PROGRESS_STATE_FINISH)
        }

        lifecycleScope.launch {
            vb.textkProgress1.setProgressState(TextKProgress.PROGRESS_STATE_LOADING)
            vb.textkProgress1.setProgressText("0%", 0)
            delay(1000)
            vb.textkProgress1.setProgressText("20%", 20)
            delay(1000)
            vb.textkProgress1.setProgressText("40%", 40)
            delay(1000)
            vb.textkProgress1.setProgressText("60%", 60)
            delay(1000)
            vb.textkProgress1.setProgressText("80%", 80)
            delay(1000)
            vb.textkProgress1.setProgressText("100%", 100)
            vb.textkProgress1.setProgressState(TextKProgress.PROGRESS_STATE_FINISH)
        }
        vb.textkExpandable.setExpandableText(
            "@Override\n" +
                    "protected void onCreate(Bundle savedInstanceState) {\n" +
                    "super.onCreate(savedInstanceState);\n" +
                    "setContentView(R.layout.activity_main);\n" +
                    "collapsibleTextView = (CollapsibleTextView)\n" +
                    "findViewById(R.id.CollapsibleTextView);\n" +
                    "//设置文字会出现效果\n" +
                    "collapsibleTextView.setText(getResources().getString(R.string.content));\n" +
                    "}\n" +
                    "\n" +
                    "作者：miaozbetter\n" +
                    "链接：https://www.jianshu.com/p/61aec9d934e5\n" +
                    "来源：简书\n" +
                    "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。", 4
        )
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

    fun goTextKEdit(view: View) {
        startContext<TextKEditActivity>()
    }
}