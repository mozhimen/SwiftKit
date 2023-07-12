package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
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
        Log.d(TAG, "initData: getScreenWidthDp ${UtilKScreen.getWidthDp()}")
        Log.d(TAG, "initData: getScreenHeightDp ${UtilKScreen.getHeightDp()}")
        Log.d(TAG, "initData: getScreenWidth ${UtilKScreen.getRealWidth()}")
        Log.d(TAG, "initData: getScreenHeight ${UtilKScreen.getRealHeight()}")
        Log.d(TAG, "initData: getScreenWidth2 ${UtilKScreen.getCurrentWidth()}")
        Log.d(TAG, "initData: getScreenHeight2 ${UtilKScreen.getCurrentHeight()}")
    }
}