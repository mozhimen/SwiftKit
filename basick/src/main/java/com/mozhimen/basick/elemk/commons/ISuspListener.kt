package com.mozhimen.basick.elemk.commons


/**
 * @ClassName IListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 14:50
 * @Version 1.0
 */
typealias ISusp_Listener = suspend () -> Unit
typealias ISusp_AListener<A> = suspend () -> A
typealias ISuspA_Listener<A> = suspend (a: A) -> Unit
typealias ISuspAA_Listener<A> = suspend (a1: A, a2: A) -> Unit
typealias ISuspAB_Listener<A, B> = suspend (a: A, b: B) -> Unit
