package com.mozhimen.basick.elemk.android.view

import android.app.Activity
import android.util.Log
import android.view.OrientationEventListener
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.android.hardware.cons.CSensorManager
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.utilk.androidx.lifecycle.runOnMainThread


/**
 * @ClassName ScreenOrientationOfSensorProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/30 16:54
 * @Version 1.0
 */
@OptInApiInit_ByLazy
@OptInApiCall_BindLifecycle
class ScreenOrientationOfSensorProxy<A>(private val _activity: A) : BaseWakeBefDestroyLifecycleObserver() where A : Activity, A : LifecycleOwner {
    companion object {
        private val SENSOR_ANGLE = 10
    }

    interface IScreenOrientationChangedListener {
        fun onOrientationChanged(degree: Int){}
        fun onDegreeChanged(degree: Int){}
    }
    ////////////////////////////////////////////////////////////////////////

    private var _orientationEventListener: OrientationEventListener? = null
    private var _screenOrientationChangedListener: IScreenOrientationChangedListener? = null

    ////////////////////////////////////////////////////////////////////////

    init {
        _activity.runOnMainThread(::startObserveScreen)
    }
    ////////////////////////////////////////////////////////////////////////

    fun setScreenOrientationChangedListener(listener: IScreenOrientationChangedListener) {
        _screenOrientationChangedListener = listener
    }

    ////////////////////////////////////////////////////////////////////////

    override fun onDestroy(owner: LifecycleOwner) {
        _screenOrientationChangedListener = null
        disableOrientationListener()
        super.onDestroy(owner)
    }

    ////////////////////////////////////////////////////////////////////////

    private fun startObserveScreen() {
        disableOrientationListener()
        Log.d(TAG, "startObserveScreen: ")
        _orientationEventListener = object : OrientationEventListener(_activity, CSensorManager.SENSOR_DELAY_NORMAL) {
            override fun onOrientationChanged(orientation: Int) {
//                    Log.v(TAG, "startObserveScreen: onOrientationChanged " + orientation);
                if (orientation == ORIENTATION_UNKNOWN) return  //手机平放时，检测不到有效的角度
                _screenOrientationChangedListener?.onDegreeChanged(orientation)

                if (orientation > 360 - SENSOR_ANGLE || orientation < SENSOR_ANGLE) {//下面是手机旋转准确角度与四个方向角度（0 90 180 270）的转换
                    _screenOrientationChangedListener?.onOrientationChanged(0)
                } else if (orientation > 90 - SENSOR_ANGLE && orientation < 90 + SENSOR_ANGLE) {
                    _screenOrientationChangedListener?.onOrientationChanged(90)
                } else if (orientation > 180 - SENSOR_ANGLE && orientation < 180 + SENSOR_ANGLE) {
                    _screenOrientationChangedListener?.onOrientationChanged(180)
                } else if (orientation > 270 - SENSOR_ANGLE && orientation < 270 + SENSOR_ANGLE) {
                    _screenOrientationChangedListener?.onOrientationChanged(270)
                }
            }
        }
        if (_orientationEventListener!!.canDetectOrientation()) {
            Log.v(TAG, "startObserveScreen: Can detect orientation")
            _orientationEventListener!!.enable()
        } else {
            Log.v(TAG, "startObserveScreen: Cannot detect orientation")
            disableOrientationListener()
        }
    }

    private fun disableOrientationListener() {
        Log.d(TAG, "disableOrientationListener: ")
        if (_orientationEventListener != null) {
            _orientationEventListener!!.disable()
            _orientationEventListener = null
        }
    }
}