package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.java.datatype.UtilKCollection


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
fun <T, I> Iterable<T>.joinElement2List(predicate: (T) -> I): List<I> =
    UtilKCollection.joinElement2List(this, predicate)

/**
 * 将一个Collection的Item中的某个Element组合起来成一个新Collection(忽略重复)
 * @receiver Iterable<T>
 * @param predicate Function1<T, I>
 * @return List<I>
 */
fun <T, I> Iterable<T>.joinElement2ListIgnoreRepeat(predicate: (T) -> I): List<I> =
    UtilKCollection.joinElement2ListIgnoreRepeat(this, predicate)

/**
 * 将一个Collection的Item中的某个Element组合起来成一个新Collection(忽略Null)
 * @receiver Iterable<T?>
 * @param predicate Function1<T?, I>
 * @return List<I>
 */
fun <T, I> Iterable<T>.joinElement2ListIgnoreNull(predicate: (T?) -> I): List<I> =
    UtilKCollection.joinElement2ListIgnoreNull(this, predicate)

/**
 * 获取符合条件的元素在该Collection的位置
 * @receiver Iterable<T>
 * @param predicate Function1<T, Boolean>
 * @return Int
 */
fun <T> Iterable<T>.getIndexFirst(predicate: (T) -> Boolean): Int? =
    UtilKCollection.getIndexFirst(this, predicate)

/**
 * 判断符合条件的元素是否在Collection中
 * @receiver Iterable<T>
 * @param predicate Function1<T, Boolean>
 * @return Int
 */
fun <T> Iterable<T>.containsBy(predicate: (T) -> Boolean): Boolean =
    UtilKCollection.containsBy(this, predicate)