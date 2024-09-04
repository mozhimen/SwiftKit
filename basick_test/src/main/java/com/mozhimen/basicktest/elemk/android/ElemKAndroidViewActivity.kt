package com.mozhimen.basicktest.elemk.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.basick.helpers.ScreenOrientationOfSensorProxy
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM.lazy_ofNone

/**
 * @ClassName ElemKAndroidViewActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/2/2 22:17
 * @Version 1.0
 */
class ElemKAndroidViewActivity : AppCompatActivity() {
    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    private val _screenOrientationOfSensorProxy: ScreenOrientationOfSensorProxy<ElemKAndroidViewActivity> by lazy_ofNone { ScreenOrientationOfSensorProxy(this) }

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
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