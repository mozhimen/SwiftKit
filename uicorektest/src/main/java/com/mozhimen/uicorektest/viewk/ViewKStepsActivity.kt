package com.mozhimen.uicorektest.viewk

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityViewkStepsBinding

class ViewKStepsActivity : BaseKActivity<ActivityViewkStepsBinding, BaseKViewModel>(R.layout.activity_viewk_steps) {

    override fun initData(savedInstanceState: Bundle?) {
        vb.viewkStepsViewksteps.setTitles(arrayOf("第一步", "第二步", "第三步", "第四步"))

        vb.viewkStepsBtnReset.setOnClickListener {
            vb.viewkStepsViewksteps.reset()
        }

        vb.viewkStepsBtnBack.setOnClickListener {
            vb.viewkStepsViewksteps.back()
        }

        vb.viewkStepsBtnNext.setOnClickListener {
            vb.viewkStepsViewksteps.next()
        }
    }

    private var eventX = 0.0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        //获取手指在屏幕上的坐标
        when (event.action) {
            MotionEvent.ACTION_DOWN -> eventX = event.x
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> if (event.x - eventX > 0) {
                Log.i(TAG, "右")
                vb.viewkStepsViewksteps.back()
            } else if (event.x - eventX < 0) {
                Log.i(TAG, "左")
                vb.viewkStepsViewksteps.next()
            }
        }
        return true
    }
}