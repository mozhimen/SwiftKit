package com.mozhimen.basick.elemk.commons

/**
 * @ClassName IApplicable
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/14
 * @Version 1.0
 */
interface IApplicable<K, T> {
    fun applyFor(key: K?): T?
}