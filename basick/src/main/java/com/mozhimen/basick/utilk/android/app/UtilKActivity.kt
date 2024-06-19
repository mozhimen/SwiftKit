package com.mozhimen.basick.utilk.android.app

import android.app.Activity
import android.content.Intent
import android.view.Display
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName UtilKActivity
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 13:44
 * @Version 1.0
 */
object UtilKActivity : IUtilK {

    @JvmStatic
    fun getCurrentFocus(activity: Activity): View? =
        activity.currentFocus

    @JvmStatic
    @RequiresApi(CVersCode.V_30_11_R)
    fun getDisplay(activity: Activity): Display =
        activity.display!!

    @JvmStatic
    fun getWindowManager(activity: Activity): WindowManager =
        activity.windowManager

    //////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isFinishing(activity: Activity): Boolean =
        activity.isFinishing

    @JvmStatic
    fun isDestroyed(activity: Activity): Boolean =
        activity.isDestroyed

    //////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyResult(activity: Activity, resultCode: Int, data: Intent?) {
        data?.let {
            activity.setResult(resultCode, it)
        } ?: run {
            activity.setResult(resultCode)
        }
    }

    /**
     * android.util.AndroidRuntimeException: requestFeature() must be called before adding content
     * setContentView顺序正常，并且也可以在继承AppCompatActivity情况下也能正常运行，
     * 导致我不正常的是Title隐藏选项：getSupportActionBar().hide();
     * //注意顺序，一定要在requestWindowFeature下面，否则报错
     */
    @JvmStatic
    fun requestWindowFeature(activity: Activity, featureId: Int) {
        activity.requestWindowFeature(featureId)
    }
}