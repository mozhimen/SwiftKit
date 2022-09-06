package com.mozhimen.uicorek.textk.bubble

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.utilk.UtilKView
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble.ArrowPosPolicy
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble.ArrowDirection
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
class TextKBubbleProxy : ITextKBubble {
    //region # variate
    private val TAG = "TextKBubbleProxy>>>>>"
    private lateinit var _parentView: View
    private var _bubbleListener: ITextKBubbleListener? = null

    private var _arrowToViewRef: WeakReference<View>? = null
    private var _drawableArrowDirection: ArrowDirection = ArrowDirection.None
    private val _onLayoutChangeListener = View.OnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> requestUpdateBubble() }
    private val _location = IntArray(2)    // 方便计算用的中间值对象，避免重复创建
    private val _rectTo = Rect()
    private val _rectSelf = Rect()
    private val _bubbleDrawable = TextKBubbleDrawable()
    private var _paddingLeftOffset = 0
    private var _paddingTopOffset = 0
    private var _paddingRightOffset = 0
    private var _paddingBottomOffset = 0

    override var arrowDirection: ArrowDirection = ArrowDirection.Auto
    override var arrowHeight: Float = ARROW_HEIGHT
    override var arrowWidth: Float = ARROW_WIDTH
    override var arrowPosPolicy: ArrowPosPolicy = ArrowPosPolicy.TargetCenter
    override var arrowPosOffset: Float = ARROW_POS_OFFSET
    override var arrowToViewId: Int = 0
        set(value) {
            field = value
            setArrowToViewRef(null)
        }
    override var arrowToView: View?
        get() = _arrowToViewRef?.get()
        set(value) {
            arrowToViewId = value?.id ?: 0
            setArrowToViewRef(value)
        }
    override var bgColor: Int = BG_COLOR
    override var borderColor: Int = BORDER_COLOR
    override var borderWidth: Float = BORDER_WIDTH
    override var gapPadding: Float = GAP_PADDING
    override var cornerTopLeftRadius: Float = CORNER_RADIUS
    override var cornerTopRightRadius: Float = CORNER_RADIUS
    override var cornerBottomLeftRadius: Float = CORNER_RADIUS
    override var cornerBottomRightRadius: Float = CORNER_RADIUS
    override var paddingLeft: Float = PADDING
        get() {
            return _bubbleListener?.let {
                (it.getSuperPaddingLeft() - _paddingLeftOffset).toFloat()
            } ?: field
        }
    override var paddingTop: Float = PADDING
        get() {
            return _bubbleListener?.let {
                (it.getSuperPaddingTop() - _paddingTopOffset).toFloat()
            } ?: field
        }
    override var paddingRight: Float = PADDING
        get() {
            return _bubbleListener?.let {
                (it.getSuperPaddingRight() - _paddingRightOffset).toFloat()
            } ?: field
        }
    override var paddingBottom: Float = PADDING
        get() {
            return _bubbleListener?.let {
                (it.getSuperPaddingBottom() - _paddingBottomOffset).toFloat()
            } ?: field
        }

    companion object {
        private val ARROW_HEIGHT = 6f.dp2px().toFloat()
        private val ARROW_WIDTH = 10f.dp2px().toFloat()
        private val ARROW_POS_OFFSET = 10f.dp2px().toFloat()
        private const val BG_COLOR = Color.WHITE
        private const val BORDER_COLOR = Color.GRAY
        private val BORDER_WIDTH = 1f.dp2px().toFloat()
        private val GAP_PADDING = 0f.dp2px().toFloat()
        private val CORNER_RADIUS = 4f.dp2px().toFloat()
        private val PADDING = 0f.dp2px().toFloat()
    }
    //endregion

    fun init(view: View, context: Context, attrs: AttributeSet?) {
        try {
            _parentView = view
            _bubbleListener = view as ITextKBubbleListener
            initAttr(context, attrs)
            updateDrawable(_parentView.width, _parentView.height, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateDrawable(width: Int, height: Int, drawImmediately: Boolean) {
        var arrowToOffsetX = 0f
        var arrowToOffsetY = 0f

        if (arrowToView == null && arrowToViewId != 0) {
            arrowToView = UtilKView.findViewFromParentById(arrowToViewId, _parentView)
            setArrowToViewRef(arrowToView)
        }

        _drawableArrowDirection = arrowDirection
        arrowToView?.let {
            arrowToView!!.getLocationOnScreen(_location)
            _rectTo.set(_location[0], _location[1], _location[0] + arrowToView!!.width, _location[1] + arrowToView!!.height)
            _parentView.getLocationOnScreen(_location)

            _rectSelf.set(_location[0], _location[1], _location[0] + width, _location[1] + height)
            if (_drawableArrowDirection === ArrowDirection.Auto) {
                _drawableArrowDirection = getAutoArrowDirection(_rectSelf, _rectTo)
            }
            arrowToOffsetX = _rectTo.centerX().toFloat() - _rectSelf.centerX().toFloat()
            arrowToOffsetY = _rectTo.centerY().toFloat() - _rectSelf.centerY().toFloat()
        }

        setPadding(
            _parentView.paddingLeft.toFloat(),
            _parentView.paddingTop.toFloat(),
            _parentView.paddingRight.toFloat(),
            _parentView.paddingBottom.toFloat()
        )

        if (drawImmediately) {
            _bubbleDrawable.resetRect(width, height)
            _bubbleDrawable.setCornerRadius(cornerTopLeftRadius, cornerTopRightRadius, cornerBottomRightRadius, cornerBottomLeftRadius)
            _bubbleDrawable.setBgColor(bgColor)
            _bubbleDrawable.setBorderWidth(borderWidth)
            _bubbleDrawable.setGapPadding(gapPadding)
            _bubbleDrawable.setBorderColor(borderColor)
            _bubbleDrawable.setArrowDirection(_drawableArrowDirection)
            _bubbleDrawable.setArrowPosPolicy(arrowPosPolicy)
            _bubbleDrawable.setArrowTo(arrowToOffsetX, arrowToOffsetY)
            _bubbleDrawable.setArrowPosDelta(arrowPosOffset)
            _bubbleDrawable.setArrowHeight(arrowHeight)
            _bubbleDrawable.setArrowWidth(arrowWidth)
            _bubbleDrawable.updateShapes()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                _parentView.background = _bubbleDrawable
            } else {
                _parentView.setBackgroundDrawable(_bubbleDrawable) // noinspection deprecation
            }
        }
    }

    override fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        cornerTopLeftRadius = topLeft
        cornerTopRightRadius = topRight
        cornerBottomLeftRadius = bottomLeft
        cornerBottomRightRadius = bottomRight
    }

    override fun setCornerRadius(radius: Float) {
        setCornerRadius(radius, radius, radius, radius)
    }

    override fun setPadding(padding: Float) {
        setPadding(padding, padding)
    }

    override fun setPadding(paddingHorizontal: Float, paddingVertical: Float) {
        setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
    }

    override fun setPadding(left: Float, top: Float, right: Float, bottom: Float) {
        _bubbleListener ?: return

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            val stack = Throwable().stackTrace
            for (i in 0..6) {
                if (stack[i].className == View::class.java.name && (stack[i].methodName == "recomputePadding")
                ) {
                    Log.w(TAG, "Called setPadding by View on old Android platform")
                    _bubbleListener?.setSuperPadding(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
                    return
                }
            }
        }

        _paddingLeftOffset = 0.also { _paddingBottomOffset = it }.also { _paddingRightOffset = it }.also { _paddingTopOffset = it }
        when (_drawableArrowDirection) {
            ArrowDirection.Left -> _paddingLeftOffset += arrowHeight.toInt()
            ArrowDirection.Up -> _paddingTopOffset += arrowHeight.toInt()
            ArrowDirection.Right -> _paddingRightOffset += arrowHeight.toInt()
            ArrowDirection.Down -> _paddingBottomOffset += arrowHeight.toInt()
            ArrowDirection.Auto, ArrowDirection.None -> {}
        }

        val superPaddingLeft: Int = left.toInt() + _paddingLeftOffset
        val superPaddingTop: Int = top.toInt() + _paddingTopOffset
        val superPaddingRight: Int = right.toInt() + _paddingRightOffset
        val superPaddingBottom: Int = bottom.toInt() + _paddingBottomOffset

        if (superPaddingLeft != _bubbleListener!!.getSuperPaddingLeft() || superPaddingTop != _bubbleListener!!.getSuperPaddingTop() || superPaddingRight != _bubbleListener!!.getSuperPaddingRight() || superPaddingBottom != _bubbleListener!!.getSuperPaddingBottom()) {
            _parentView.post {
                _bubbleListener!!.setSuperPadding(
                    superPaddingLeft, superPaddingTop, superPaddingRight,
                    superPaddingBottom
                )
            }
        }
    }

    override fun requestUpdateBubble() {
        updateDrawable(_parentView.width, _parentView.height, true)
    }

    private fun setArrowToViewRef(targetView: View?) {
        _arrowToViewRef?.let {
            val oldTargetView: View? = it.get()
            oldTargetView?.removeOnLayoutChangeListener(_onLayoutChangeListener)
        }

        _arrowToViewRef = if (targetView != null) WeakReference(targetView) else null
        targetView?.addOnLayoutChangeListener(_onLayoutChangeListener)
    }

    private fun initAttr(context: Context, attrs: AttributeSet?) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextKBubble)
        arrowDirection =
            ArrowDirection.valueOf(
                typedArray.getInteger(R.styleable.TextKBubble_textKBubble_arrowDirection, ArrowDirection.Auto.value)
            )
        arrowHeight =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_arrowHeight, ARROW_HEIGHT)
        arrowWidth =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_arrowWidth, ARROW_WIDTH)
        arrowPosPolicy =
            ArrowPosPolicy.valueOf(
                typedArray.getInteger(R.styleable.TextKBubble_textKBubble_arrowPosPolicy, ArrowPosPolicy.TargetCenter.value)
            )
        arrowPosOffset =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_arrowPosOffset, ARROW_POS_OFFSET)
        arrowToViewId =
            typedArray.getResourceId(R.styleable.TextKBubble_textKBubble_arrowToViewId, 0)

        bgColor =
            typedArray.getColor(R.styleable.TextKBubble_textKBubble_bgColor, BG_COLOR)
        borderColor =
            typedArray.getColor(R.styleable.TextKBubble_textKBubble_borderColor, BORDER_COLOR)
        borderWidth =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_borderWidth, BORDER_WIDTH)
        gapPadding =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_gapPadding, GAP_PADDING)

        val cornerRadius =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_cornerRadius, CORNER_RADIUS)
        cornerTopLeftRadius = cornerRadius.also { cornerBottomRightRadius = it }
            .also { cornerBottomLeftRadius = it }.also { cornerTopRightRadius = it }
        cornerTopLeftRadius =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_cornerTopLeftRadius, cornerTopLeftRadius)
        cornerTopRightRadius =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_cornerTopRightRadius, cornerTopLeftRadius)
        cornerBottomLeftRadius =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_cornerBottomLeftRadius, cornerTopLeftRadius)
        cornerBottomRightRadius =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_cornerBottomRightRadius, cornerTopLeftRadius)

        val padding =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_padding, PADDING)
        paddingLeft = padding.also { paddingTop = it }
            .also { paddingRight = it }.also { paddingBottom = it }
        paddingLeft =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_paddingHorizontal, paddingLeft).also { paddingRight = it }
        paddingTop =
            typedArray.getDimension(R.styleable.TextKBubble_textKBubble_paddingVertical, paddingTop).also { paddingBottom = it }
        typedArray.recycle()
    }

    /**
     * 根据目标对象相对中心位置，推导箭头朝向
     * @param bubble Rect 气泡的区域
     * @param target Rect 目标区域
     * @return ArrowDirection 推导出的箭头朝向
     */
    private fun getAutoArrowDirection(bubble: Rect, target: Rect): ArrowDirection {
        if (!bubble.intersects(target.left, target.top, target.right, target.bottom)) {
            val offset = Point(bubble.centerX() - target.centerX(), bubble.centerY() - target.centerY())
            if (abs(offset.x) < bubble.width() / 2 + target.width() / 2) {
                if (offset.y < 0) {
                    return ArrowDirection.Down
                } else if (offset.y > 0) {
                    return ArrowDirection.Up
                }
            } else if (abs(offset.y) < bubble.height() / 2 + target.height() / 2) {
                if (offset.x < 0) {
                    return ArrowDirection.Right
                } else if (offset.x > 0) {
                    return ArrowDirection.Left
                }
            }
        }
        return ArrowDirection.None
    }
}