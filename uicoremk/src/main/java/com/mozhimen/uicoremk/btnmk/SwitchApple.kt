package com.mozhimen.uicoremk.btnmk

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
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.mozhimen.basicsmk.utilmk.dp2px
import com.mozhimen.uicoremk.R

/**
 * @ClassName SwitchApple
 * @Description SwitchApple
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/11/30 14:35
 * @Version 1.0
 */
class SwitchApple @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr), View.OnClickListener {

    //region #variate
    private val TAG = "SwitchApple>>>>>"

    private var bgColorOn = 0x4DD865
    private var bgColorOff = 0xFFFFFF
    private var bgColorBorder = 0xEAEAEA
    private var iconColorBtn = 0xFFFFFF
    private var iconMargin = 1f.dp2px()
    private var borderWidth = 1f.dp2px()
    private var animTime = 500
    private var defaultStatus = false

    private var switchStatus = false
    private var isAnimRunning = false

    private var iconView: CardView
    private var bgView: ImageView

    private var mSwitchAppleCallback: SwitchAppleCallback? = null
    //endregion

    interface SwitchAppleCallback {
        fun switch(status: Boolean)
    }

    fun setOnSwitchListener(switchAppleCallback: SwitchAppleCallback) {
        mSwitchAppleCallback = switchAppleCallback
    }

    //设置初始状态,也可以在xml中设置-> app:switchApple_defaultStatus = false|true
    fun setDefaultStatus(status: Boolean) {
        defaultStatus = status
        switchStatus = defaultStatus
        refreshView()
    }

    fun getSwitchStatus(): Boolean = switchStatus

    //region #private function
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchApple)
        defaultStatus =
            typedArray.getBoolean(R.styleable.SwitchApple_switchApple_defaultStatus, defaultStatus)
                .also { switchStatus = it }
        bgColorOn = typedArray.getColor(R.styleable.SwitchApple_switchApple_bgColorOn, bgColorOn)
        bgColorOff = typedArray.getColor(R.styleable.SwitchApple_switchApple_bgColorOff, bgColorOff)
        bgColorBorder =
            typedArray.getColor(R.styleable.SwitchApple_switchApple_bgColorBorder, bgColorBorder)
        iconColorBtn =
            typedArray.getColor(R.styleable.SwitchApple_switchApple_iconColorBtn, iconColorBtn)
        iconMargin =
            typedArray.getDimensionPixelSize(
                R.styleable.SwitchApple_switchApple_iconMargin,
                iconMargin
            )
        borderWidth =
            typedArray.getDimensionPixelSize(
                R.styleable.SwitchApple_switchApple_borderWidth,
                borderWidth
            )
        animTime = typedArray.getInteger(R.styleable.SwitchApple_switchApple_animTime, animTime)
        typedArray.recycle()

        //init view
        bgView = ImageView(context)
        addView(bgView)
        val layoutParams1 = bgView.layoutParams as LayoutParams
        layoutParams1.height = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams1.width = ViewGroup.LayoutParams.MATCH_PARENT


        iconView = CardView(context)
        addView(iconView)
        iconView.x = iconMargin.toFloat()
        iconView.y = iconMargin.toFloat()

        //register listener
        setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        animationSwitch()
    }

    private fun animationSwitch() {
        if (isAnimRunning) return
        switchStatus = if (switchStatus) {
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
    private fun initView() {
        //iconView
        val layoutParams2 = iconView.layoutParams as LayoutParams
        layoutParams2.height = (bgView.measuredHeight - iconMargin * 2)
        layoutParams2.width = (bgView.measuredHeight - iconMargin * 2)
        if (switchStatus) {
            iconView.x = bgView.measuredWidth.toFloat() - iconView.layoutParams.width - iconMargin
        } else {
            iconView.x = iconMargin.toFloat()
        }
        iconView.radius = bgView.measuredHeight.toFloat() / 2 - iconMargin
        iconView.setCardBackgroundColor(iconColorBtn)

        //bgView
        val drawable = GradientDrawable()
        //小球圆角角度
        drawable.cornerRadius = (bgView.measuredHeight / 2).toFloat()
        //外框颜色
        drawable.setStroke(borderWidth, bgColorBorder)
        if (switchStatus) {
            drawable.setColor(bgColorOn)
        } else {
            drawable.setColor(bgColorOff)
        }
        bgView.background = drawable
    }

    fun refreshView() {
        //iconView
        if (switchStatus) {
            iconView.x = bgView.measuredWidth.toFloat() - iconView.layoutParams.width - iconMargin
        } else {
            iconView.x = iconMargin.toFloat()
        }
    }

    @SuppressLint("Recycle")
    private fun startAnimation(status: Boolean) {
        //iconView
        val animation: TranslateAnimation
        var w = bgView.measuredWidth.toFloat()
        if (w == 0f || w == -1f) {
            w = bgView.width.toFloat()
        }
        animation = if (!defaultStatus) {
            if (status) {
                TranslateAnimation(
                    0f,
                    w - iconView.layoutParams.width - iconMargin * 2,
                    0f,
                    0f
                )
            } else {
                TranslateAnimation(
                    w - iconView.layoutParams.width - iconMargin * 2,
                    0f,
                    0f,
                    0f
                )
            }
        } else {
            if (status) {
                TranslateAnimation(
                    -(w - iconView.layoutParams.width - iconMargin * 2),
                    0f,
                    0f,
                    0f
                )
            } else {
                TranslateAnimation(
                    0f,
                    -(w - iconView.layoutParams.width - iconMargin * 2),
                    0f,
                    0f
                )
            }
        }
        animation.duration = animTime.toLong()
        animation.repeatMode = Animation.REVERSE
        animation.interpolator = AccelerateDecelerateInterpolator()
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                isAnimRunning = true

                //如果想动画结束再执行回调的话,就把这段话放在onAnimationEnd就阔以了
                mSwitchAppleCallback?.switch(status)
            }

            override fun onAnimationEnd(animation: Animation) {
                isAnimRunning = false
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        iconView.startAnimation(animation)

        //bgView
        val background = bgView.background as GradientDrawable
        val animator: ValueAnimator = if (status) {
            ObjectAnimator.ofInt(
                background,
                "color",
                bgColorOff,
                bgColorOn
            )
        } else {
            ObjectAnimator.ofInt(
                background,
                "color",
                bgColorOn,
                bgColorOff
            )
        }
        animator.duration = animTime.toLong()
        animator.setEvaluator(ArgbEvaluator()) //渐变色平滑
        animator.start()
    }
//endregion
}