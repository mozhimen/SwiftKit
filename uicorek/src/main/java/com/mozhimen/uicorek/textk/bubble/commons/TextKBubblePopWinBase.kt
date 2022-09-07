package com.mozhimen.uicorek.textk.bubble.commons

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble.ArrowDirection
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble.ArrowPosPolicy
import com.mozhimen.uicorek.textk.bubble.mos.RelativePos

/**
 * @ClassName PopwinKBubble
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/6 20:58
 * @Version 1.0
 */
open class TextKBubblePopWinBase(contentView: View, bubbleView: ITextKBubble) : PopupWindow(
    contentView,
    ViewGroup.LayoutParams.WRAP_CONTENT,
    ViewGroup.LayoutParams.WRAP_CONTENT
) {
    protected val TAG = "TextKBubblePopWinBase>>>>>"

    protected open var _padding: Int = 2f.dp2px()
    protected open var _arrowPosOffset = 0
    protected open var _delayMillis: Long = 0

    private var _bubbleListener: ITextKBubble = bubbleView
    private val _handler = Handler(Looper.getMainLooper())
    private val _dismissRunnable = Runnable { dismiss() }

    /**
     * 构造函数
     * contentView 弹窗内容View，可以是个包裹BubbleView的Layout（方便指定BubbleView的大小），也可以就是一个 BubbleView
     * bubbleView  气泡View
     */
    init {
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelOnTouchOutside(true)
        setCancelOnTouch(true)
    }

    override fun dismiss() {
        _handler.removeCallbacks(_dismissRunnable) // prevent leak
        super.dismiss()
    }

    /**
     * 设置点击气泡关闭弹窗
     * @param cancel Boolean 是否点击气泡关闭弹窗，默认是
     */
    fun setCancelOnTouch(cancel: Boolean) {
        contentView.setOnClickListener(if (cancel) View.OnClickListener { dismiss() } else null)
    }

    /**
     * 设置点击外部区域关闭弹窗
     * @param cancel Boolean 设置点击外部区域关闭弹窗，默认是
     */
    fun setCancelOnTouchOutside(cancel: Boolean) {
        isOutsideTouchable = cancel
        isFocusable = cancel
    }

    /**
     * 设置超时后自动关闭弹窗
     * @param delayMillis Long 自动关闭延时，设0将不会自动关闭
     */
    fun setCancelOnLater(delayMillis: Long) {
        _handler.removeCallbacks(_dismissRunnable)
        _delayMillis = delayMillis
        if (delayMillis > 0) {
            _handler.postDelayed(_dismissRunnable, delayMillis)
        }
    }

    /**
     * 设置气泡与屏幕边缘的（最小）间距
     * 因为气泡紧贴着屏幕边缘不太美观 边距px
     * @param padding Int
     */
    fun setPadding(padding: Int) {
        _padding = padding
    }

    /**
     * 设置箭头在所在边线上的偏移距离
     * 这是一个快捷入口，将转调BubbleView的setArrowPosOffset
     * @param arrowPosOffset Int 基于箭头位置策略，相应的偏差
     * 值必须>0，朝上/下时在X轴方向偏移，朝左/右时在Y轴方向偏移
     */
    fun setArrowPosOffset(arrowPosOffset: Int) {
        _arrowPosOffset = arrowPosOffset
    }

    /**
     * 显示气泡弹窗，并将箭头指向目标中央
     * @param anchorView View 气泡箭头要指向的目标
     * @param direction ArrowDirection 箭头方向，同时也决定了气泡出现的位置
     */
    fun showArrowTo(anchorView: View, direction: ArrowDirection) {
        showArrowTo(anchorView, direction, 0)
    }

    /**
     * 显示气泡弹窗，并将箭头指向目标
     * @param anchorView View 气泡箭头要指向的目标
     * @param direction ArrowDirection 箭头方向，同时也决定了气泡出现的位置
     * @param margin Int 气泡箭头与目标的距离
     */
    fun showArrowTo(anchorView: View, direction: ArrowDirection, margin: Int) {
        val relativePos: RelativePos = when (direction) {
            ArrowDirection.Up -> RelativePos(RelativePos.CENTER_HORIZONTAL, RelativePos.BELOW)
            ArrowDirection.Down -> RelativePos(RelativePos.CENTER_HORIZONTAL, RelativePos.ABOVE)
            ArrowDirection.Left -> RelativePos(RelativePos.TO_RIGHT_OF, RelativePos.CENTER_VERTICAL)
            ArrowDirection.Right -> RelativePos(RelativePos.TO_LEFT_OF, RelativePos.CENTER_VERTICAL)
            else -> RelativePos(RelativePos.CENTER_HORIZONTAL, RelativePos.CENTER_VERTICAL)
        }
        showArrowTo(anchorView, relativePos, margin, margin)
    }

    /**
     * 显示气泡弹窗，并将箭头指向目标
     * @param anchorView View 气泡箭头对齐的目标
     * @param relativePos RelativePos 气泡与目标的对齐方式
     * @param marginH Int
     * @param marginV Int
     */
    @SuppressLint("LongLogTag")
    open fun showArrowTo(anchorView: View, relativePos: RelativePos, marginH: Int, marginV: Int) {
        dismiss()
        val screenWidth = UtilKScreen.getScreenWidth()
        val screenHeight = UtilKScreen.getScreenHeight()

        val navigationBarHeight = getNavigationBarHeightOffset(anchorView)
        val anchorRect = getRectInWindow(anchorView)
        contentView.measure(
            View.MeasureSpec.makeMeasureSpec(screenWidth - 2 * _padding, View.MeasureSpec.AT_MOST),
            View.MeasureSpec.makeMeasureSpec(screenHeight - 2 * _padding, View.MeasureSpec.AT_MOST)
        )
        val contentWidth = contentView.measuredWidth
        val contentHeight = contentView.measuredHeight

        Log.d(TAG, "showArrowTo w:$contentWidth, h:$contentHeight")
        val outProp = PopupProp()
        getPopupProp(
            screenWidth,
            screenHeight,
            navigationBarHeight,
            anchorRect,
            contentWidth,
            contentHeight,
            relativePos,
            marginH,
            marginV,
            _padding,
            outProp
        )
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        animationStyle = outProp.animationStyle
        if (contentWidth > outProp.maxWidth) {
            width = outProp.maxWidth
        }
        _bubbleListener.setArrowDirection(outProp.direction)
        _bubbleListener.setArrowPosPolicy(outProp.arrowPosPolicy)
        _bubbleListener.setArrowTo(anchorView)
        _bubbleListener.setArrowPosOffset(_arrowPosOffset.toFloat())
        showAtLocation(anchorView, outProp.gravity, outProp.x, outProp.y)
        if (_delayMillis > 0) {
            setCancelOnLater(_delayMillis)
        }
    }

    private fun getRectInWindow(view: View): Rect {
        val location = IntArray(2)
        view.getLocationInWindow(location)
        return Rect(location[0], location[1], location[0] + view.width, location[1] + view.height)
    }

    private fun getPopupProp(
        screenWidth: Int, screenHeight: Int,
        navigationBarHeight: Int, anchorRect: Rect,
        contentWidth: Int, contentHeight: Int,
        relativePos: RelativePos, marginH: Int, marginV: Int, padding: Int,
        outProp: PopupProp
    ) {
        outProp.direction = relativePos.getArrowDirection()
        outProp.animationStyle = getAnimationStyle(outProp.direction)
        outProp.gravity = 0
        getPopupPropOfX(screenWidth, anchorRect, contentWidth, relativePos, marginH, padding, outProp)
        getPopupPropOfMaxWidth(screenWidth, anchorRect, relativePos, marginH, padding, outProp)
        getPopupPropOfY(screenHeight, navigationBarHeight, anchorRect, relativePos, marginV, outProp)
        when (outProp.direction) {
            ArrowDirection.Up, ArrowDirection.Down -> when (relativePos.getHorizontalRelate()) {
                RelativePos.CENTER_HORIZONTAL -> outProp.arrowPosPolicy = ArrowPosPolicy.TargetCenter
                RelativePos.ALIGN_LEFT -> outProp.arrowPosPolicy = ArrowPosPolicy.SelfBegin
                RelativePos.ALIGN_RIGHT -> outProp.arrowPosPolicy = ArrowPosPolicy.SelfEnd
                else -> outProp.arrowPosPolicy = ArrowPosPolicy.TargetCenter
            }
            ArrowDirection.Left, ArrowDirection.Right -> when (relativePos.getVerticalRelate()) {
                RelativePos.CENTER_HORIZONTAL -> outProp.arrowPosPolicy = ArrowPosPolicy.TargetCenter
                RelativePos.ALIGN_TOP -> outProp.arrowPosPolicy = ArrowPosPolicy.SelfBegin
                RelativePos.ALIGN_BOTTOM -> outProp.arrowPosPolicy = ArrowPosPolicy.SelfEnd
                else -> outProp.arrowPosPolicy = ArrowPosPolicy.TargetCenter
            }
            else -> outProp.arrowPosPolicy = ArrowPosPolicy.TargetCenter
        }
    }

    private fun getPopupPropOfX(
        screenWidth: Int,
        anchorRect: Rect,
        contentWidth: Int,
        relativePos: RelativePos,
        marginH: Int,
        padding: Int,
        outProp: PopupProp
    ) {
        when (relativePos.getHorizontalRelate()) {
            RelativePos.ALIGN_LEFT -> {
                outProp.gravity = outProp.gravity or Gravity.LEFT
                outProp.x = anchorRect.left + marginH
            }
            RelativePos.TO_RIGHT_OF -> {
                outProp.gravity = outProp.gravity or Gravity.LEFT
                outProp.x = anchorRect.right + marginH
            }
            RelativePos.TO_LEFT_OF -> {
                outProp.gravity = outProp.gravity or Gravity.RIGHT
                outProp.x = screenWidth - anchorRect.left + marginH
            }
            RelativePos.ALIGN_RIGHT -> {
                outProp.gravity = outProp.gravity or Gravity.RIGHT
                outProp.x = screenWidth - anchorRect.right + marginH
            }
            RelativePos.CENTER_HORIZONTAL -> {
                if (anchorRect.centerX() < contentWidth / 2 + padding) {
                    outProp.gravity = outProp.gravity or Gravity.LEFT
                    outProp.x = padding
                } else if (screenWidth - anchorRect.centerX() < contentWidth / 2 + padding) {
                    outProp.gravity = outProp.gravity or Gravity.RIGHT
                    outProp.x = padding
                } else {
                    outProp.gravity = Gravity.CENTER_HORIZONTAL
                    outProp.x = anchorRect.centerX() - screenWidth / 2
                }
            }
        }
    }

    private fun getPopupPropOfMaxWidth(screenWidth: Int, anchorRect: Rect, relativePos: RelativePos, marginH: Int, padding: Int, outProp: PopupProp) {
        when (relativePos.getHorizontalRelate()) {
            RelativePos.ALIGN_LEFT -> outProp.maxWidth = screenWidth - anchorRect.left - marginH - padding
            RelativePos.TO_RIGHT_OF -> outProp.maxWidth = screenWidth - anchorRect.right - marginH - padding
            RelativePos.TO_LEFT_OF -> outProp.maxWidth = anchorRect.left - marginH - padding
            RelativePos.ALIGN_RIGHT -> outProp.maxWidth = anchorRect.right - marginH - padding
            RelativePos.CENTER_HORIZONTAL -> outProp.maxWidth = screenWidth - 2 * padding
        }
    }

    private fun getPopupPropOfY(
        screenHeight: Int,
        navigationBarHeight: Int,
        anchorRect: Rect,
        relativePos: RelativePos,
        marginV: Int,
        outProp: PopupProp
    ) {
        when (relativePos.getVerticalRelate()) {
            RelativePos.ALIGN_TOP -> {
                outProp.gravity = outProp.gravity or Gravity.TOP
                outProp.y = anchorRect.top + marginV
            }
            RelativePos.BELOW -> {
                outProp.gravity = outProp.gravity or Gravity.TOP
                outProp.y = anchorRect.bottom + marginV
            }
            RelativePos.ALIGN_BOTTOM -> {
                outProp.gravity = outProp.gravity or Gravity.BOTTOM
                outProp.y = screenHeight + navigationBarHeight - anchorRect.bottom + marginV
            }
            RelativePos.ABOVE -> {
                outProp.gravity = outProp.gravity or Gravity.BOTTOM
                outProp.y = screenHeight + navigationBarHeight - anchorRect.top + marginV
            }
            RelativePos.CENTER_VERTICAL -> {
                outProp.gravity = outProp.gravity or Gravity.CENTER_VERTICAL
                outProp.y = anchorRect.centerY() - navigationBarHeight / 2 - screenHeight / 2
            }
        }
    }

    private fun getAnimationStyle(direction: ArrowDirection): Int {
        return when (direction) {
            ArrowDirection.Up -> R.style.TextKBubblePopWinAnim_ArrowUp
            ArrowDirection.Down -> R.style.TextKBubblePopWinAnim_ArrowDown
            ArrowDirection.Left -> R.style.TextKBubblePopWinAnim_ArrowLeft
            ArrowDirection.Right -> R.style.TextKBubblePopWinAnim_ArrowRight
            else -> R.style.TextKBubblePopWinAnim_ArrowNone
        }
    }

    /**
     * 获得用于补偿位置偏移的 NavigationBar 高度
     * 在 Android5.0 以上系统，showAtLocation 如果使用了 Gravity.BOTTOM 或 Gravity.CENTER_VERTICAL 可能出现显示偏移的Bug
     * 偏移值和 NavigationBar 高度有关
     * @param view View 目标View
     * @return Int 如果需要修正且存在NavigationBar则返回高度，否则为0
     */
    private fun getNavigationBarHeightOffset(view: View): Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) UtilKScreen.getNavBarHeight(view) else 0

    private class PopupProp {
        var direction: ArrowDirection = ArrowDirection.Auto
        var arrowPosPolicy: ArrowPosPolicy = ArrowPosPolicy.TargetCenter
        var animationStyle = 0
        var maxWidth = 0
        var gravity = 0
        var x = 0
        var y = 0
    }
}