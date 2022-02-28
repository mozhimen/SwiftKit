package com.mozhimen.app.uicoremk.viewmk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.mozhimen.app.databinding.ActivityViewmkStepsBinding

class ViewMKStepsActivity : AppCompatActivity() {
    private val vb by lazy { ActivityViewmkStepsBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        vb.viewmkStepsViewmksteps.setTitles(arrayOf("第一步", "第二步", "第三步", "第四步"))

        vb.viewmkStepsBtnReset.setOnClickListener {
            vb.viewmkStepsViewmksteps.reset()
        }

        vb.viewmkStepsBtnBack.setOnClickListener {
            vb.viewmkStepsViewmksteps.back()
        }

        vb.viewmkStepsBtnNext.setOnClickListener {
            vb.viewmkStepsViewmksteps.next()
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
                Log.e("sss", "右")
                vb.viewmkStepsViewmksteps.back()
            } else if (event.x - eventX < 0) {
                Log.e("sss", "左")
                vb.viewmkStepsViewmksteps.next()
            }
        }
        return true
    }
}