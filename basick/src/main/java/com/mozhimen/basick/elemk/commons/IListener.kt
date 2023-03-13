package com.mozhimen.basick.elemk.commons


/**
 * @ClassName IListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 14:50
 * @Version 1.0
 */
typealias IListener = () -> Unit;
typealias IValueListener<T> = (T) -> Unit;
