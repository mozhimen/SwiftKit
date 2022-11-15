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

    /**
     * 获取箭头朝向
     * @return ArrowDirection
     */
    fun getArrowDirection(): EArrowDirection

//    /**
//     * 设置箭头朝向
//     */
//    var arrowDirection: ArrowDirection

    /**
     * 设置箭头三角形厚度
     * @param arrowHeight Float 箭头厚度
     */
    fun setArrowHeight(arrowHeight: Float)

    /**
     * 获取箭头厚度
     * @return Float
     */
    fun getArrowHeight(): Float

//    /**
//     * 设置箭头三角形厚度
//     */
//    var arrowHeight: Float

    /**
     * 设置箭头三角形底宽
     * @param arrowWidth Float 箭头底边宽度
     */
    fun setArrowWidth(arrowWidth: Float)

    /**
     * 获取箭头三角形底宽
     * @return Float
     */
    fun getArrowWidth(): Float

//    /**
//     * 设置箭头三角形底宽
//     */
//    var arrowWidth: Float

    /**
     * 设置箭头在边线上的位置策略
     * @param arrowPosPolicy ArrowPosPolicy 箭头位置策略
     */
    fun setArrowPosPolicy(arrowPosPolicy: EArrowPosPolicy)

    /**
     * 获取箭头在边线上的位置策略
     * @return ArrowPosPolicy
     */
    fun getArrowPosPolicy(): EArrowPosPolicy

//    /**
//     * 设置箭头在边线上的位置策略
//     */
//    var arrowPosPolicy: ArrowPosPolicy

    /**
     * 设置箭头在所在边线上的偏移距离
     * 视 ArrowPosPolicy 而定，为 TargetCenter 或 SelfCenter 时无意义
     * @param offset Float 基于箭头位置策略，相应的偏差
     * 朝上/下时在X轴方向偏移，朝左/右时在Y轴方向偏移
     * 值必须 >0，视 ArrowPosPolicy 从首段或尾端开始偏移
     */
    fun setArrowPosOffset(offset: Float)

    /**
     * 获取箭头在所在边线上的偏移距离
     * @return Float
     */
    fun getArrowPosOffset(): Float

//    /**
//     * 设置箭头在所在边线上的偏移距离
//     */
//    var arrowPosOffset: Float

    /**
     * 设置箭头指向的View对象
     * 设置了View对象后，setArrowPos将不起作用
     * @param targetViewId Int 指向的ViewId
     */
    fun setArrowToByViewId(targetViewId: Int)

    /**
     * 获取箭头指向的View对象Id
     * @return Int
     */
    fun getArrowToByViewId(): Int

//    /**
//     * 设置箭头指向的View对象Id
//     */
//    var arrowToViewId: Int

    /**
     * 设置箭头指向的View对象
     * @param targetView View
     */
    fun setArrowTo(targetView: View)

    /**
     * 获取箭头指向的View对象
     * @return View
     */
    fun getArrowTo(): View?

//    /**
//     * 设置箭头指向的View对象
//     */
//    var arrowToView: View?

    /**
     * 设置气泡背景色
     * @param bgColor Int 气泡背景颜色
     */
    fun setBgColor(bgColor: Int)

    /**
     * 获取气泡背景色
     * @return Int
     */
    fun getBgColor(): Int

//    /**
//     * 设置气泡背景色
//     */
//    var bgColor: Int

    /**
     * 设置边框线颜色
     * @param borderColor Int 边框颜色
     */
    fun setBorderColor(borderColor: Int)

    /**
     * 获取边框线颜色
     * @return Int
     */
    fun getBorderColor(): Int

//    /**
//     * 设置边框线颜色
//     */
//    var borderColor: Int

    /**
     * 设置边框线宽
     * @param borderWidth Float 边框厚度
     */
    fun setBorderWidth(borderWidth: Float)

    /**
     * 获取边框线宽
     * @return Float
     */
    fun getBorderWidth(): Float

//    /**
//     * 设置边框线宽
//     */
//    var borderWidth: Float

    /**
     * 设置边框于背景之间的间隙宽度
     * @param gapPadding Float 间隙宽度
     */
    fun setGapPadding(gapPadding: Float)

    /**
     * 获取边框于背景之间的间隙宽度
     * @return Float
     */
    fun getGapPadding(): Float

//    /**
//     * 设置边框于背景之间的间隙宽度
//     */
//    var gapPadding: Float

    /**
     * 设置边角弧度
     * 可以为四角指定不同弧度
     * @param topLeft Float 左上角
     * @param topRight Float 右上角
     * @param bottomRight Float 右下角
     * @param bottomLeft Float 左下角
     */
    fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float)

    /**
     * 设置边角弧度
     * @param radius Float
     */
    fun setCornerRadius(radius: Float)

    /**
     * 设置边角弧度左上角
     * @return Float
     */
    fun getCornerTopLeftRadius(): Float

    /**
     * 设置边角弧度右上角
     * @return Float
     */
    fun getCornerTopRightRadius(): Float

    /**
     * 设置边角弧度左下角
     * @return Float
     */
    fun getCornerBottomLeftRadius(): Float

    /**
     * 设置边角弧度右下角
     * @return Float
     */
    fun getCornerBottomRightRadius(): Float

//    /**
//     * 设置边角弧度左上角
//     */
//    var cornerTopLeftRadius: Float
//
//    /**
//     * 设置边角弧度右上角
//     */
//    var cornerTopRightRadius: Float
//
//    /**
//     * 设置边角弧度左下角
//     */
//    var cornerBottomLeftRadius: Float
//
//    /**
//     * 设置边角弧度右下角
//     */
//    var cornerBottomRightRadius: Float

    /**
     * 设定Padding
     * @param padding Float
     */
    fun setPadding(padding: Float)

    /**
     * 设定Padding
     * @param paddingHorizontal Float
     * @param paddingVertical Float
     */
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

    /**
     * 设定Padding左
     * @return Float
     */
    fun getPaddingLeft(): Int

    /**
     * 设定Padding上
     * @return Float
     */
    fun getPaddingTop(): Int

    /**
     * 设定Padding右
     * @return Float
     */
    fun getPaddingRight(): Int

    /**
     * 设定Padding下
     * @return Float
     */
    fun getPaddingBottom(): Int

//    /**
//     * 设定Padding左
//     */
//    var paddingLeft: Float
//
//    /**
//     * 设定Padding上
//     */
//    var paddingTop: Float
//
//    /**
//     * 设定Padding右
//     */
//    var paddingRight: Float
//
//    /**
//     * 设定Padding下
//     */
//    var paddingBottom: Float

    /**
     * 请求刷新UI样式
     * 设置好以上属性后，调用该函数进行刷新
     */
    fun requestUpdateBubble()
}