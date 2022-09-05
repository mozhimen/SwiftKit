package com.mozhimen.uicorek.textk.bubble.commons

import android.util.SparseArray
import android.view.View

/**
 * @ClassName ITextKBubble
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/5 13:21
 * @Version 1.0
 */
interface ITextKBubble {
    /**
     * 箭头朝向定义
     * @property value Int
     * @property isLeft Boolean
     * @property isUp Boolean
     * @property isRight Boolean
     * @property isDown Boolean
     * @constructor
     */
    enum class ArrowDirection(value: Int) {
        None(-1),//无箭头
        Auto(0), //自动确定指向
        Left(1),
        Up(2),
        Right(3),
        Down(4);

        companion object {
            private var int2TypeDict = SparseArray<ArrowDirection>()

            init {
                for (type in values()) {
                    int2TypeDict.put(type.value, type)
                }
            }

            fun valueOf(value: Int): ArrowDirection {
                return int2TypeDict[value] ?: Auto
            }
        }

        var value = 0

        init {
            this.value = value
        }

        val isLeft: Boolean
            get() = this == Left
        val isUp: Boolean
            get() = this == Up
        val isRight: Boolean
            get() = this == Right
        val isDown: Boolean
            get() = this == Down
    }

    /**
     * 箭头位置策略定义
     * @property value Int
     * @constructor
     */
    enum class ArrowPosPolicy(value: Int) {

        TargetCenter(0),//箭头指向目标View的中心点
        SelfCenter(1),//箭头从自己的中心点发出
        SelfBegin(2),//结合setArrowPosDelta，箭头从所在轴的头端开始偏移
        SelfEnd(3);//结合setArrowPosDelta，箭头从所在轴的尾端开始偏移

        companion object {
            private var intToTypeDict = SparseArray<ArrowPosPolicy>()

            init {
                for (type in values()) {
                    intToTypeDict.put(type.value, type)
                }
            }

            fun valueOf(value: Int): ArrowPosPolicy {
                return intToTypeDict[value] ?: TargetCenter
            }
        }

        var value = 0

        init {
            this.value = value
        }
    }

//    /**
//     * 设置箭头朝向
//     * @param arrowDirection ArrowDirection 上下左右或者无
//     */
//    fun setArrowDirection(arrowDirection: ArrowDirection)
//
//    /**
//     * 获取箭头朝向
//     * @return ArrowDirection
//     */
//    fun getArrowDirection(): ArrowDirection

    var arrowDirection: ArrowDirection

//    /**
//     * 设置箭头三角形厚度
//     * @param arrowHeight Float 箭头厚度
//     */
//    fun setArrowHeight(arrowHeight: Float)
//
//    /**
//     * 获取箭头厚度
//     * @return Float
//     */
//    fun getArrowHeight(): Float

    var arrowHeight: Float

//    /**
//     * 设置箭头三角形底宽
//     * @param arrowWidth Float 箭头底边宽度
//     */
//    fun setArrowWidth(arrowWidth: Float)
//
//    /**
//     * 获取箭头三角形底宽
//     * @return Float
//     */
//    fun getArrowWidth(): Float

    var arrowWidth: Float

//    /**
//     * 设置箭头在边线上的位置策略
//     * @param policy ArrowPosPolicy 箭头位置策略
//     */
//    fun setArrowPosPolicy(policy: ArrowPosPolicy)
//
//    /**
//     * 获取箭头在边线上的位置策略
//     * @return ArrowPosPolicy
//     */
//    fun getArrowPosPolicy(): ArrowPosPolicy

    var arrowPosPolicy: ArrowPosPolicy

//    /**
//     * 设置箭头在所在边线上的偏移距离
//     * 视 ArrowPosPolicy 而定，为 TargetCenter 或 SelfCenter 时无意义
//     * @param delta Float 基于箭头位置策略，相应的偏差
//     * 朝上/下时在X轴方向偏移，朝左/右时在Y轴方向偏移
//     * 值必须 >0，视 ArrowPosPolicy 从首段或尾端开始偏移
//     */
//    fun setArrowPosDelta(delta: Float)
//
//    /**
//     * 获取箭头在所在边线上的偏移距离
//     * @return Float
//     */
//    fun getArrowPosDelta(): Float

    var arrowPosDelta: Float

    /**
     * 设置箭头指向的View对象
     * 设置了View对象后，setArrowPos将不起作用
     * @param viewId Int 指向的ViewId
     */
    fun setArrowTo(viewId: Int)

//    /**
//     * 设置箭头指向的View对象
//     * @param view View
//     */
//    fun setArrowTo(view: View)
//
//    /**
//     * 获取箭头指向的View对象
//     * @return View
//     */
//    fun getArrowTo(): View

    var arrowTo: View

//    /**
//     * 设置气泡背景色
//     * @param fillColor Int 气泡背景颜色
//     */
//    fun setFillColor(fillColor: Int)
//
//    /**
//     * 获取气泡背景色
//     * @return Int
//     */
//    fun getFillColor(): Int

    var fillColor: Int

//    /**
//     * 设置边框线颜色
//     * @param borderColor Int 边框颜色
//     */
//    fun setBorderColor(borderColor: Int)
//
//    /**
//     * 获取边框线颜色
//     * @return Int
//     */
//    fun getBorderColor(): Int

    var borderColor: Int

//    /**
//     * 设置边框线宽
//     * @param borderWidth Float 边框厚度
//     */
//    fun setBorderWidth(borderWidth: Float)
//
//    /**
//     * 获取边框线宽
//     * @return Float
//     */
//    fun getBorderWidth(): Float

    var borderWidth: Float

//    /**
//     * 设置边框于背景之间的间隙宽度
//     * @param fillPadding Float 间隙宽度
//     */
//    fun setFillPadding(fillPadding: Float)
//
//    /**
//     * 获取边框于背景之间的间隙宽度
//     * @return Float
//     */
//    fun getFillPadding(): Float

    var fillPadding: Float

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

//    /**
//     * 设置边角弧度左上角
//     * @return Float
//     */
//    fun getCornerTopLeftRadius(): Float
//
//    /**
//     * 设置边角弧度右上角
//     * @return Float
//     */
//    fun getCornerTopRightRadius(): Float
//
//    /**
//     * 设置边角弧度左下角
//     * @return Float
//     */
//    fun getCornerBottomLeftRadius(): Float
//
//    /**
//     * 设置边角弧度右下角
//     * @return Float
//     */
//    fun getCornerBottomRightRadius(): Float

    var cornerTopLeftRadius: Float
    var cornerTopRightRadius: Float
    var cornerBottomLeftRadius: Float
    var cornerBottomRightRadius: Float

    /**
     * 设定Padding
     * @param padding Int
     */
    fun setPadding(padding: Int)

    /**
     * 设定Padding
     * @param paddingHorizontal Int
     * @param paddingVertical Int
     */
    fun setPadding(paddingHorizontal: Int, paddingVertical: Int)

    /**
     * 设定Padding
     * 将自动将箭头区域占用空间加入Padding，使内容能够完全被气泡包含
     * @param left Int
     * @param top Int
     * @param right Int
     * @param bottom Int
     */
    fun setPadding(left: Int, top: Int, right: Int, bottom: Int)

//    /**
//     * 设定Padding左
//     * @return Int
//     */
//    fun getPaddingLeft(): Int
//
//    /**
//     * 设定Padding上
//     * @return Int
//     */
//    fun getPaddingTop(): Int
//
//    /**
//     * 设定Padding右
//     * @return Int
//     */
//    fun getPaddingRight(): Int
//
//    /**
//     * 设定Padding下
//     * @return Int
//     */
//    fun getPaddingBottom(): Int

    var paddingLeft: Int
    var paddingTop: Int
    var paddingRight: Int
    var paddingBottom: Int

    /**
     * 请求刷新UI样式
     * 设置好以上属性后，调用该函数进行刷新
     */
    fun requestUpdateBubble()
}