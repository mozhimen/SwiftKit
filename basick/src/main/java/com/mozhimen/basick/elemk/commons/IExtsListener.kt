package com.mozhimen.basick.elemk.commons

/**
 * @ClassName IExtsListener
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/7/4 21:44
 * @Version 1.0
 */
typealias IExts_Listener<E> = E.() -> Unit
typealias IExts_TListener<E, A> = E.() -> A
