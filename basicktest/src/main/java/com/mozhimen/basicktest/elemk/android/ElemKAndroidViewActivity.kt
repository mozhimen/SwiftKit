package com.mozhimen.basicktest.elemk.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.basick.elemk.android.view.ScreenOrientationOfSensorProxy
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy

/**
 * @ClassName ElemKAndroidViewActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/2/2 22:17
 * @Version 1.0
 */
class ElemKAndroidViewActivity : AppCompatActivity() {
    @OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class)
    private val _screenOrientationOfSensorProxy: ScreenOrientationOfSensorProxy<ElemKAndroidViewActivity> by lazy { ScreenOrientationOfSensorProxy(this) }

    @OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _screenOrientationOfSensorProxy.apply {
            bindLifecycle(this@ElemKAndroidViewActivity)
            setScreenOrientationChangedListener(object : ScreenOrientationOfSensorProxy.IScreenOrientationChangedListener {
                override fun onDegreeChanged(degree: Int) {
                    //角度改变(更加灵活)
                }

                override fun onOrientationChanged(degree: Int) {
                    //角度改变(0,90,180,270)
                }
            })
        }
    }
}