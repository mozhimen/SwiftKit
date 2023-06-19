package com.mozhimen.uicorek.textk.bubble.commons

import android.view.View
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowDirection
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowPosPolicy

/**
 * @ClassName ITextKBubble
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/5 13:21
 * @Version 1.0
 */
interface ITextKBubble {

    /**
     * 设置箭头朝向
     * @param arrowDirection ArrowDirection 上下左右或者无
     */
    fun setArrowDirection(arrowDirection: EArrowDirection)
    fun getArrowDirection(): EArrowDirection

    /**
     * 设置箭头三角形厚度
     * @param arrowHeight Float 箭头厚度
     */
    fun setArrowHeight(arrowHeight: Float)
    fun getArrowHeight(): Float

    /**
     * 设置箭头三角形底宽
     * @param arrowWidth Float 箭头底边宽度
     */
    fun setArrowWidth(arrowWidth: Float)
    fun getArrowWidth(): Float

    /**
     * 设置箭头在边线上的位置策略
     * @param arrowPosPolicy ArrowPosPolicy 箭头位置策略
     */
    fun setArrowPosPolicy(arrowPosPolicy: EArrowPosPolicy)
    fun getArrowPosPolicy(): EArrowPosPolicy

    /**
     * 设置箭头在所在边线上的偏移距离
     * 视 ArrowPosPolicy 而定，为 TargetCenter 或 SelfCenter 时无意义
     * @param offset Float 基于箭头位置策略，相应的偏差
     * 朝上/下时在X轴方向偏移，朝左/右时在Y轴方向偏移
     * 值必须 >0，视 ArrowPosPolicy 从首段或尾端开始偏移
     */
    fun setArrowPosOffset(offset: Float)
    fun getArrowPosOffset(): Float

    /**
     * 设置箭头指向的View对象
     * 设置了View对象后，setArrowPos将不起作用
     * @param targetViewId Int 指向的ViewId
     */
    fun setArrowToByViewId(targetViewId: Int)
    fun getArrowToByViewId(): Int

    /**
     * 设置箭头指向的View对象
     * @param targetView View
     */
    fun setArrowTo(targetView: View)
    fun getArrowTo(): View?

    /**
     * 设置气泡背景色
     * @param bgColor Int 气泡背景颜色
     */
    fun setBgColor(bgColor: Int)
    fun getBgColor(): Int

    /**
     * 设置边框线颜色
     * @param borderColor Int 边框颜色
     */
    fun setBorderColor(borderColor: Int)
    fun getBorderColor(): Int

    /**
     * 设置边框线宽
     * @param borderWidth Float 边框厚度
     */
    fun setBorderWidth(borderWidth: Float)
    fun getBorderWidth(): Float

    /**
     * 设置边框于背景之间的间隙宽度
     * @param gapPadding Float 间隙宽度
     */
    fun setGapPadding(gapPadding: Float)
    fun getGapPadding(): Float

    /**
     * 设置边角弧度
     * 可以为四角指定不同弧度
     * @param topLeft Float 左上角
     * @param topRight Float 右上角
     * @param bottomRight Float 右下角
     * @param bottomLeft Float 左下角
     */
    fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float)
    fun setCornerRadius(radius: Float)
    fun getCornerTopLeftRadius(): Float
    fun getCornerTopRightRadius(): Float
    fun getCornerBottomLeftRadius(): Float
    fun getCornerBottomRightRadius(): Float

    fun setPadding(padding: Float)
    fun setPadding(paddingHorizontal: Float, paddingVertical: Float)
    /**
     * 设定Padding
     * 将自动将箭头区域占用空间加入Padding，使内容能够完全被气泡包含
     * @param left Float
     * @param top Float
     * @param right Float
     * @param bottom Float
     */
    fun setPadding(left: Float, top: Float, right: Float, bottom: Float)
    fun getPaddingLeft(): Int
    fun getPaddingTop(): Int
    fun getPaddingRight(): Int
    fun getPaddingBottom(): Int

    /**
     * 请求刷新UI样式
     * 设置好以上属性后，调用该函数进行刷新
     */
    fun requestUpdateBubble()
}