package com.mozhimen.basick.elemk.commons

/**
 * @ClassName IReturnable
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/14
 * @Version 1.0
 */
interface IReturnable<T> {
    fun giveBack(obj: T?)
}