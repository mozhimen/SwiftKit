package com.mozhimen.basick.utilk.bitmap.blur

import android.text.TextUtils
import android.view.View
import com.mozhimen.basick.utilk.view.UtilKView
import java.lang.ref.WeakReference

/**
 * @ClassName PopupBlurOption
 * @Description TODO
 * @Author 大灯泡 / mozhimen / Kolin Zhao
 * @Date 2022/11/30 21:41
 * @Version 1.0
 */
class UtilKBitmapBlurOption {
    companion object {
        private const val DEFAULT_BLUR_RADIUS = 10f
        private const val DEFAULT_PRE_SCALE_RATIO = 0.125f
        private const val DEFAULT_ANIMATION_DURATION: Long = 500
        private const val DEFAULT_BLUR_ASYNC = true //默认子线程blur
    }

    private var _weakBlurView: WeakReference<View>? = null
    private var _blurRadius = DEFAULT_BLUR_RADIUS
    private var _blurPreScaleRatio = DEFAULT_PRE_SCALE_RATIO
    private var _blurInDuration = DEFAULT_ANIMATION_DURATION
    private var _blurOutDuration = DEFAULT_ANIMATION_DURATION
    private var _blurAsync = DEFAULT_BLUR_ASYNC
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
        return if (_blurInDuration < 0) DEFAULT_ANIMATION_DURATION else _blurInDuration
    }

    fun getBlurOutDuration(): Long {
        return if (_blurOutDuration < 0) DEFAULT_ANIMATION_DURATION else _blurOutDuration
    }

    fun isBlurAsync(): Boolean {
        return _blurAsync
    }

    fun isAllowToBlur(): Boolean {
        return getBlurView() != null
    }

    fun setBlurView(blurView: View): UtilKBitmapBlurOption {
        _weakBlurView = WeakReference(blurView)
        var isDecorView = false
        if (blurView.parent != null) isDecorView = TextUtils.equals(blurView.parent.javaClass.name, "com.android.internal.policy.DecorView")
        if (!isDecorView) isDecorView = UtilKView.isDecorView(blurView)
        setFullScreen(isDecorView)
        return this
    }

    fun setBlurRadius(blurRadius: Float): UtilKBitmapBlurOption {
        var tempBlurRadius = blurRadius
        if (tempBlurRadius <= 0) {
            tempBlurRadius = 0.1f
        } else if (tempBlurRadius > 25) {
            tempBlurRadius = 25f
        }
        _blurRadius = tempBlurRadius
        return this
    }

    fun setBlurPreScaleRatio(blurPreScaleRatio: Float): UtilKBitmapBlurOption {
        _blurPreScaleRatio = blurPreScaleRatio
        return this
    }

    fun setBlurInDuration(blurInDuration: Long): UtilKBitmapBlurOption {
        _blurInDuration = blurInDuration
        return this
    }

    fun setBlurOutDuration(blurOutDuration: Long): UtilKBitmapBlurOption {
        _blurOutDuration = blurOutDuration
        return this
    }

    fun setBlurAsync(blurAsync: Boolean): UtilKBitmapBlurOption {
        _blurAsync = blurAsync
        return this
    }

    fun setFullScreen(fullScreen: Boolean): UtilKBitmapBlurOption {
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