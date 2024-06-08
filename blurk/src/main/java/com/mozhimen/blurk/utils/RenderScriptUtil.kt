package com.mozhimen.blurk.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RSIllegalArgumentException
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import androidx.annotation.ChecksSdkIntAtLeast
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.blurk.BlurKFast
import com.mozhimen.basick.utilk.android.graphics.UtilKBitmapDeal
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKLongLogWrapper
import com.mozhimen.basick.utilk.android.util.d
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.android.view.UtilKViewWrapper
import com.mozhimen.basick.utilk.kotlin.ranges.UtilK_Ranges

/**
 * @ClassName RenderScriptHelper
 * @Description
 * warn:renderscript即将遗弃，后期将前移到Vulkan，使用GPU更快
 * https://developer.android.com/guide/topics/renderscript/compute?hl=zh-cn#additional-code-samples
 * https://developer.android.com/guide/topics/renderscript/migrate?hl=zh-cn
 * @Author 大灯泡 / mozhimen / Kolin Zhao
 * @Version 1.0
 */
@SuppressLint("StaticFieldLeak")
object RenderScriptUtil : BaseUtilK() {
    private var _startTime: Long = 0

    @Volatile
    private var _renderScript: RenderScript? = null

    @JvmStatic
    fun get(context: Context): RenderScript? {
        if (_renderScript == null) {
            synchronized(RenderScriptUtil::class.java) {
                if (_renderScript == null) {
                    _renderScript = RenderScript.create(context)
                }
            }
        }
        return _renderScript
    }

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_18_43_J2)
    fun isRenderScriptSupported(): Boolean =
        UtilKBuildVersion.isAfterV_18_43_J2()

    @JvmStatic
    fun blur(view: View, scaledRatio: Float, radius: Float): Bitmap? =
        blur(view, scaledRatio, radius, true)

    @JvmStatic
    fun blur(view: View, scaledRatio: Float, radius: Float, fullScreen: Boolean): Bitmap? =
        blur(view, scaledRatio, radius, fullScreen, 0, 0)

    @JvmStatic
    fun blur(view: View, scaledRatio: Float, radius: Float, fullScreen: Boolean, cutoutX: Int, cutoutY: Int): Bitmap? =
        blur(UtilKViewWrapper.getBitmap_ofViewBackground(view, scaledRatio, fullScreen, cutoutX, cutoutY), view.width, view.height, radius)

    @JvmStatic
    fun blur(origin: Bitmap?, resultWidth: Int, resultHeight: Int, radius: Float): Bitmap? {
        _startTime = System.currentTimeMillis()
        return if (isRenderScriptSupported()) {
            "blur: 脚本模糊".d(TAG)
            scriptBlur(origin, resultWidth, resultHeight, radius)
        } else {
            "blur: 快速模糊".d(TAG)
            fastBlur(origin, resultWidth, resultHeight, radius)
        }
    }

    @JvmStatic
    fun scriptBlur(origin: Bitmap?, outWidth: Int, outHeight: Int, radius: Float): Bitmap? {
        if (origin == null || origin.isRecycled) return null
        val renderScript = get(_context)
        val blurInput = Allocation.createFromBitmap(renderScript, origin)
        val blurOutput = Allocation.createTyped(renderScript, blurInput.type)
        var blur: ScriptIntrinsicBlur? = null
        try {
            blur = ScriptIntrinsicBlur.create(renderScript, blurInput.element)
        } catch (e: RSIllegalArgumentException) {
            if (e.message != null && e.message!!.contains("unsupported element type")) {
                blur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
            }
        }
        if (blur == null) {
            "scriptBlur: 脚本模糊失败，转fastBlur".e(TAG)
            blurInput.destroy()
            blurOutput.destroy()
            return fastBlur(origin, outWidth, outHeight, radius)
        }
        blur.setRadius(UtilK_Ranges.constraint(radius, 0f, 20f))
        blur.setInput(blurInput)
        blur.forEach(blurOutput)
        blurOutput.copyTo(origin)

        //释放
        blurInput.destroy()
        blurOutput.destroy()
        val result = UtilKBitmapDeal.applyBitmapAnyResize(origin, outWidth, outHeight)
        origin.recycle()
        val time = System.currentTimeMillis() - _startTime
        if (UtilKLongLogWrapper.isLogEnable())
            UtilKLongLogWrapper.i("scriptBlur: 模糊用时：【" + time + "ms】")
        return result
    }

    @JvmStatic
    fun fastBlur(origin: Bitmap?, outWidth: Int, outHeight: Int, radius: Float): Bitmap? {
        var tempOrigin = origin
        if (tempOrigin == null || tempOrigin.isRecycled) return null
        tempOrigin = BlurKFast.blurBitmap(tempOrigin, UtilK_Ranges.constraint(radius, 0f, 20f).toInt(), false)
        if (tempOrigin == null || tempOrigin.isRecycled) return null
        tempOrigin = UtilKBitmapDeal.applyBitmapAnyResize(tempOrigin, outWidth, outHeight)
        val time = System.currentTimeMillis() - _startTime
        if (UtilKLongLogWrapper.isLogEnable())
            UtilKLongLogWrapper.i("fastBlur: 模糊用时：【" + time + "ms】")
        return tempOrigin
    }
}