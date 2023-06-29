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
import com.mozhimen.basick.animk.builder.temps.AnimatorGradientDrawableColorType
import com.mozhimen.basick.animk.builder.temps.AnimKTranslationType
import com.mozhimen.basick.elemk.commons.IValue1Listener
import com.mozhimen.basick.utilk.android.util.dp2px
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame
import com.mozhimen.uicorek.layoutk.btn.helpers.LayoutKBtnSwitchAttrsParser
import com.mozhimen.uicorek.layoutk.btn.mos.LayoutKBtnSwitchAttrs
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
typealias ILayoutKSwitchListener = IValue1Listener<Boolean>// (status: Boolean) -> Unit

interface ILayoutKBtnSwitch {
    /**
     * 设置初始状态,也可以在xml中设置-> app:switch_defaultStatus = false|true
     * @param status Boolean
     */
    fun setDefaultStatus(status: Boolean)
    fun toggleSwitchStatus(status: Boolean)
    fun toggleSwitchStatus()

    /**
     * 获取状态
     * @return Boolean
     */
    fun getSwitchStatus(): Boolean
    fun setOnSwitchListener(listener: ILayoutKSwitchListener)
}

class LayoutKBtnSwitch @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLayoutKFrame(context, attrs, defStyleAttr), View.OnClickListener, ILayoutKBtnSwitch {

    private val _mSwitch = MSwitch()
    private val _mBg = MBG()
    private val _attrs: LayoutKBtnSwitchAttrs by lazy { LayoutKBtnSwitchAttrsParser.parseAttrs(context, attrs) }
    private var _layoutKSwitchListener: ILayoutKSwitchListener? = null

    private var _defaultStatus = false
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
            drawable.cornerRadius = _mBg.height / 2//小球圆角角度
            drawable.setStroke(_attrs.borderWidth, _attrs.borderColor)//外框颜色
            return drawable.also { field = it }
        }
    private var _switchOnAnimation: Animation? = null
        get() {
            if (field != null) return field
            val animation = AnimKBuilder.asAnimation().add(AnimKTranslationType().apply {
                //fromX(0f, false).toX(_switch.leftXOn - _switch.leftXOff, false)
                if (!_defaultStatus) {
                    fromX(0f, false).toX(_mSwitch.leftXOn - _mSwitch.leftXOff, false)
                } else {
                    fromX(_mSwitch.leftXOff - _mSwitch.leftXOn, false).toX(0f, false)
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
            val animation = AnimKBuilder.asAnimation().add(AnimKTranslationType().apply {
                //fromX(_switch.leftXOn - _switch.leftXOff, false).toX(0f, false)
                if (!_defaultStatus) {
                    fromX(_mSwitch.leftXOn - _mSwitch.leftXOff, false).toX(0f, false)
                } else {
                    fromX(0f, false).toX(_mSwitch.leftXOff - _mSwitch.leftXOn, false)
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
        AnimKBuilder.asAnimator().add(AnimatorGradientDrawableColorType().setGradientDrawable(_bgView!!.background as GradientDrawable).setColors(_attrs.bgColorOff, _attrs.bgColorOn))
            .setDuration(_attrs.animTime.toLong()).build()
    }
    private val _bgOffAnimator by lazy {
        AnimKBuilder.asAnimator().add(AnimatorGradientDrawableColorType().setGradientDrawable(_bgView!!.background as GradientDrawable).setColors(_attrs.bgColorOn, _attrs.bgColorOff))
            .setDuration(_attrs.animTime.toLong()).build()
    }

    init {
        if (!isInEditMode) {
            minimumWidth = 20f.dp2px().toInt()
            minimumHeight = 10f.dp2px().toInt()
            setOnClickListener(this)

            addView(_bgView)
            addView(_switchView)

            _defaultStatus = _attrs.defaultStatus
            this.post {
                initBg()
                initSwitch()
                initView()
            }
        }
    }


    override fun setDefaultStatus(status: Boolean) {
        _defaultStatus = status
    }

    override fun toggleSwitchStatus(status: Boolean) {
        (context as LifecycleOwner).lifecycleScope.launch(Dispatchers.Main) {
            if (_switchStatus == status) return@launch
            if (_isAnimRunning) delay(_attrs.animTime.toLong())
            startAnimation(status.also { _switchStatus = status })
        }
    }

    override fun toggleSwitchStatus() {
        toggleSwitchStatus(!_switchStatus)
    }

    override fun getSwitchStatus(): Boolean = _switchStatus

    override fun setOnSwitchListener(listener: ILayoutKSwitchListener) {
        _layoutKSwitchListener = listener
    }

    private fun initBg() {
        _mBg.apply {
            width = _bgView!!.measuredWidth.toFloat()
            height = _bgView!!.measuredHeight.toFloat()
        }
    }

    private fun initSwitch() {
        _mSwitch.apply {
            this.width = _mBg.height - _attrs.btnMargin * 2
            leftXOff = _attrs.btnMargin
            leftXOn = _mBg.width - this.width - _attrs.btnMargin
        }
    }

    override fun initView() {
        val switchViewLayoutParams = _switchView!!.layoutParams as LayoutParams
        switchViewLayoutParams.width = _mSwitch.width.toInt()
        switchViewLayoutParams.height = _mSwitch.height.toInt()
        _switchView!!.layoutParams = switchViewLayoutParams

        _bgDrawable!!.setColor(if (_switchStatus) _attrs.bgColorOn else _attrs.bgColorOff)
        _bgView!!.background = _bgDrawable

        _switchView!!.x = if (_switchStatus) _mSwitch.leftXOn else _mSwitch.leftXOff
        _switchView!!.y = _mSwitch.topY
        _switchView!!.radius = _mSwitch.radius
        _switchView!!.setCardBackgroundColor(_attrs.btnColor)
    }

    override fun onClick(v: View?) {
        toggleSwitchStatus()
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
}
