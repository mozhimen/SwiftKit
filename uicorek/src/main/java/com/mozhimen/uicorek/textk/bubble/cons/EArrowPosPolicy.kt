package com.mozhimen.uicorek.textk.bubble.cons

import android.util.SparseArray


/**
 * @ClassName ArrowPosPolicy
 * @Description  箭头位置策略定义
 * @property value Int
 * @constructor
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/31 14:00
 * @Version 1.0
 */
enum class EArrowPosPolicy(value: Int) {

    TargetCenter(0),//箭头指向目标View的中心点
    SelfCenter(1),//箭头从自己的中心点发出
    SelfBegin(2),//结合setArrowPosOffset，箭头从所在轴的头端开始偏移
    SelfEnd(3);//结合setArrowPosOffset，箭头从所在轴的尾端开始偏移

    companion object {
        private var intToTypeDict = SparseArray<EArrowPosPolicy>()

        init {
            for (type in values()) {
                intToTypeDict.put(type.value, type)
            }
        }

        fun valueOf(value: Int): EArrowPosPolicy {
            return intToTypeDict[value] ?: TargetCenter
        }
    }

    var value = 0

    init {
        this.value = value
    }
}