package com.mozhimen.uicorek.textk.bubble

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.uicorek.layoutk.commons.ILayoutK
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.view.UtilKView
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.drawablek.arrow.DrawableKArrow
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowDirection
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowPosPolicy
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubbleListener
import java.lang.ref.WeakReference
import kotlin.math.abs

/**
 * @ClassName TextKBubbleProxy
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/5 13:19
 * @Version 1.0
 */
class TextKBubbleProxy(private val _context: Context) : ITextKBubble, ILayoutK {
    //region # variate
    private val TAG = "TextKBubbleProxy>>>>>"
    private lateinit var _textKBubble: TextKBubble
    private lateinit var _textKBubbleListener: ITextKBubbleListener

    private var _arrowToViewRef: WeakReference<View>? = null
    private var _drawableArrowDirection: EArrowDirection = EArrowDirection.None
    private val _onLayoutChangeListener = View.OnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        requestUpdateBubble()
    }
    private val _location = IntArray(2)    // 方便计算用的中间值对象，避免重复创建
    private val _rectTo = Rect()
    private val _rectSelf = Rect()
    private val _bubbleDrawable = DrawableKArrow()
    private var _paddingLeftOffset = 0
    private var _paddingTopOffset = 0
    private var _paddingRightOffset = 0
    private var _paddingBottomOffset = 0

    private var _arrowDirection: EArrowDirection = EArrowDirection.Auto
    private var _arrowHeight: Float = ARROW_HEIGHT
    private var _arrowWidth: Float = ARROW_WIDTH
    private var _arrowPosPolicy: EArrowPosPolicy = EArrowPosPolicy.TargetCenter
    private var _arrowPosOffset: Float = ARROW_POS_OFFSET
    private var _arrowToByViewId: Int = 0
    private var _bgColor: Int = BG_COLOR
    private var _borderColor: Int = BORDER_COLOR
    private var _borderWidth: Float = BORDER_WIDTH
    private var _gapPadding: Float = GAP_PADDING
    private var _cornerTopLeftRadius: Float = CORNER_RADIUS
    private var _cornerTopRightRadius: Float = CORNER_RADIUS
    private var _cornerBottomLeftRadius: Float = CORNER_RADIUS
    private var _cornerBottomRightRadius: Float = CORNER_RADIUS
    private var _paddingLeft: Float = PADDING
    private var _paddingTop: Float = PADDING
    private var _paddingRight: Float = PADDING
    private var _paddingBottom: Float = PADDING

    companion object {
        private val ARROW_HEIGHT = 4f.dp2px().toFloat()
        private val ARROW_WIDTH = 6f.dp2px().toFloat()
        private val ARROW_POS_OFFSET = 10f.dp2px().toFloat()
        private const val BG_COLOR = Color.WHITE
        private const val BORDER_COLOR = Color.GRAY
        private val BORDER_WIDTH = 1f.dp2px().toFloat()
        private val GAP_PADDING = 0f.dp2px().toFloat()
        private val CORNER_RADIUS = 4f.dp2px().toFloat()
        private val PADDING = 2f.dp2px().toFloat()
    }
    //endregion

    fun init(textKBubble: TextKBubble, attrs: AttributeSet?) {
        _textKBubble = textKBubble
        _textKBubbleListener = textKBubble
        initAttrs(attrs, 0)
        updateDrawable(_textKBubble.width, _textKBubble.height, false)
    }

    override fun initFlag() {}

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        val typedArray = _context.obtainStyledAttributes(attrs, R.styleable.TextKBubble)
        _arrowDirection =
            EArrowDirection.get(
                typedArray.getInteger(R.styleable.TextKBubble_textKBubble_arrowDirection, EArrowDirection.Auto.value)
            )
        _arrowHeight =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_arrowHeight, ARROW_HEIGHT)
        _arrowWidth =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_arrowWidth, ARROW_WIDTH)
        _arrowPosPolicy =
            EArrowPosPolicy.get(
                typedArray.getInteger(R.styleable.TextKBubble_textKBubble_arrowPosPolicy, EArrowPosPolicy.TargetCenter.value)
            )
        _arrowPosOffset =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_arrowPosOffset, ARROW_POS_OFFSET)
        _arrowToByViewId =
            typedArray.getResourceId(R.styleable.TextKBubble_textKBubble_arrowToViewId, 0)

        _bgColor =
            typedArray.getColor(R.styleable.TextKBubble_textKBubble_bgColor, BG_COLOR)
        _borderColor =
            typedArray.getColor(R.styleable.TextKBubble_textKBubble_borderColor, BORDER_COLOR)
        _borderWidth =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_borderWidth, BORDER_WIDTH)
        _gapPadding =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_gapPadding, GAP_PADDING)

        val cornerRadius =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_cornerRadius, CORNER_RADIUS)
        _cornerTopLeftRadius = cornerRadius.also { _cornerBottomRightRadius = it }
            .also { _cornerBottomLeftRadius = it }.also { _cornerTopRightRadius = it }
        _cornerTopLeftRadius =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_cornerTopLeftRadius, _cornerTopLeftRadius)
        _cornerTopRightRadius =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_cornerTopRightRadius, _cornerTopRightRadius)
        _cornerBottomLeftRadius =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_cornerBottomLeftRadius, _cornerBottomLeftRadius)
        _cornerBottomRightRadius =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_cornerBottomRightRadius, _cornerBottomRightRadius)

        val padding =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_padding, PADDING)
        _paddingLeft = padding.also { _paddingTop = it }
            .also { _paddingRight = it }.also { _paddingBottom = it }
        _paddingLeft =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_paddingHorizontal, _paddingLeft).also { _paddingRight = it }
        _paddingTop =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_paddingVertical, _paddingTop).also { _paddingBottom = it }
        typedArray.recycle()
    }

    override fun initView() {}

    fun updateDrawable(width: Int, height: Int, drawImmediately: Boolean) {
        var arrowToOffsetX = 0f
        var arrowToOffsetY = 0f

        var arrowToView = getArrowTo()

        if (arrowToView == null && _arrowToByViewId != 0) {
            arrowToView = UtilKView.findViewFromParentById(_arrowToByViewId, _textKBubble)
            setArrowToViewRef(arrowToView)
        }

        _drawableArrowDirection = _arrowDirection
        arrowToView?.let {
            arrowToView.getLocationOnScreen(_location)
            _rectTo.set(
                _location[0],
                _location[1],
                _location[0] + arrowToView.width,
                _location[1] + arrowToView.height
            )
            _textKBubble.getLocationOnScreen(_location)

            _rectSelf.set(
                _location[0],
                _location[1],
                _location[0] + width,
                _location[1] + height
            )

            if (_drawableArrowDirection == EArrowDirection.Auto) {
                _drawableArrowDirection = getAutoArrowDirection(_rectSelf, _rectTo)
            }
            arrowToOffsetX = _rectTo.centerX().toFloat() - _rectSelf.centerX().toFloat()
            arrowToOffsetY = _rectTo.centerY().toFloat() - _rectSelf.centerY().toFloat()
        }

        setPadding(
            _textKBubble.paddingLeft.toFloat(),
            _textKBubble.paddingTop.toFloat(),
            _textKBubble.paddingRight.toFloat(),
            _textKBubble.paddingBottom.toFloat()
        )

        if (drawImmediately) {
            _bubbleDrawable.resetRect(width, height)
            _bubbleDrawable.setCornerRadius(_cornerTopLeftRadius, _cornerTopRightRadius, _cornerBottomRightRadius, _cornerBottomLeftRadius)
            _bubbleDrawable.setFillColor(_bgColor)
            _bubbleDrawable.setBorderWidth(_borderWidth)
            _bubbleDrawable.setGapWidth(_gapPadding)
            _bubbleDrawable.setBorderColor(_borderColor)
            _bubbleDrawable.setArrowDirection(_drawableArrowDirection)
            _bubbleDrawable.setArrowPosPolicy(_arrowPosPolicy)
            _bubbleDrawable.setArrowToPoint(arrowToOffsetX, arrowToOffsetY)
            _bubbleDrawable.setArrowPosDelta(_arrowPosOffset)
            _bubbleDrawable.setArrowHeight(_arrowHeight)
            _bubbleDrawable.setArrowWidth(_arrowWidth)
            _bubbleDrawable.updateShapes()
            if (Build.VERSION.SDK_INT >= CVersionCode.V_16_41_J) {
                _textKBubble.background = _bubbleDrawable
            } else {
                _textKBubble.setBackgroundDrawable(_bubbleDrawable) // noinspection deprecation
            }
        }
    }

    override fun setArrowDirection(arrowDirection: EArrowDirection) {
        _arrowDirection = arrowDirection
    }

    override fun getArrowDirection(): EArrowDirection =
        _arrowDirection

    override fun setArrowHeight(arrowHeight: Float) {
        _arrowHeight = arrowHeight
    }

    override fun getArrowHeight(): Float =
        _arrowHeight

    override fun setArrowWidth(arrowWidth: Float) {
        _arrowWidth = arrowWidth
    }

    override fun getArrowWidth(): Float =
        _arrowWidth

    override fun setArrowPosPolicy(arrowPosPolicy: EArrowPosPolicy) {
        _arrowPosPolicy = arrowPosPolicy
    }

    override fun getArrowPosPolicy(): EArrowPosPolicy =
        _arrowPosPolicy

    override fun setArrowPosOffset(offset: Float) {
        _arrowPosOffset = offset
    }

    override fun getArrowPosOffset(): Float =
        _arrowPosOffset

    override fun setArrowToByViewId(targetViewId: Int) {
        _arrowToByViewId = targetViewId
    }

    override fun getArrowToByViewId(): Int =
        _arrowToByViewId

    override fun setArrowTo(targetView: View) {
        _arrowToByViewId = targetView.id
        setArrowToViewRef(targetView)
    }

    override fun getArrowTo(): View? =
        _arrowToViewRef?.get()

    override fun setBgColor(bgColor: Int) {
        _bgColor = bgColor
    }

    override fun getBgColor(): Int =
        _bgColor

    override fun setBorderColor(borderColor: Int) {
        _borderColor = borderColor
    }

    override fun getBorderColor(): Int =
        _borderColor

    override fun setBorderWidth(borderWidth: Float) {
        _borderWidth = borderWidth
    }

    override fun getBorderWidth(): Float =
        _borderWidth

    override fun setGapPadding(gapPadding: Float) {
        _gapPadding = gapPadding
    }

    override fun getGapPadding(): Float =
        _gapPadding

    override fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        _cornerTopLeftRadius = topLeft
        _cornerTopRightRadius = topRight
        _cornerBottomLeftRadius = bottomLeft
        _cornerBottomRightRadius = bottomRight
    }

    override fun setCornerRadius(radius: Float) {
        setCornerRadius(radius, radius, radius, radius)
    }

    override fun getCornerTopLeftRadius(): Float =
        _cornerTopLeftRadius

    override fun getCornerTopRightRadius(): Float =
        _cornerTopRightRadius

    override fun getCornerBottomLeftRadius(): Float =
        _cornerBottomLeftRadius

    override fun getCornerBottomRightRadius(): Float =
        _cornerBottomRightRadius

    override fun setPadding(padding: Float) {
        setPadding(padding, padding)
    }

    override fun setPadding(paddingHorizontal: Float, paddingVertical: Float) {
        setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
    }

    override fun setPadding(left: Float, top: Float, right: Float, bottom: Float) {
        Log.d(TAG, "setPadding: _bubbleListener is not null")
        if (Build.VERSION.SDK_INT <= CVersionCode.V_16_41_J) {
            val stack = Throwable().stackTrace
            for (i in 0..6) {
                if (stack[i].className == View::class.java.name && (stack[i].methodName == "recomputePadding")
                ) {
                    Log.w(TAG, "Called setPadding by View on old Android platform")
                    _textKBubbleListener.setSuperPadding(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
                    return
                }
            }
        }

        _paddingLeftOffset = 0.also { _paddingBottomOffset = it }.also { _paddingRightOffset = it }.also { _paddingTopOffset = it }
        when (_drawableArrowDirection) {
            EArrowDirection.Left -> _paddingLeftOffset += _arrowHeight.toInt()
            EArrowDirection.Up -> _paddingTopOffset += _arrowHeight.toInt()
            EArrowDirection.Right -> _paddingRightOffset += _arrowHeight.toInt()
            EArrowDirection.Down -> _paddingBottomOffset += _arrowHeight.toInt()
            EArrowDirection.Auto, EArrowDirection.None -> {}
        }

        val superPaddingLeft: Int = left.toInt() + _paddingLeftOffset
        val superPaddingTop: Int = top.toInt() + _paddingTopOffset
        val superPaddingRight: Int = right.toInt() + _paddingRightOffset
        val superPaddingBottom: Int = bottom.toInt() + _paddingBottomOffset

        if (superPaddingLeft != _textKBubbleListener.getSuperPaddingLeft() || superPaddingTop != _textKBubbleListener.getSuperPaddingTop() || superPaddingRight != _textKBubbleListener.getSuperPaddingRight() || superPaddingBottom != _textKBubbleListener.getSuperPaddingBottom()) {
            _textKBubble.post {
                _textKBubbleListener.setSuperPadding(
                    superPaddingLeft, superPaddingTop, superPaddingRight, superPaddingBottom
                )
            }
        }
    }

    override fun getPaddingLeft(): Int =
        _textKBubbleListener.getSuperPaddingLeft() - _paddingLeftOffset

    override fun getPaddingTop(): Int =
        _textKBubbleListener.getSuperPaddingTop() - _paddingTopOffset

    override fun getPaddingRight(): Int =
        _textKBubbleListener.getSuperPaddingRight() - _paddingRightOffset

    override fun getPaddingBottom(): Int =
        _textKBubbleListener.getSuperPaddingBottom() - _paddingBottomOffset

    override fun requestUpdateBubble() {
        updateDrawable(_textKBubble.width, _textKBubble.height, true)
    }

    private fun setArrowToViewRef(targetView: View?) {
        _arrowToViewRef?.let {
            val oldTargetView: View? = it.get()
            oldTargetView?.removeOnLayoutChangeListener(_onLayoutChangeListener)
        }

        _arrowToViewRef = if (targetView != null) WeakReference(targetView) else null
        targetView?.addOnLayoutChangeListener(_onLayoutChangeListener)
    }

    /**
     * 根据目标对象相对中心位置，推导箭头朝向
     * @param bubble Rect 气泡的区域
     * @param target Rect 目标区域
     * @return ArrowDirection 推导出的箭头朝向
     */
    private fun getAutoArrowDirection(bubble: Rect, target: Rect): EArrowDirection {
        if (!bubble.intersects(target.left, target.top, target.right, target.bottom)) {
            val offset = Point(bubble.centerX() - target.centerX(), bubble.centerY() - target.centerY())
            if (abs(offset.x) < bubble.width() / 2 + target.width() / 2) {
                if (offset.y < 0) {
                    return EArrowDirection.Down
                } else if (offset.y > 0) {
                    return EArrowDirection.Up
                }
            } else if (abs(offset.y) < bubble.height() / 2 + target.height() / 2) {
                if (offset.x < 0) {
                    return EArrowDirection.Right
                } else if (offset.x > 0) {
                    return EArrowDirection.Left
                }
            }
        }
        return EArrowDirection.None
    }
}