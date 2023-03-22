package com.mozhimen.uicorek.layoutk.btn

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.animk.builder.temps.GradientDrawableColorAnimatorType
import com.mozhimen.basick.animk.builder.temps.TranslationType
import com.mozhimen.basick.elemk.commons.IValueListener
import com.mozhimen.basick.utilk.res.UtilKRes
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.bases.BaseAttrsParser
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @ClassName LayoutKBtnSwitch
 * @Description LayoutKBtnSwitch
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/11/30 14:35
 * @Version 1.0
 */
typealias ILayoutKSwitchListener = IValueListener<Boolean>// (status: Boolean) -> Unit

class LayoutKBtnSwitch @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLayoutKFrame(context, attrs, defStyleAttr), View.OnClickListener {

    private val _switch = MSwitch()
    private val _bg = MBG()
    private val _attrs: LayoutKBtnSwitchAttrs by lazy { AttrsParser.parseAttrs(context, attrs) }
    private var _layoutKSwitchListener: ILayoutKSwitchListener? = null

    private var _defaultStatus = _attrs.defaultStatus
        set(value) {
            if (value == field) return
            _switchStatus = value
            field = value
        }
    private var _switchStatus = _defaultStatus
    private var _isAnimRunning = false

    private var _switchView: CardView? = null
        get() {
            if (field != null) return field
            val btnView = CardView(context)
            return btnView.also { field = it }
        }
    private var _bgView: ImageView? = null
        get() {
            if (field != null) return field
            val bgView = ImageView(context)
            bgView.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            return bgView.also { field = it }
        }
    private var _bgDrawable: GradientDrawable? = null
        get() {
            if (field != null) return field
            val drawable = GradientDrawable()
            drawable.cornerRadius = _bg.height / 2//小球圆角角度
            drawable.setStroke(_attrs.borderWidth, _attrs.borderColor)//外框颜色
            return drawable.also { field = it }
        }
    private var _switchOnAnimation: Animation? = null
        get() {
            if (field != null) return field
            val animation = AnimKBuilder.asAnimation().add(TranslationType().apply {
                //fromX(0f, false).toX(_switch.leftXOn - _switch.leftXOff, false)
                if (!_defaultStatus) {
                    fromX(0f, false).toX(_switch.leftXOn - _switch.leftXOff, false)
                } else {
                    fromX(_switch.leftXOff - _switch.leftXOn, false).toX(0f, false)
                }
            }).setDuration(_attrs.animTime.toLong())
                .setInterpolator(AccelerateDecelerateInterpolator()).build()
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    _isAnimRunning = true

                    //如果想动画结束再执行回调的话,就把这段话放在onAnimationEnd就阔以了
                    _layoutKSwitchListener?.invoke(_switchStatus)
                }

                override fun onAnimationEnd(animation: Animation) {
                    _isAnimRunning = false
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            return animation.also { field = it }
        }
    private var _switchOffAnimation: Animation? = null
        get() {
            if (field != null) return field
            val animation = AnimKBuilder.asAnimation().add(TranslationType().apply {
                //fromX(_switch.leftXOn - _switch.leftXOff, false).toX(0f, false)
                if (!_defaultStatus) {
                    fromX(_switch.leftXOn - _switch.leftXOff, false).toX(0f, false)
                } else {
                    fromX(0f, false).toX(_switch.leftXOff - _switch.leftXOn, false)
                }
            }).setDuration(_attrs.animTime.toLong())
                .setInterpolator(AccelerateDecelerateInterpolator()).build()
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    _isAnimRunning = true

                    //如果想动画结束再执行回调的话,就把这段话放在onAnimationEnd就阔以了
                    _layoutKSwitchListener?.invoke(_switchStatus)
                }

                override fun onAnimationEnd(animation: Animation) {
                    _isAnimRunning = false
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            return animation.also { field = it }
        }
    private val _bgOnAnimator by lazy {
        AnimKBuilder.asAnimator().add(GradientDrawableColorAnimatorType().setGradientDrawable(_bgView!!.background as GradientDrawable).setColors(_attrs.bgColorOff, _attrs.bgColorOn))
            .setDuration(_attrs.animTime.toLong()).build()
    }
    private val _bgOffAnimator by lazy {
        AnimKBuilder.asAnimator().add(GradientDrawableColorAnimatorType().setGradientDrawable(_bgView!!.background as GradientDrawable).setColors(_attrs.bgColorOn, _attrs.bgColorOff))
            .setDuration(_attrs.animTime.toLong()).build()
    }

    init {
        if (!isInEditMode) {
            minimumWidth = 20f.dp2px().toInt()
            minimumHeight = 10f.dp2px().toInt()
            setOnClickListener(this)

            addView(_bgView)
            addView(_switchView)

            this.post {
                initBg()
                initSwitch()
                initView()
            }
        }
    }

    private fun initBg() {
        _bg.apply {
            width = _bgView!!.measuredWidth.toFloat()
            height = _bgView!!.measuredHeight.toFloat()
        }
    }

    private fun initSwitch() {
        _switch.apply {
            this.width = _bg.height - _attrs.btnMargin * 2
            leftXOff = _attrs.btnMargin.toFloat()
            leftXOn = _bg.width - this.width - _attrs.btnMargin
        }
    }

    override fun initView() {
        val switchViewLayoutParams = _switchView!!.layoutParams as LayoutParams
        switchViewLayoutParams.width = _switch.width.toInt()
        switchViewLayoutParams.height = _switch.height.toInt()
        _switchView!!.layoutParams = switchViewLayoutParams

        _bgDrawable!!.setColor(if (_switchStatus) _attrs.bgColorOn else _attrs.bgColorOff)
        _bgView!!.background = _bgDrawable

        _switchView!!.x = if (_switchStatus) _switch.leftXOn else _switch.leftXOff
        _switchView!!.y = _switch.topY
        _switchView!!.radius = _switch.radius
        _switchView!!.setCardBackgroundColor(_attrs.btnColor)
    }

    override fun onClick(v: View?) {
        toggleSwitch()
    }

    fun setOnSwitchListener(listener: ILayoutKSwitchListener) {
        _layoutKSwitchListener = listener
    }

    /**
     * 设置初始状态,也可以在xml中设置-> app:switch_defaultStatus = false|true
     * @param status Boolean
     */
    fun setDefaultStatus(status: Boolean) {
        _defaultStatus = status
    }

    /**
     * 获取状态
     * @return Boolean
     */
    fun getSwitchStatus(): Boolean = _switchStatus

    fun toggleSwitch(status: Boolean) {
        (context as LifecycleOwner).lifecycleScope.launch(Dispatchers.Main) {
            if (_switchStatus == status) return@launch
            if (_isAnimRunning) delay(_attrs.animTime.toLong())
            startAnimation(status.also { _switchStatus = status })
        }
    }

    fun toggleSwitch() {
        toggleSwitch(!_switchStatus)
    }

    private fun startAnimation(status: Boolean) {
        _switchView!!.startAnimation(if (status) _switchOnAnimation else _switchOffAnimation)
        if (status) _bgOnAnimator.start() else _bgOffAnimator.start()
    }

    private class MBG {
        var width: Float = 0f
        var height: Float = 0f
    }

    private class MSwitch {
        var leftXOff: Float = 0f
        var leftXOn: Float = 0f
        var width: Float = 0f
        val topY: Float
            get() = leftXOff
        val height: Float
            get() = width
        val radius: Float
            get() = width / 2f
    }

    private data class LayoutKBtnSwitchAttrs(
        val defaultStatus: Boolean,
        val bgColorOn: Int,
        val bgColorOff: Int,
        val btnColor: Int,
        val btnMargin: Float,
        val borderColor: Int,
        val borderWidth: Int,
        val animTime: Int
    )

    private object AttrsParser : BaseAttrsParser<LayoutKBtnSwitchAttrs> {
        const val DEFAULT_STATUS = false
        val BG_COLOR_ON = UtilKRes.getColor(R.color.blue_normal)
        val BG_COLOR_OFF = UtilKRes.getColor(R.color.blue_light)
        val BTN_COLOR = UtilKRes.getColor(android.R.color.white)
        val BTN_MARGIN = 3f.dp2px()
        val BORDER_COLOR = UtilKRes.getColor(R.color.blue_light)
        val BORDER_WIDTH = 1f.dp2px().toInt()
        const val ANIM_TIME = 300

        override fun parseAttrs(context: Context, attrs: AttributeSet?): LayoutKBtnSwitchAttrs {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKBtnSwitch)
            val defaultStatus =
                typedArray.getBoolean(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_defaultStatus, DEFAULT_STATUS)
            val bgColorOn =
                typedArray.getColor(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_bgColorOn, BG_COLOR_ON)
            val bgColorOff =
                typedArray.getColor(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_bgColorOff, BG_COLOR_OFF)
            val btnColor =
                typedArray.getColor(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_btnColor, BTN_COLOR)
            val btnMargin =
                typedArray.getDimensionPixelSize(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_btnMargin, BTN_MARGIN.toInt()).toFloat()
            val borderColor =
                typedArray.getColor(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_borderColor, BORDER_COLOR)
            val borderWidth =
                typedArray.getDimensionPixelSize(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_borderWidth, BORDER_WIDTH)
            val animTime =
                typedArray.getInt(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_animTime, ANIM_TIME)
            typedArray.recycle()
            return LayoutKBtnSwitchAttrs(defaultStatus, bgColorOn, bgColorOff, btnColor, btnMargin, borderColor, borderWidth, animTime)
        }
    }
}