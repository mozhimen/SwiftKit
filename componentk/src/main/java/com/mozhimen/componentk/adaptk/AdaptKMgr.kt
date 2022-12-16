package com.mozhimen.componentk.adaptk

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import com.mozhimen.basick.utilk.UtilKScreen
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
class AdaptKMgr {
    private val TAG = "AdaptKMgr>>>>>"
    private var _length: Int
    private var _width: Int

    init {
        val length = UtilKScreen.getRealScreenHeight()
        val width = UtilKScreen.getRealScreenWidth()
        _length = max(length, width)
        _width = min(length, width)
    }

    /**
     * 在application中进行初始化
     * @param length Int 设备的长
     * @param width Int 设备的宽
     */
    fun init(length: Int = _length, width: Int = _width) {
        Log.d(TAG, "init: length $_length width $_width")
        AutoSizeConfig.getInstance().onAdaptListener = object : onAdaptListener {
            override fun onAdaptBefore(target: Any, activity: Activity) {
                AutoSizeConfig.getInstance().screenWidth = length
                AutoSizeConfig.getInstance().screenHeight = width
                if (activity.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {                //根据屏幕方向，设置适配基准
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

    companion object {
        @JvmStatic
        val instance = AdaptKMgrProvider.holder
    }

    private object AdaptKMgrProvider {
        val holder = AdaptKMgr()
    }
}