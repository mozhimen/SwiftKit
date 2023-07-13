package com.mozhimen.basick.elemk.commons

/**
 * @ClassName IExtsListener
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/7/4 21:44
 * @Version 1.0
 */
typealias IExtension_Listener<E> = E.() -> Unit
typealias IExtension_AListener<E, A> = E.() -> A
