package com.mozhimen.uicorek.textk.bubble

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowDirection
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowPosPolicy
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubbleListener

/**
 * @ClassName TextKBubble
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/5 13:09
 * @Version 1.0
 */
class TextKBubble @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attrs, defStyleAttr), ITextKBubble, ITextKBubbleListener {

    //region # variate
    private val _textKBubbleDelegate: TextKBubbleDelegate by lazy { TextKBubbleDelegate(context) }

    override fun setArrowDirection(arrowDirection: EArrowDirection) {
        _textKBubbleDelegate.setArrowDirection(arrowDirection)
    }

    override fun getArrowDirection(): EArrowDirection =
        _textKBubbleDelegate.getArrowDirection()

    override fun setArrowHeight(arrowHeight: Float) {
        _textKBubbleDelegate.setArrowHeight(arrowHeight)
    }

    override fun getArrowHeight(): Float =
        _textKBubbleDelegate.getArrowHeight()

    override fun setArrowWidth(arrowWidth: Float) {
        _textKBubbleDelegate.setArrowWidth(arrowWidth)
    }

    override fun getArrowWidth(): Float =
        _textKBubbleDelegate.getArrowWidth()

    override fun setArrowPosPolicy(arrowPosPolicy: EArrowPosPolicy) {
        _textKBubbleDelegate.setArrowPosPolicy(arrowPosPolicy)
    }

    override fun getArrowPosPolicy(): EArrowPosPolicy =
        _textKBubbleDelegate.getArrowPosPolicy()

    override fun setArrowPosOffset(offset: Float) {
        _textKBubbleDelegate.setArrowPosOffset(offset)
    }

    override fun getArrowPosOffset(): Float =
        _textKBubbleDelegate.getArrowPosOffset()

    override fun setArrowToByViewId(targetViewId: Int) {
        _textKBubbleDelegate.setArrowToByViewId(targetViewId)
    }

    override fun getArrowToByViewId(): Int =
        _textKBubbleDelegate.getArrowToByViewId()

    override fun setArrowTo(targetView: View) {
        _textKBubbleDelegate.setArrowTo(targetView)
    }

    override fun getArrowTo(): View? =
        _textKBubbleDelegate.getArrowTo()

    override fun setBgColor(bgColor: Int) {
        _textKBubbleDelegate.setBgColor(bgColor)
    }

    override fun getBgColor(): Int =
        _textKBubbleDelegate.getBgColor()

    override fun setBorderColor(borderColor: Int) {
        _textKBubbleDelegate.setBorderColor(borderColor)
    }

    override fun getBorderColor(): Int =
        _textKBubbleDelegate.getBorderColor()

    override fun setBorderWidth(borderWidth: Float) {
        _textKBubbleDelegate.setBorderWidth(borderWidth)
    }

    override fun getBorderWidth(): Float =
        _textKBubbleDelegate.getBorderWidth()

    override fun setGapPadding(gapPadding: Float) {
        _textKBubbleDelegate.setGapPadding(gapPadding)
    }

    override fun getGapPadding(): Float =
        _textKBubbleDelegate.getGapPadding()

    override fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        _textKBubbleDelegate.setCornerRadius(topLeft, topRight, bottomRight, bottomLeft)
    }

    override fun setCornerRadius(radius: Float) {
        _textKBubbleDelegate.setCornerRadius(radius)
    }

    override fun getCornerTopLeftRadius(): Float =
        _textKBubbleDelegate.getCornerTopLeftRadius()

    override fun getCornerTopRightRadius(): Float =
        _textKBubbleDelegate.getCornerTopRightRadius()

    override fun getCornerBottomLeftRadius(): Float =
        _textKBubbleDelegate.getCornerBottomLeftRadius()

    override fun getCornerBottomRightRadius(): Float =
        _textKBubbleDelegate.getCornerBottomRightRadius()

    override fun setPadding(padding: Float) {
        _textKBubbleDelegate.setPadding(padding)
    }

    override fun setPadding(paddingHorizontal: Float, paddingVertical: Float) {
        _textKBubbleDelegate.setPadding(paddingHorizontal, paddingVertical)
    }

    override fun setPadding(left: Float, top: Float, right: Float, bottom: Float) {
        _textKBubbleDelegate.setPadding(left, top, right, bottom)
    }

    override fun getPaddingLeft(): Int =
        _textKBubbleDelegate.getPaddingLeft()

    override fun getPaddingTop(): Int =
        _textKBubbleDelegate.getPaddingTop()

    override fun getPaddingRight(): Int =
        _textKBubbleDelegate.getPaddingRight()

    override fun getPaddingBottom(): Int =
        _textKBubbleDelegate.getPaddingBottom()

    override fun requestUpdateBubble() {
        _textKBubbleDelegate.requestUpdateBubble()
    }

    override fun setSuperPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
    }

    override fun getSuperPaddingLeft(): Int {
        return super.getPaddingLeft()
    }

    override fun getSuperPaddingTop(): Int {
        return super.getPaddingTop()
    }

    override fun getSuperPaddingRight(): Int {
        return super.getPaddingRight()
    }

    override fun getSuperPaddingBottom(): Int {
        return super.getPaddingBottom()
    }
    //endregion

    init {
        if (!isInEditMode) {
            _textKBubbleDelegate.init(this, attrs)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (!isInEditMode) {
            _textKBubbleDelegate.updateDrawable(right - left, bottom - top, true)
        }
    }
}