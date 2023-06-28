package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basicktest.databinding.ActivityUtilkScreenBinding
import com.mozhimen.basick.sensek.systembar.annors.ASenseKSystemBar
import com.mozhimen.basick.sensek.systembar.cons.CSystemBarType


/**
 * @ClassName UtilKScreenActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/24 14:35
 * @Version 1.0
 */
@ASenseKSystemBar(systemBarType = CSystemBarType.IMMERSED_LIGHT)
class UtilKScreenActivity : BaseActivityVB<ActivityUtilkScreenBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        Log.d(TAG, "initData: getScreenWidthDp ${UtilKScreen.getScreenWidthDp()}")
        Log.d(TAG, "initData: getScreenHeightDp ${UtilKScreen.getScreenHeightDp()}")
        Log.d(TAG, "initData: getScreenWidth ${UtilKScreen.getRealScreenWidth()}")
        Log.d(TAG, "initData: getScreenHeight ${UtilKScreen.getRealScreenHeight()}")
        Log.d(TAG, "initData: getScreenWidth2 ${UtilKScreen.getCurrentScreenWidth()}")
        Log.d(TAG, "initData: getScreenHeight2 ${UtilKScreen.getCurrentScreenHeight()}")
    }
}