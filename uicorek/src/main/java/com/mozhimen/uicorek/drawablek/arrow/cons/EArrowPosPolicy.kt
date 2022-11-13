package com.mozhimen.uicorek.drawablek.arrow.cons

/**
 * @ClassName EArrowPosPolicy
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/14 0:47
 * @Version 1.0
 */
enum class EArrowPosPolicy {
    TargetCenter,//箭头指向目标View的中心点
    SelfCenter,//箭头从自己的中心点发出
    SelfBegin,//结合setArrowPosOffset，箭头从所在轴的头端开始偏移
    SelfEnd//结合setArrowPosOffset，箭头从所在轴的尾端开始偏移
}