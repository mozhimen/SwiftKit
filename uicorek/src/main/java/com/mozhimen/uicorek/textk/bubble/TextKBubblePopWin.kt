package com.mozhimen.uicorek.textk.bubble

import android.graphics.Rect
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble.ArrowDirection
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble.ArrowPosPolicy
import com.mozhimen.uicorek.textk.bubble.commons.TextKBubblePopWinBase
import com.mozhimen.uicorek.textk.bubble.mos.RelativePos

/**
 * @ClassName TextKBubblePopWin
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/7 3:38
 * @Version 1.0
 */
open class TextKBubblePopWin(
    contentView: View, private val textKBubble: ITextKBubble
) : TextKBubblePopWinBase(contentView, textKBubble) {
    override var _padding = 2f.dp2px()
    override var _arrowPosOffset = 0
    override var _delayMillis: Long = 2500
    var paddingHorizontal = 0
    var paddingVertical = 0

    /**
     * 显示气泡弹窗，并将箭头指向目标
     * @param anchorView View 气泡箭头对齐的目标
     * @param relativePos RelativePos 气泡与目标的对齐方式
     * @param marginH Int
     * @param marginV Int
     */
    override fun showArrowTo(anchorView: View, relativePos: RelativePos, marginH: Int, marginV: Int) {
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

        val outProp = PopupProp()
        getPopupProp(
            screenWidth,
            screenHeight,
            navigationBarHeight,
            anchorRect,
            contentWidth,
            relativePos,
            0,
            -paddingVertical - 4f.dp2px() / 2,
            _padding,
            outProp
        )

        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        animationStyle = outProp.animationStyle
        if (contentWidth > outProp.maxWidth) {
            width = outProp.maxWidth
        }
        textKBubble.arrowDirection = outProp.direction
        textKBubble.arrowPosPolicy = ArrowPosPolicy.SelfCenter
        textKBubble.arrowToView = anchorView
        textKBubble.arrowPosOffset = _arrowPosOffset.toFloat()

        isClippingEnabled = false

        Log.d(TAG, "showArrowTo: location equals: x: ${outProp.x} y: ${outProp.y}")
        showAtLocation(
            anchorView,
            Gravity.CENTER_HORIZONTAL or Gravity.TOP,
            outProp.x + paddingHorizontal,
            outProp.y
        )

        if (_delayMillis > 0) {
            setCancelOnLater(_delayMillis)
        }
    }

    private fun getRectInWindow(view: View): Rect {
        val location = IntArray(2)
        view.getLocationInWindow(location)
        return Rect(location[0], location[1], location[0] + view.width, location[1] + view.height)
    }

    /**
     * 获得用于补偿位置偏移的 NavigationBar 高度
     * 在 Android5.0 以上系统，showAtLocation 如果使用了 Gravity.BOTTOM 或 Gravity.CENTER_VERTICAL 可能出现显示偏移的Bug
     * 偏移值和 NavigationBar 高度有关
     * @param view View 目标View
     * @return Int 如果需要修正且存在NavigationBar则返回高度，否则为0
     */
    private fun getNavigationBarHeightOffset(view: View): Int =
        UtilKScreen.getNavBarHeight(view)

    private inner class PopupProp {
        var direction: ArrowDirection = ArrowDirection.Auto
        var arrowPosPolicy: ArrowPosPolicy = ArrowPosPolicy.TargetCenter
        var animationStyle: Int = 0
        var maxWidth: Int = 0
        var gravity: Int = 0
        var x: Int = 0
        var y: Int = 0
    }

    private fun getPopupPropOfMaxWidth(
        screenWidth: Int,
        anchorRect: Rect,
        relativePos: RelativePos,
        marginH: Int,
        padding: Int,
        outProp: PopupProp
    ) {
        when (relativePos.getHorizontalRelate()) {
            RelativePos.ALIGN_LEFT -> outProp.maxWidth = screenWidth - anchorRect.left - marginH - padding
            RelativePos.TO_RIGHT_OF -> outProp.maxWidth = screenWidth - anchorRect.right - marginH - padding
            RelativePos.TO_LEFT_OF -> outProp.maxWidth = anchorRect.left - marginH - padding
            RelativePos.ALIGN_RIGHT -> outProp.maxWidth = anchorRect.right - marginH - padding
            RelativePos.CENTER_HORIZONTAL -> outProp.maxWidth = screenWidth - 2 * padding
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
            RelativePos.CENTER_HORIZONTAL -> when {
                anchorRect.centerX() < contentWidth / 2 + padding -> {
                    outProp.gravity = outProp.gravity or Gravity.LEFT
                    outProp.x = padding
                }
                screenWidth - anchorRect.centerX() < contentWidth / 2 + padding -> {
                    outProp.gravity = outProp.gravity or Gravity.RIGHT
                    outProp.x = padding
                }
                else -> {
                    outProp.gravity = Gravity.CENTER_HORIZONTAL
                    outProp.x = anchorRect.centerX() - screenWidth / 2
                }
            }
        }
    }

    private fun getPopupProp(
        screenWidth: Int,
        screenHeight: Int,
        navigationBarHeight: Int,
        anchorRect: Rect,
        contentWidth: Int,
        relativePos: RelativePos,
        marginH: Int,
        marginV: Int,
        padding: Int,
        outProp: PopupProp
    ) {
        outProp.direction = relativePos.getArrowDirection()
        outProp.animationStyle = getAnimationStyle(outProp.direction)
        outProp.gravity = 0
        getPopupPropOfX(
            screenWidth,
            anchorRect,
            contentWidth,
            relativePos,
            marginH,
            padding,
            outProp
        )
        getPopupPropOfMaxWidth(screenWidth, anchorRect, relativePos, marginH, padding, outProp)
        getPopupPropOfY(
            screenHeight,
            navigationBarHeight,
            anchorRect,
            relativePos,
            marginV,
            outProp
        )

        when (outProp.direction) {
            ArrowDirection.Up, ArrowDirection.Down -> when (relativePos.getHorizontalRelate()) {
                RelativePos.CENTER_HORIZONTAL -> outProp.arrowPosPolicy =
                    ArrowPosPolicy.TargetCenter
                RelativePos.ALIGN_LEFT -> outProp.arrowPosPolicy =
                    ArrowPosPolicy.SelfBegin
                RelativePos.ALIGN_RIGHT -> outProp.arrowPosPolicy =
                    ArrowPosPolicy.SelfEnd
                else -> outProp.arrowPosPolicy = ArrowPosPolicy.TargetCenter
            }
            ArrowDirection.Left, ArrowDirection.Right -> when (relativePos.getVerticalRelate()) {
                RelativePos.CENTER_HORIZONTAL -> outProp.arrowPosPolicy =
                    ArrowPosPolicy.TargetCenter
                RelativePos.ALIGN_TOP -> outProp.arrowPosPolicy =
                    ArrowPosPolicy.SelfBegin
                RelativePos.ALIGN_BOTTOM -> outProp.arrowPosPolicy =
                    ArrowPosPolicy.SelfEnd
                else -> outProp.arrowPosPolicy = ArrowPosPolicy.TargetCenter
            }
            else -> outProp.arrowPosPolicy = ArrowPosPolicy.TargetCenter
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
}