package com.mozhimen.blurk.mos

import android.text.TextUtils
import android.view.View
import com.mozhimen.basick.utilk.android.view.UtilKView
import com.mozhimen.blurk.cons.CBlurCons
import java.lang.ref.WeakReference

/**
 * @ClassName PopupBlurOption
 * @Description TODO
 * @Author 大灯泡 / mozhimen / Kolin Zhao
 * @Date 2022/11/30 21:41
 * @Version 1.0
 */
class ImageKBlurConfig {
    private var _weakBlurView: WeakReference<View>? = null
    private var _blurRadius = CBlurCons.RADIUS
    private var _blurPreScaleRatio = CBlurCons.PRE_SCALE_RATIO
    private var _blurInDuration = CBlurCons.ANIMATION_DURATION
    private var _blurOutDuration = CBlurCons.ANIMATION_DURATION
    private var _blurAsync = CBlurCons.BLUR_ASYNC
    private var _isFullScreen = true

    fun getBlurView(): View? {
        return if (_weakBlurView == null) null else _weakBlurView?.get()
    }

    fun getBlurRadius(): Float {
        return _blurRadius
    }

    fun isFullScreen(): Boolean {
        return _isFullScreen
    }

    fun getBlurPreScaleRatio(): Float {
        return _blurPreScaleRatio
    }

    fun getBlurInDuration(): Long {
        return if (_blurInDuration < 0) CBlurCons.ANIMATION_DURATION else _blurInDuration
    }

    fun getBlurOutDuration(): Long {
        return if (_blurOutDuration < 0) CBlurCons.ANIMATION_DURATION else _blurOutDuration
    }

    fun isBlurAsync(): Boolean {
        return _blurAsync
    }

    fun isAllowToBlur(): Boolean {
        return getBlurView() != null
    }

    fun setBlurView(blurView: View): ImageKBlurConfig {
        _weakBlurView = WeakReference(blurView)
        var isContentOrDecorView = false
        if (blurView.parent != null) isContentOrDecorView = TextUtils.equals(blurView.parent.javaClass.name, "com.android.internal.policy.DecorView")
        if (!isContentOrDecorView) isContentOrDecorView = UtilKView.isContentOrDecorView(blurView)
        setFullScreen(isContentOrDecorView)
        return this
    }

    fun setBlurRadius(blurRadius: Float): ImageKBlurConfig {
        var tempBlurRadius = blurRadius
        if (tempBlurRadius <= 0) {
            tempBlurRadius = 0.1f
        } else if (tempBlurRadius > 25) {
            tempBlurRadius = 25f
        }
        _blurRadius = tempBlurRadius
        return this
    }

    fun setBlurPreScaleRatio(blurPreScaleRatio: Float): ImageKBlurConfig {
        _blurPreScaleRatio = blurPreScaleRatio
        return this
    }

    fun setBlurInDuration(blurInDuration: Long): ImageKBlurConfig {
        _blurInDuration = blurInDuration
        return this
    }

    fun setBlurOutDuration(blurOutDuration: Long): ImageKBlurConfig {
        _blurOutDuration = blurOutDuration
        return this
    }

    fun setBlurAsync(blurAsync: Boolean): ImageKBlurConfig {
        _blurAsync = blurAsync
        return this
    }

    fun setFullScreen(fullScreen: Boolean): ImageKBlurConfig {
        _isFullScreen = fullScreen
        return this
    }

    fun clear() {
        if (_weakBlurView != null) {
            _weakBlurView!!.clear()
        }
        _weakBlurView = null
    }
}