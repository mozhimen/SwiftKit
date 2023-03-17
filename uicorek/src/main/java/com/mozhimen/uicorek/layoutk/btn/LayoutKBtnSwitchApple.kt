package com.mozhimen.uicorek.layoutk.btn

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.uicorek.R

/**
 * @ClassName SwitchApple
 * @Description SwitchApple
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/11/30 14:35
 * @Version 1.0
 */
class LayoutKBtnSwitchApple @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLayoutKFrame(context, attrs, defStyleAttr), View.OnClickListener {

    //region #variate
    private var _bgColorOn = 0x4DD865
    private var _bgColorOff = 0xFFFFFF
    private var _bgColorBorder = 0xEAEAEA
    private var _iconColorBtn = 0xFFFFFF
    private var _iconMargin = 1f.dp2px().toInt()
    private var _borderWidth = 1f.dp2px().toInt()
    private var _animTime = 500
    private var _defaultStatus = false

    private var _switchStatus = false
    private var _isAnimRunning = false

    private var _iconView: CardView
    private var _bgView: ImageView

    private var _switchAppleListener: ISwitchAppleListener? = null
    //endregion

    interface ISwitchAppleListener {
        fun switch(status: Boolean)
    }

    fun setOnSwitchListener(ISwitchAppleListener: ISwitchAppleListener) {
        _switchAppleListener = ISwitchAppleListener
    }

    //设置初始状态,也可以在xml中设置-> app:switchApple_defaultStatus = false|true
    fun setDefaultStatus(status: Boolean) {
        _defaultStatus = status
        _switchStatus = _defaultStatus
        refreshView()
    }

    fun getSwitchStatus(): Boolean = _switchStatus

    //region #private function
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKBtnSwitchApple)
        _defaultStatus =
            typedArray.getBoolean(R.styleable.LayoutKBtnSwitchApple_layoutKBtnSwitchApple_defaultStatus, _defaultStatus)
                .also { _switchStatus = it }
        _bgColorOn = typedArray.getColor(R.styleable.LayoutKBtnSwitchApple_layoutKBtnSwitchApple_bgColorOn, _bgColorOn)
        _bgColorOff = typedArray.getColor(R.styleable.LayoutKBtnSwitchApple_layoutKBtnSwitchApple_bgColorOff, _bgColorOff)
        _bgColorBorder =
            typedArray.getColor(R.styleable.LayoutKBtnSwitchApple_layoutKBtnSwitchApple_borderColor, _bgColorBorder)
        _iconColorBtn =
            typedArray.getColor(R.styleable.LayoutKBtnSwitchApple_layoutKBtnSwitchApple_btnColor, _iconColorBtn)
        _iconMargin =
            typedArray.getDimensionPixelSize(
                R.styleable.LayoutKBtnSwitchApple_layoutKBtnSwitchApple_btnMargin,
                _iconMargin
            )
        _borderWidth =
            typedArray.getDimensionPixelSize(
                R.styleable.LayoutKBtnSwitchApple_layoutKBtnSwitchApple_borderWidth,
                _borderWidth
            )
        _animTime = typedArray.getInteger(R.styleable.LayoutKBtnSwitchApple_layoutKBtnSwitchApple_animTime, _animTime)
        typedArray.recycle()

        //init view
        _bgView = ImageView(context)
        addView(_bgView)
        val layoutParams1 = _bgView.layoutParams as LayoutParams
        layoutParams1.height = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams1.width = ViewGroup.LayoutParams.MATCH_PARENT


        _iconView = CardView(context)
        addView(_iconView)
        _iconView.x = _iconMargin.toFloat()
        _iconView.y = _iconMargin.toFloat()

        //register listener
        setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        animationSwitch()
    }

    private fun animationSwitch() {
        if (_isAnimRunning) return
        _switchStatus = if (_switchStatus) {
            startAnimation(false)
            false
        } else {
            startAnimation(true)
            true
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        initView()
    }

    @SuppressLint("ResourceAsColor")
    override fun initView() {
        //iconView
        val layoutParams2 = _iconView.layoutParams as LayoutParams
        layoutParams2.height = (_bgView.measuredHeight - _iconMargin * 2)
        layoutParams2.width = (_bgView.measuredHeight - _iconMargin * 2)
        if (_switchStatus) {
            _iconView.x = _bgView.measuredWidth.toFloat() - _iconView.layoutParams.width - _iconMargin
        } else {
            _iconView.x = _iconMargin.toFloat()
        }
        _iconView.radius = _bgView.measuredHeight.toFloat() / 2 - _iconMargin
        _iconView.setCardBackgroundColor(_iconColorBtn)

        //bgView
        val drawable = GradientDrawable()
        //小球圆角角度
        drawable.cornerRadius = (_bgView.measuredHeight / 2).toFloat()
        //外框颜色
        drawable.setStroke(_borderWidth, _bgColorBorder)
        if (_switchStatus) {
            drawable.setColor(_bgColorOn)
        } else {
            drawable.setColor(_bgColorOff)
        }
        _bgView.background = drawable
    }

    fun refreshView() {
        //iconView
        if (_switchStatus) {
            _iconView.x = _bgView.measuredWidth.toFloat() - _iconView.layoutParams.width - _iconMargin
        } else {
            _iconView.x = _iconMargin.toFloat()
        }
    }

    @SuppressLint("Recycle")
    private fun startAnimation(status: Boolean) {
        //iconView
        val animation: TranslateAnimation
        var w = _bgView.measuredWidth.toFloat()
        if (w == 0f || w == -1f) {
            w = _bgView.width.toFloat()
        }
        animation = if (!_defaultStatus) {
            if (status) {
                TranslateAnimation(
                    0f,
                    w - _iconView.layoutParams.width - _iconMargin * 2,
                    0f,
                    0f
                )
            } else {
                TranslateAnimation(
                    w - _iconView.layoutParams.width - _iconMargin * 2,
                    0f,
                    0f,
                    0f
                )
            }
        } else {
            if (status) {
                TranslateAnimation(
                    -(w - _iconView.layoutParams.width - _iconMargin * 2),
                    0f,
                    0f,
                    0f
                )
            } else {
                TranslateAnimation(
                    0f,
                    -(w - _iconView.layoutParams.width - _iconMargin * 2),
                    0f,
                    0f
                )
            }
        }
        animation.duration = _animTime.toLong()
        animation.repeatMode = Animation.REVERSE
        animation.interpolator = AccelerateDecelerateInterpolator()
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                _isAnimRunning = true

                //如果想动画结束再执行回调的话,就把这段话放在onAnimationEnd就阔以了
                _switchAppleListener?.switch(status)
            }

            override fun onAnimationEnd(animation: Animation) {
                _isAnimRunning = false
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        _iconView.startAnimation(animation)

        //bgView
        val background = _bgView.background as GradientDrawable
        val animator: ValueAnimator = if (status) {
            ObjectAnimator.ofInt(
                background,
                "color",
                _bgColorOff,
                _bgColorOn
            )
        } else {
            ObjectAnimator.ofInt(
                background,
                "color",
                _bgColorOn,
                _bgColorOff
            )
        }
        animator.duration = _animTime.toLong()
        animator.setEvaluator(ArgbEvaluator()) //渐变色平滑
        animator.start()
    }
//endregion
}