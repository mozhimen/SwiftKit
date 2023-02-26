package com.mozhimen.uicorek.imagek

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.animk.builder.commons.IAnimatorUpdateListener
import com.mozhimen.basick.animk.builder.temps.AlphaAnimatorType
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.taskk.executor.TaskKExecutor
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.basick.utilk.graphics.bitmap.blur.helpers.RenderScriptHelper
import com.mozhimen.basick.utilk.graphics.bitmap.blur.mos.UtilKBitmapBluConfig
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
        private const val BLUR_TASK_WAIT_TIMEOUT: Long = 1000 //图片模糊超时1秒
    }

    @Volatile
    private var _abortBlur = false
    private var _blurOption: UtilKBitmapBluConfig? = null
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
        if (Build.VERSION.SDK_INT >= CVersionCode.V_16_41_J) {
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

    fun applyBlurOption(option: UtilKBitmapBluConfig) {
        applyBlurOption(option, false)
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
                Log.e(TAG, "start 缓存模糊动画，等待模糊完成")
            }
            return
        }
        if (_cacheAction != null) {        //干掉缓存的runnable
            _cacheAction!!.destroy()
            _cacheAction = null
        }
        if (_isAnimating) return
        Log.i(TAG, "start 开始模糊alpha动画")
        _isAnimating = true
        if (duration > 0) {
            startAlphaInAnimation(duration)
        } else if (duration == -2L) {
            startAlphaInAnimation(if (_blurOption == null) 500 else _blurOption!!.getBlurInDuration())
        } else {
            imageAlpha = 255
        }
    }

    /**
     * alpha退场动画
     */
    fun dismiss(duration: Long) {
        _isAnimating = false
        Log.i(TAG, "dismiss 模糊imageview alpha动画")
        if (duration > 0) {
            startAlphaOutAnimation(duration)
        } else if (duration == -2L) {
            startAlphaOutAnimation(if (_blurOption == null) 500 else _blurOption!!.getBlurOutDuration())
        } else {
            imageAlpha = 0
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        _isAttachedToWindow = true
        if (_attachedCache != null) {
            _attachedCache!!.forceRestore()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _abortBlur = true
    }

    private fun startAlphaInAnimation(duration: Long) {
//        val valueAnimator = ValueAnimator.ofInt(0, 255)
//        valueAnimator.duration = duration
//        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
//        valueAnimator.addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
//                _isAnimating = false
//            }
//        })
//        valueAnimator.addUpdateListener { animation -> imageAlpha = (animation.animatedValue as Int) }
//        valueAnimator.start()

        AnimKBuilder.asAnimator().add(AlphaAnimatorType().setAlpha(0f, 1f).addAnimatorUpdateListener(object : IAnimatorUpdateListener {
            override fun onChange(value: Int) {
                imageAlpha = value
            }
        }).addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                _isAnimating = false
            }
        })).setDuration(duration).setInterpolator(AccelerateDecelerateInterpolator()).build().start()
    }

    private fun startAlphaOutAnimation(duration: Long) {
//        val valueAnimator = ValueAnimator.ofInt(255, 0)
//        valueAnimator.duration = duration
//        valueAnimator.interpolator = AccelerateInterpolator()
//        valueAnimator.addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
//                _isAnimating = false
//            }
//        })
//        valueAnimator.addUpdateListener { animation -> imageAlpha = (animation.animatedValue as Int) }
//        valueAnimator.start()

        AnimKBuilder.asAnimator().add(AlphaAnimatorType().setAlpha(1f, 0f).addAnimatorUpdateListener(object : IAnimatorUpdateListener {
            override fun onChange(value: Int) {
                imageAlpha = value
            }
        }).addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                _isAnimating = false
            }
        })).setDuration(duration).setInterpolator(AccelerateInterpolator()).build().start()
    }

    /**
     * 子线程模糊
     * @param anchorView
     */
    private fun startBlurTask(anchorView: View) {
        TaskKExecutor.execute(TAG, runnable = CreateBlurBitmapRunnable(anchorView))
    }

    /**
     * 判断是否处于主线程，并进行设置bitmap
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

    /**
     * 设置bitmap，并进行后续处理（此方法必定运行在主线程）
     * @param bitmap
     */
    private fun handleSetImageBitmap(bitmap: Bitmap?, isOnUpdate: Boolean) {
        if (bitmap != null) {
            Log.i(TAG, "bitmap: 【" + bitmap.width + "," + bitmap.height + "】")
        }
        imageAlpha = if (isOnUpdate) 255 else 0
        setImageBitmap(bitmap)
        val option = _blurOption
        if (option != null && !option.isFullScreen()) {
            val anchorView = option.getBlurView() ?: return            //非全屏的话，则需要将bitmap变化到对应位置
            val rect = Rect()
            anchorView.getGlobalVisibleRect(rect)
            val matrix = imageMatrix
            matrix.setTranslate(rect.left.toFloat(), rect.top.toFloat())
            imageMatrix = matrix
        }
        _blurFinish.compareAndSet(false, true)
        Log.i(TAG, "设置成功：" + _blurFinish.get())
        if (_cacheAction != null) {
            Log.i(TAG, "恢复缓存动画")
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

    private fun applyBlurOption(option: UtilKBitmapBluConfig, isOnUpdate: Boolean) {
        _blurOption = option
        val anchorView = option.getBlurView()
        if (anchorView == null) {
            Log.e(TAG, "applyBlurOption 模糊锚点View为空，放弃模糊操作...")
            destroy()
            return
        }
        if (option.isBlurAsync() && !isOnUpdate) {        //因为考虑到实时更新位置（包括模糊也要实时）的原因，因此强制更新时模糊操作在主线程完成。
            Log.i(TAG, "applyBlurOption 子线程blur")
            startBlurTask(anchorView)
        } else {
            try {
                Log.i(TAG, "applyBlurOption 主线程blur")
                if (!RenderScriptHelper.isRenderScriptSupported()) {
                    Log.e(TAG, "applyBlurOption 不支持脚本模糊。。。最低支持api 17(Android 4.2.2)，将采用fastBlur")
                }
                setImageBitmapOnUiThread(
                    RenderScriptHelper.blur(anchorView, option.getBlurPreScaleRatio(), option.getBlurRadius(), option.isFullScreen(), _cutoutX, _cutoutY), isOnUpdate
                )
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
                Log.e(TAG, "applyBlurOption 模糊异常", e)
                destroy()
            }
        }
    }

    inner class CreateBlurBitmapRunnable(target: View) : Runnable {
        private val _outWidth: Int
        private val _outHeight: Int
        private val _bitmap: Bitmap?

        init {
            _outWidth = target.width
            _outHeight = target.height
            _bitmap = RenderScriptHelper.getViewBitmap(target, _blurOption!!.getBlurPreScaleRatio(), _blurOption!!.isFullScreen(), _cutoutX, _cutoutY)
        }

        override fun run() {
            if (_abortBlur || _blurOption == null) {
                Log.e(TAG, "run 放弃模糊，可能是已经移除了布局")
                return
            }
            Log.i(TAG, "run 子线程模糊执行")
            setImageBitmapOnUiThread(
                RenderScriptHelper.blur(_bitmap, _outWidth, _outHeight, _blurOption!!.getBlurRadius()), false
            )
        }
    }

    inner class CacheAction(
        private var _action: Runnable?, private var _delay: Long
    ) {
        private val _startTime: Long = System.currentTimeMillis()
        private val _isOverTime: Boolean
            get() = System.currentTimeMillis() - _startTime > BLUR_TASK_WAIT_TIMEOUT

        fun restore() {
            if (_isOverTime) {
                Log.e(TAG, "restore 模糊超时")
                destroy()
                return
            }
            if (_action != null) {
                post(_action)
            }
        }

        fun forceRestore() {
            if (_action != null) {
                post(_action)
            }
        }

        fun destroy() {
            if (_action != null) {
                removeCallbacks(_action)
            }
            _action = null
            _delay = 0
        }

        fun matches(otherAction: Runnable): Boolean {
            return (_action != null && _action == otherAction)
        }
    }
}