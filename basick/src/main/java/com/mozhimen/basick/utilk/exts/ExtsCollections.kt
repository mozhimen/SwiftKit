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

/**
 * 将一个Collection的Item中的某个Element组合起来成一个新Collection
 * @receiver Iterable<T>
 * @param predicate Function1<T, I>
 * @return List<I>
 */
fun <T, I> Iterable<T>.combineElement2ListIgnoreRepeat(predicate: (T) -> I): List<I> =
    UtilKCollections.combineElement2ListIgnoreRepeat(this, predicate)

/**
 * 获取符合条件的元素在该Collection的位置
 * @receiver Iterable<T>
 * @param predicate Function1<T, Boolean>
 * @return Int
 */
fun <T> Iterable<T>.getIndexFirst(predicate: (T) -> Boolean): Int? =
    UtilKCollections.getIndexFirst(this, predicate)

/**
 * 判断符合条件的元素是否在Collection中
 * @receiver Iterable<T>
 * @param predicate Function1<T, Boolean>
 * @return Int
 */
fun <T> Iterable<T>.containsBy(predicate: (T) -> Boolean): Boolean =
    UtilKCollections.containsBy(this, predicate)