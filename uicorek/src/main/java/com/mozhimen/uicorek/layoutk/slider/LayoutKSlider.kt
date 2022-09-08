package com.mozhimen.uicorek.layoutk.slider

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import com.mozhimen.basick.basek.BaseKLayoutFrame

/**
 * @ClassName LayoutKSlider
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 14:14
 * @Version 1.0
 */
class LayoutKSlider @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    BaseKLayoutFrame(context, attrs, defStyleAttr, defStyleRes) {
/*
    //region # variate
    private lateinit var _gestureDetector: GestureDetectorCompat
    //endregion

    init {
        initFlag()
    }

    override fun initFlag() {
        setWillNotDraw(false)

        _gestureDetector = GestureDetectorCompat(context, object : SimpleOnGestureListener() {
            //some callbacks
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                //onClick(e)
                return super.onSingleTapConfirmed(e)
            }

            override fun onContextClick(e: MotionEvent): Boolean {
                return super.onContextClick(e)
            }
        })

        this.settings = com.github.florent37.androidslidr.Slidr.Settings(this)
        this.settings.init(context, attrs)
    }*/
}