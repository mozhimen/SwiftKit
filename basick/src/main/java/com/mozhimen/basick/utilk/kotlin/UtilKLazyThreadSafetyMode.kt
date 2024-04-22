package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKLazyThreadSafetyMode
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/26
 * @Version 1.0
 */
object UtilKLazyThreadSafetyMode {
    /**
     * NONE
     * 非线程安全 使用此方式，需要注意在线程安全的情况下调用，否则多线程下很可能造成多个初始化变量，导致不同的线程初始时调用的对象甚至不一致，造成逻辑问题。但其实对于 Android 开发而言，这种反而是比较常见的用途，与我们打交道的往往都是主线程，比
     * 多线程调用下可能会多次初始化，导致逻辑异常。
     * 没有并发场景时，性能最好。
     */
    @JvmStatic
    fun getNONE(): LazyThreadSafetyMode =
        LazyThreadSafetyMode.NONE

    /**
     * SYNCHRONIZED
     * 线程安全 比如有某个变量，可能会被多个线程同时调用，而且你不接受初始化函数可能会调用多次，所以可以使用此方法，但需要注意的是，因为get时其内部使用了对象锁，所以在多线程情况下 第一次 调用时，很可能会阻塞我们的其他线程，比如子线程和主线程同时调用，此时子线程先调用到，那主线程此时就会被阻塞，虽然这个时机其实一般而言很短(主要取决于内部逻辑)，但也仍需要注意。
     * 整个初始化过程都被 synchronized 包围，因此多线程下初始化函数不会执行多次，但首次获取到锁的线程可能会阻塞其他线程(对于主线程也要使用这个属性的场景，需要额外注意)。一般情况下 synchronized 也不重，可以放心使用，但在锁竞争激烈，锁持有时间长的时候，会升级到重量级锁，经历用户态和内核态的切换，损耗性能。
     * 如果没有并发操作，使用 synchronized 反而会多一点点加锁的消耗。
     */
    @JvmStatic
    fun getSYNCHRONIZED(): LazyThreadSafetyMode =
        LazyThreadSafetyMode.SYNCHRONIZED

    /**
     * PUBLICATION
     * 线程安全 但是相比前者，你可以接受 你的初始化函数可能被调用多次 ，但并不影响你最终的使用，因为只有第一个初始化结果的才会被返回，并不影响你的逻辑，所以一般情况下，如果不在意上述问题，我们可以尽量采用这种方式去编写线程安全代码。以避免调用get加锁导致初始化性能损耗。
     * 多线程下初始化函数可能会被执行多次 ，但只有第一个初始化结果会被实际赋值，不影响使用。初始化函数不会阻塞其他线程，只有在赋值时才使用 CAS 机制。
     * 这种方式虽然避免了 synchronized 同步，但可能会增加额外的工作量(初始化函数执行多次)。实际工作中我基本上没用过这种方式，但 Kotlin 提供了这个机制，我们在某些场景可以去权衡具体该使用谁，毕竟 synchronized 有膨胀的风险。
     */
    @JvmStatic
    fun getPUBLICATION(): LazyThreadSafetyMode =
        LazyThreadSafetyMode.PUBLICATION
}