package com.mozhimen.uicorek.drawablek.arrow.cons

import android.util.SparseArray

/**
 * @ClassName EArrowPosPolicy
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/14 0:47
 * @Version 1.0
 */
enum class EArrowPosPolicy(val value: Int) {
    TargetCenter(0),//箭头指向目标View的中心点
    SelfCenter(1),//箭头从自己的中心点发出
    SelfBegin(2),//结合setArrowPosOffset，箭头从所在轴的头端开始偏移
    SelfEnd(3);//结合setArrowPosOffset，箭头从所在轴的尾端开始偏移

    companion object {
        private var _int2TypeDict = SparseArray<EArrowPosPolicy>()

        init {
            for (type in values()) {
                _int2TypeDict.put(type.value, type)
            }
        }

        fun get(value: Int): EArrowPosPolicy {
            return _int2TypeDict[value] ?: TargetCenter
        }
    }
}