package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basicktest.databinding.ActivityUtilkScreenBinding
import com.mozhimen.uicorek.adaptk.systembar.annors.AAdaptKSystemBarProperty
import com.mozhimen.uicorek.adaptk.systembar.cons.CProperty


/**
 * @ClassName UtilKScreenActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/24 14:35
 * @Version 1.0
 */
@AAdaptKSystemBarProperty(CProperty.IMMERSED_HARD_STICKY)
class UtilKScreenActivity : BaseActivityVB<ActivityUtilkScreenBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        Log.d(TAG, "initData: getWidthDp ${UtilKScreen.getWidthDp()}")
        Log.d(TAG, "initData: getHeightDp ${UtilKScreen.getHeightDp()}")
        Log.d(TAG, "initData: getWidthOfDisplay ${UtilKScreen.getWidthOfDisplay()}")
        Log.d(TAG, "initData: getHeightOfDisplay ${UtilKScreen.getHeightOfDisplay()}")
        Log.d(TAG, "initData: getWidthOfDefaultDisplay ${UtilKScreen.getWidthOfDefaultDisplay()}")
        Log.d(TAG, "initData: getHeightOfDefaultDisplay ${UtilKScreen.getHeightOfDefaultDisplay()}")
        Log.d(TAG, "initData: getWidthOfWindow ${UtilKScreen.getWidthOfWindow()}")
        Log.d(TAG, "initData: getHeightOfWindow ${UtilKScreen.getHeightOfWindow()}")
        Log.d(TAG, "initData: isOrientationPortraitOfConfiguration ${UtilKScreen.isOrientationPortraitOfConfiguration()}")
        Log.d(TAG, "initData: isOrientationPortraitOfDisplay ${UtilKScreen.isOrientationPortraitOfDisplay(this)}")
    }
}