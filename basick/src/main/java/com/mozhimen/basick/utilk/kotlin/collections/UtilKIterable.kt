package com.mozhimen.basick.utilk.kotlin.collections

import com.mozhimen.basick.elemk.commons.IA_BListener

/**
 * @ClassName UtilKCollections
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/27 0:24
 * @Version 1.0
 */
fun <T> Iterable<T>.containsBy(predicate: IA_BListener<T, Boolean>): Boolean =
    UtilKIterable.containsBy(this, predicate)

fun <T> Iterable<T>.getIndexFirst(predicate: IA_BListener<T, Boolean>): Int? =
        UtilKIterable.getIndexFirst(this, predicate)

fun <T, I> Iterable<T>.joinT2list(predicate: IA_BListener<T, I>): List<I> =
    UtilKIterable.joinT2list(this, predicate)

fun <T, I> Iterable<T>.joinT2listIgnoreRepeat(predicate: IA_BListener<T, I>): List<I> =
    UtilKIterable.joinT2listIgnoreRepeat(this, predicate)

fun <T, I> Iterable<T>.joinT2listIgnoreNull(predicate: IA_BListener<T?, I>): List<I> =
    UtilKIterable.joinT2listIgnoreNull(this, predicate)

object UtilKIterable {

    /**
     * 判断符合条件的元素是否在Collection中
     */
    @JvmStatic
    fun <T> containsBy(iterable: Iterable<T>, predicate: IA_BListener<T, Boolean>): Boolean {
        return getIndexFirst(iterable, predicate) != null
    }

    /**
     * 获取符合条件的元素在该Collection的位置
     */
    @JvmStatic
    fun <T> getIndexFirst(iterable: Iterable<T>, predicate: IA_BListener<T, Boolean>): Int? {
        val index = iterable.indexOf(iterable.find(predicate))
        return if (index == -1) null else index
    }

    /**
     * 将一个Collection的Item中的某个Element组合起来成一个新Collection
     */
    @JvmStatic
    fun <T, I> joinT2list(iterable: Iterable<T>, predicate: IA_BListener<T, I>): List<I> {
        return joinT2list(iterable, ArrayList(), predicate)
    }

    /**
     * 将一个Collection的Item中的某个Element组合起来成一个新Collection
     */
    @JvmStatic
    fun <T, I, C : MutableCollection<in I>> joinT2list(iterable: Iterable<T>, newCollection: C, predicate: IA_BListener<T, I>): C {
        for (element in iterable) if (!newCollection.contains(predicate(element))) newCollection.add(predicate(element))
        return newCollection
    }

    /**
     * 将一个Collection的Item中的某个Element组合起来成一个新Collection(忽略重复的)
     */
    @JvmStatic
    fun <T, I> joinT2listIgnoreRepeat(iterable: Iterable<T>, predicate: IA_BListener<T, I>): List<I> {
        return joinT2listIgnoreRepeat(iterable, ArrayList(), predicate)
    }

    /**
     * 将一个Collection的Item中的某个Element组合起来成一个新Collection(忽略重复的)
     */
    @JvmStatic
    fun <T, I, C : MutableCollection<in I>> joinT2listIgnoreRepeat(iterable: Iterable<T>, newCollection: C, predicate: IA_BListener<T, I>): C {
        for (element in iterable) newCollection.add(predicate(element))
        return newCollection
    }

    /**
     * 将一个Collection的Item中的某个Element组合起来成一个新Collection(忽略Null的)
     */
    @JvmStatic
    fun <T, I> joinT2listIgnoreNull(iterable: Iterable<T?>, predicate: IA_BListener<T?, I>): List<I> {
        return joinT2listIgnoreNull(iterable, ArrayList(), predicate)
    }

    /**
     * 将一个Collection的Item中的某个Element组合起来成一个新Collection(忽略Null的)
     */
    @JvmStatic
    fun <T, I, C : MutableCollection<in I>> joinT2listIgnoreNull(iterable: Iterable<T?>, newCollection: C, predicate: IA_BListener<T?, I>): C {
        for (element in iterable) newCollection.add(predicate(element))
        return newCollection
    }
}