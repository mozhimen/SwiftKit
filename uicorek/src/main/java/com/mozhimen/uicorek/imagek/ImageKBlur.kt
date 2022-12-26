package com.mozhimen.uicorek.imagek

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import com.mozhimen.basick.utilk.bitmap.blur.UtilKBitmapBlurOption
import com.mozhimen.basick.utilk.log.UtilKSmartLog
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @ClassName ImageKBlur
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/12/24 16:37
 * @Version 1.0
 */
class ImageKBlur @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "ImageKBlur>>>>>"
    }

    @Volatile
    private var _abortBlur = false
    private var _blurOption: UtilKBitmapBlurOption? = null
    private val _blurFinish = AtomicBoolean(false)

    @Volatile
    private var _isAnimating = false
    private var _startDuration: Long = 0
    private var _cacheAction: CacheAction? = null
    private var _attachedCache: CacheAction? = null
    private var _isAttachedToWindow = false
    private var _cutoutX = 0
    private var _cutoutY = 0

    init {
        isFocusable = false
        isFocusableInTouchMode = false
        scaleType = ScaleType.MATRIX
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            background = null
        } else {
            setBackgroundDrawable(null)
        }
    }

    fun setCutoutX(cutoutX: Int): ImageKBlur {
        this._cutoutX = cutoutX
        return this
    }

    fun setCutoutY(cutoutY: Int): ImageKBlur {
        this._cutoutY = cutoutY
        return this
    }

    fun applyBlurOption(option: UtilKBitmapBlurOption) {
        applyBlurOption(option, false)
    }

    private fun applyBlurOption(option: UtilKBitmapBlurOption, isOnUpdate: Boolean) {
        _blurOption = option
        val anchorView = option.getBlurView()
        if (anchorView == null) {
            UtilKSmartLog.e(TAG, "applyBlurOption 模糊锚点View为空，放弃模糊操作...")
            destroy()
            return
        }
        //因为考虑到实时更新位置（包括模糊也要实时）的原因，因此强制更新时模糊操作在主线程完成。
        if (option.isBlurAsync() && !isOnUpdate) {
            UtilKSmartLog.i(TAG, "applyBlurOption 子线程blur")
            startBlurTask(anchorView)
        } else {
            try {
                UtilKSmartLog.i(TAG, "applyBlurOption 主线程blur")
                if (!isRenderScriptSupported()) {
                    UtilKSmartLog.e(TAG, "applyBlurOption 不支持脚本模糊。。。最低支持api 17(Android 4.2.2)，将采用fastblur")
                }
                setImageBitmapOnUiThread(
                    blur(anchorView, option.getBlurPreScaleRatio(), option.getBlurRadius(), option.isFullScreen(), _cutoutX, _cutoutY), isOnUpdate
                )
            } catch (e: Exception) {
                e.printStackTrace()
                UtilKSmartLog.e(TAG, "applyBlurOption 模糊异常", e)
                destroy()
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _abortBlur = true
    }

    fun update() {
        if (_blurOption != null) {
            applyBlurOption(_blurOption!!, true)
        }
    }

    /**
     * alpha进场动画
     */
    fun start(duration: Long) {
        _startDuration = duration
        if (!_blurFinish.get()) {
            if (_cacheAction == null) {
                _cacheAction = CacheAction({ start(_startDuration) }, 0)
                UtilKSmartLog.e(TAG, "缓存模糊动画，等待模糊完成")
            }
            return
        }
        if (_cacheAction != null) {        //干掉缓存的runnable
            _cacheAction!!.destroy()
            _cacheAction = null
        }
        if (_isAnimating) return
        UtilKSmartLog.i(TAG, "start 开始模糊alpha动画")
        _isAnimating = true
        if (duration > 0) {
            startAlphaInAnimation(duration)
        } else if (duration == -2L) {
            startAlphaInAnimation(if (_blurOption == null) 500 else _blurOption!!.getBlurInDuration())
        } else {
            imageAlpha = 255
        }
    }

    private fun startAlphaInAnimation(duration: Long) {
        val valueAnimator = ValueAnimator.ofInt(0, 255)
        valueAnimator.duration = duration
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                _isAnimating = false
            }
        })
        valueAnimator.addUpdateListener { animation -> imageAlpha = (animation.animatedValue as Int) }
        valueAnimator.start()
    }

    /**
     * alpha退场动画
     */
    fun dismiss(duration: Long) {
        _isAnimating = false
        i(TAG, "dismiss模糊imageview alpha动画")
        if (duration > 0) {
            startAlphaOutAnimation(duration)
        } else if (duration == -2L) {
            startAlphaOutAnimation(if (_blurOption == null) 500 else _blurOption!!.getBlurOutDuration())
        } else {
            imageAlpha = 0
        }
    }

    private fun startAlphaOutAnimation(duration: Long) {
        val valueAnimator = ValueAnimator.ofInt(255, 0)
        valueAnimator.duration = duration
        valueAnimator.interpolator = AccelerateInterpolator()
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                _isAnimating = false
            }
        })
        valueAnimator.addUpdateListener { animation -> imageAlpha = (animation.animatedValue as Int) }
        valueAnimator.start()
    }

    /**
     * 子线程模糊
     *
     * @param anchorView
     */
    private fun startBlurTask(anchorView: View) {
        execute(TAG, CreateBlurBitmapRunnable(anchorView))
    }


    /**
     * 判断是否处于主线程，并进行设置bitmap
     *
     * @param blurBitmap
     */
    private fun setImageBitmapOnUiThread(blurBitmap: Bitmap?, isOnUpdate: Boolean) {
        if (isUiThread()) {
            handleSetImageBitmap(blurBitmap, isOnUpdate)
        } else {
            if (!_isAttachedToWindow) {
                _attachedCache = CacheAction({ handleSetImageBitmap(blurBitmap, isOnUpdate) }, 0)
            } else {
                post { handleSetImageBitmap(blurBitmap, isOnUpdate) }
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        _isAttachedToWindow = true
        if (_attachedCache != null) {
            _attachedCache!!.forceRestore()
        }
    }

    /**
     * 设置bitmap，并进行后续处理（此方法必定运行在主线程）
     *
     * @param bitmap
     */
    private fun handleSetImageBitmap(bitmap: Bitmap?, isOnUpdate: Boolean) {
        if (bitmap != null) {
            UtilKSmartLog.i("bitmap: 【" + bitmap.width + "," + bitmap.height + "】")
        }
        imageAlpha = if (isOnUpdate) 255 else 0
        setImageBitmap(bitmap)
        val option = _blurOption
        if (option != null && !option.isFullScreen()) {
            //非全屏的话，则需要将bitmap变化到对应位置
            val anchorView = option.getBlurView() ?: return
            val rect = Rect()
            anchorView.getGlobalVisibleRect(rect)
            val matrix = imageMatrix
            matrix.setTranslate(rect.left.toFloat(), rect.top.toFloat())
            imageMatrix = matrix
        }
        _blurFinish.compareAndSet(false, true)
        UtilKSmartLog.i(TAG, "设置成功：" + _blurFinish.get())
        if (_cacheAction != null) {
            UtilKSmartLog.i(TAG, "恢复缓存动画")
            _cacheAction!!.restore()
        }
        if (_attachedCache != null) {
            _attachedCache!!.destroy()
            _attachedCache = null
        }
    }

    private fun isUiThread(): Boolean {
        return Thread.currentThread() === Looper.getMainLooper().thread
    }

    fun destroy() {
        setImageBitmap(null)
        _abortBlur = true
        if (_blurOption != null) {
            _blurOption = null
        }
        if (_cacheAction != null) {
            _cacheAction!!.destroy()
            _cacheAction = null
        }
        _blurFinish.set(false)
        _isAnimating = false
        _startDuration = 0
    }

    internal class CreateBlurBitmapRunnable(target: View) : Runnable {
        private val outWidth: Int
        private val outHeight: Int
        private val mBitmap: Bitmap?
        override fun run() {
            if (abortBlur || mBlurOption == null) {
                UtilKSmartLog.e(TAG, "放弃模糊，可能是已经移除了布局")
                return
            }
            UtilKSmartLog.i(TAG, "子线程模糊执行")
            setImageBitmapOnUiThread(
                blur(
                    mBitmap,
                    outWidth,
                    outHeight,
                    mBlurOption.getBlurRadius()
                ),
                false
            )
        }

        init {
            outWidth = target.width
            outHeight = target.height
            mBitmap = getViewBitmap(
                target, mBlurOption.getBlurPreScaleRatio(), mBlurOption
                    .isFullScreen(), cutoutX, cutoutY
            )
        }
    }

    internal class CacheAction(var action: Runnable?, var delay: Long) {
        val startTime: Long

        fun restore() {
            if (isOverTime) {
                e(TAG, "模糊超时")
                destroy()
                return
            }
            if (action != null) {
                post(action)
            }
        }

        fun forceRestore() {
            if (action != null) {
                post(action)
            }
        }

        val isOverTime: Boolean
            get() = System.currentTimeMillis() - startTime > BLUR_TASK_WAIT_TIMEOUT

        fun destroy() {
            if (action != null) {
                removeCallbacks(action)
            }
            action = null
            delay = 0
        }

        fun matches(otherAction: Runnable?): Boolean {
            return (otherAction == null && action == null
                    || action != null && action == otherAction)
        }

        companion object {
            private const val BLUR_TASK_WAIT_TIMEOUT: Long = 1000 //图片模糊超时1秒
        }

        init {
            startTime = System.currentTimeMillis()
        }
    }
}