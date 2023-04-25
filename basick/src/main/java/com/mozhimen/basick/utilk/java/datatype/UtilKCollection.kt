package com.mozhimen.basick.utilk.java.datatype

/**
 * @ClassName UtilKCollections
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/27 0:24
 * @Version 1.0
 */
object UtilKCollection {

    /**
     * 判断符合条件的元素是否在Collection中
     * @param iterable Iterable<T>
     * @param predicate Function1<T, Boolean>
     * @return Boolean
     */
    @JvmStatic
    fun <T> containsBy(iterable: Iterable<T>, predicate: (T) -> Boolean): Boolean {
        return getIndexFirst(iterable, predicate) != null
    }

    /**
     * 获取符合条件的元素在该Collection的位置
     * @param iterable Iterable<T>
     * @param predicate Function1<T, Boolean>
     * @return Int
     */
    @JvmStatic
    fun <T> getIndexFirst(iterable: Iterable<T>, predicate: (T) -> Boolean): Int? {
        val index = iterable.indexOf(iterable.find(predicate))
        return if (index == -1) null else index
    }

    /**
     * 将一个Collection的Item中的某个Element组合起来成一个新Collection
     * @param iterable Iterable<T>
     * @param predicate Function1<T, I>
     * @return List<I>
     */
    @JvmStatic
    fun <T, I> joinElement2List(iterable: Iterable<T>, predicate: (T) -> I): List<I> {
        return joinElement2List(iterable, ArrayList(), predicate)
    }

    /**
     * 将一个Collection的Item中的某个Element组合起来成一个新Collection
     * @param iterable Iterable<T>
     * @param newCollection C
     * @param predicate Function1<T, I>
     * @return C
     */
    @JvmStatic
    fun <T, I, C : MutableCollection<in I>> joinElement2List(iterable: Iterable<T>, newCollection: C, predicate: (T) -> I): C {
        for (element in iterable) if (!newCollection.contains(predicate(element))) newCollection.add(predicate(element))
        return newCollection
    }

    /**
     * 将一个Collection的Item中的某个Element组合起来成一个新Collection(忽略重复的)
     * @param iterable Iterable<T>
     * @param predicate Function1<T, I>
     * @return List<I>
     */
    @JvmStatic
    fun <T, I> joinElement2ListIgnoreRepeat(iterable: Iterable<T>, predicate: (T) -> I): List<I> {
        return joinElement2ListIgnoreRepeat(iterable, ArrayList(), predicate)
    }

    /**
     * 将一个Collection的Item中的某个Element组合起来成一个新Collection(忽略重复的)
     * @param iterable Iterable<T>
     * @param newCollection C
     * @param predicate Function1<T, I>
     * @return C
     */
    @JvmStatic
    fun <T, I, C : MutableCollection<in I>> joinElement2ListIgnoreRepeat(iterable: Iterable<T>, newCollection: C, predicate: (T) -> I): C {
        for (element in iterable) newCollection.add(predicate(element))
        return newCollection
    }

    /**
     * 将一个Collection的Item中的某个Element组合起来成一个新Collection(忽略Null的)
     * @param iterable Iterable<T?>
     * @param predicate Function1<T?, I>
     * @return List<I>
     */
    @JvmStatic
    fun <T, I> joinElement2ListIgnoreNull(iterable: Iterable<T?>, predicate: (T?) -> I): List<I> {
        return joinElement2ListIgnoreNull(iterable, ArrayList(), predicate)
    }

    /**
     * 将一个Collection的Item中的某个Element组合起来成一个新Collection(忽略Null的)
     * @param iterable Iterable<T?>
     * @param newCollection C
     * @param predicate Function1<T?, I>
     * @return C
     */
    @JvmStatic
    fun <T, I, C : MutableCollection<in I>> joinElement2ListIgnoreNull(iterable: Iterable<T?>, newCollection: C, predicate: (T?) -> I): C {
        for (element in iterable) newCollection.add(predicate(element))
        return newCollection
    }

    /**
     * map2Str
     * @param map Map<*, *>
     * @return String
     */
    @JvmStatic
    fun map2Str(map: Map<*, *>): String {
        if (map.isEmpty()) return "map is empty"
        val stringBuilder = StringBuilder()
        stringBuilder.append("\n").append("{").append("\n").append("\t")
        val iterator: Iterator<*> = map.entries.iterator()
        while (iterator.hasNext()) {
            val (key, value) = iterator.next() as Map.Entry<*, *>
            stringBuilder.append(String.format("\t%1\$s : %2\$s", key.toString(), value.toString()))
            stringBuilder.append("\n")
        }
        stringBuilder.append("}")
        return stringBuilder.toString()
    }

    /**
     * list2Str
     * @param list List<*>
     * @return String
     */
    @JvmStatic
    fun list2Str(list: List<*>): String {
        if (list.isEmpty()) return "list is empty"
        val stringBuilder = StringBuilder()
        stringBuilder.append("\n").append("{\n ")
        for (obj in list) {
            if (obj is List<*>) stringBuilder.append(list2Str(obj))
            else stringBuilder.append(obj.toString()).append(" ,\n ")
        }
        stringBuilder.append("}")
        return stringBuilder.toString()
    }
}