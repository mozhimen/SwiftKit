package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.datatype.UtilKCollections


/**
 * @ClassName ExtsCollections
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/7 17:19
 * @Version 1.0
 */
/**
 * 将一个Collection的Item中的某个Element组合起来成一个新Collection
 * @receiver Iterable<T>
 * @param predicate Function1<T, I>
 * @return List<I>
 */
fun <T, I> Iterable<T>.combineElement2List(predicate: (T) -> I): List<I> =
    UtilKCollections.combineElement2List(this, predicate)