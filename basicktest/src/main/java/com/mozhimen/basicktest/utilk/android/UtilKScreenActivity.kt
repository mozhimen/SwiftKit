package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basicktest.databinding.ActivityUtilkScreenBinding
//import com.mozhimen.adaptk.systembar.annors.AAdaptKSystemBarProperty
//import com.mozhimen.adaptk.systembar.cons.CProperty


/**
 * @ClassName UtilKScreenActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/24 14:35
 * @Version 1.0
 */
//@AAdaptKSystemBarProperty(CProperty.IMMERSED_HARD_STICKY)
class UtilKScreenActivity : BaseActivityVDB<ActivityUtilkScreenBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        UtilKLogWrapper.dt(TAG, "initData: 获取屏幕宽度")
        UtilKLogWrapper.dt(TAG, "initData: getWidth ${UtilKScreen.getWidth()}")
        UtilKLogWrapper.dt(TAG, "initData: getWidth_ofSysMetrics ${UtilKScreen.getWidth_ofSysMetrics()}")
        UtilKLogWrapper.dt(TAG, "initData: getWidth_ofDefMetrics ${UtilKScreen.getWidth_ofDefMetrics()}")
        UtilKLogWrapper.dt(TAG, "initData: getWidth_ofRealMetrics ${UtilKScreen.getWidth_ofRealMetrics()}")
        UtilKLogWrapper.dt(TAG, "initData: getWidth_ofDefDisplay ${UtilKScreen.getWidth_ofDefDisplay()}")
        UtilKLogWrapper.dt(TAG, "initData: getWidth_ofSizeDefDisplay ${UtilKScreen.getWidth_ofSizeDefDisplay()}")
        UtilKLogWrapper.dt(TAG, "initData: getWidth_ofRealSizeDefDisplay ${UtilKScreen.getWidth_ofRealSizeDefDisplay()}")

        UtilKLogWrapper.dt(TAG, "initData: 获取屏幕高度")
        UtilKLogWrapper.dt(TAG, "initData: getHeight ${UtilKScreen.getHeight()}")
        UtilKLogWrapper.dt(TAG, "initData: getHeight_ofSysMetrics ${UtilKScreen.getHeight_ofSysMetrics()}")
        UtilKLogWrapper.dt(TAG, "initData: getHeight_ofDefMetrics ${UtilKScreen.getHeight_ofDefMetrics()}")
        UtilKLogWrapper.dt(TAG, "initData: getHeight_ofRealMetrics ${UtilKScreen.getHeight_ofRealMetrics()}")
        UtilKLogWrapper.dt(TAG, "initData: getHeight_ofDefDisplay ${UtilKScreen.getHeight_ofDefDisplay()}")
        UtilKLogWrapper.dt(TAG, "initData: getHeight_ofSizeDefDisplay ${UtilKScreen.getHeight_ofSizeDefDisplay()}")
        UtilKLogWrapper.dt(TAG, "initData: getHeight_ofRealSizeDefDisplay ${UtilKScreen.getHeight_ofRealSizeDefDisplay()}")

        UtilKLogWrapper.dt(TAG, "initData: 获取屏幕方向")
        UtilKLogWrapper.dt(TAG, "initData: isOrientationPortrait_ofSysConfig ${UtilKScreen.isOrientationPortrait_ofSysConfig()}")
        UtilKLogWrapper.dt(TAG, "initData: isOrientationPortrait_ofDefDisplay ${UtilKScreen.isOrientationPortrait_ofDefDisplay(this)}")
        UtilKLogWrapper.dt(TAG, "initData: isOrientationPortrait_ofSysMetrics ${UtilKScreen.isOrientationPortrait_ofSysMetrics()}")
    }
}