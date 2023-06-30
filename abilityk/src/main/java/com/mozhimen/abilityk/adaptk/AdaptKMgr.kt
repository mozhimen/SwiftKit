package com.mozhimen.abilityk.adaptk

import android.app.Activity
import android.util.Log
import com.mozhimen.basick.elemk.cons.CConfig
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CMetaData
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener
import java.lang.Integer.max
import java.lang.Integer.min


/**
 * @ClassName AdaptKMgr
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/16 14:11
 * @Version 1.0
 */
@AManifestKRequire(CMetaData.DESIGN_WIDTH_IN_DP, CMetaData.DESIGN_WIDTH_IN_DP)
class AdaptKMgr {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    private val TAG = "AdaptKMgr>>>>>"
    private var _length: Int
    private var _width: Int

    init {
        val length = UtilKScreen.getRealHeight()
        val width = UtilKScreen.getRealWidth()
        _length = max(length, width)
        _width = min(length, width)
    }

    /**
     * 在application中进行初始化. 请先设置

     * Application

    <meta-data
    android:name="design_width_in_dp"
    android:value="1280"/>
    <meta-data
    android:name="design_height_in_dp"
    android:value="800"/>

     * @param length Int 设备的长
     * @param width Int 设备的宽
     */
    fun init(length: Int = _length, width: Int = _width) {
        Log.d(TAG, "init: length $_length width $_width")
        AutoSizeConfig.getInstance().onAdaptListener = object : onAdaptListener {
            override fun onAdaptBefore(target: Any, activity: Activity) {
                AutoSizeConfig.getInstance().screenWidth = length
                AutoSizeConfig.getInstance().screenHeight = width
                if (UtilKScreen.isOrientationLandscape()) {                //根据屏幕方向，设置适配基准
                    AutoSizeConfig.getInstance().designWidthInDp = length.also { _length = length }                //设置横屏基准
                } else {
                    AutoSizeConfig.getInstance().designWidthInDp = width.also { _width = width }        //设置竖屏基准
                }
            }

            override fun onAdaptAfter(target: Any?, activity: Activity?) {}
        }
    }

    /**
     * 获取长
     * @return Int
     */
    fun getLength(): Int = _length

    /**
     * 获取宽
     * @return Int
     */
    fun getWidth(): Int = _width

    private object INSTANCE {
        val holder = AdaptKMgr()
    }
}