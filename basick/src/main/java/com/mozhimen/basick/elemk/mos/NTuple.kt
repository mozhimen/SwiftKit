package com.mozhimen.basick.elemk.mos

/**
 * @ClassName NTuple
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/17
 * @Version 1.0
 */
data class NTuple2<T1, T2>(val t1: T1, val t2: T2) {
    operator fun <T3, T4, T5> plus(other: NTuple3<T3, T4, T5>): NTuple5<T1, T2, T3, T4, T5> {
        return NTuple5(this.t1, this.t2, other.t1, other.t2, other.t3)
    }
}
data class NTuple3<T1, T2, T3>(val t1: T1, val t2: T2, val t3: T3)
data class NTuple4<T1, T2, T3, T4>(val t1: T1, val t2: T2, val t3: T3, val t4: T4)
data class NTuple5<T1, T2, T3, T4, T5>(val t1: T1, val t2: T2, val t3: T3, val t4: T4, val t5: T5)
