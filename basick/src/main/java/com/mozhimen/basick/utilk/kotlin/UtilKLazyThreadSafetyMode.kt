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
     */
    @JvmStatic
    fun getNONE(): LazyThreadSafetyMode =
        LazyThreadSafetyMode.NONE

    /**
     * SYNCHRONIZED
     * 线程安全 比如有某个变量，可能会被多个线程同时调用，而且你不接受初始化函数可能会调用多次，所以可以使用此方法，但需要注意的是，因为get时其内部使用了对象锁，所以在多线程情况下 第一次 调用时，很可能会阻塞我们的其他线程，比如子线程和主线程同时调用，此时子线程先调用到，那主线程此时就会被阻塞，虽然这个时机其实一般而言很短(主要取决于内部逻辑)，但也仍需要注意。
     */
    @JvmStatic
    fun getSYNCHRONIZED(): LazyThreadSafetyMode =
        LazyThreadSafetyMode.SYNCHRONIZED

    /**
     * PUBLICATION
     * 线程安全 但是相比前者，你可以接受 你的初始化函数可能被调用多次 ，但并不影响你最终的使用，因为只有第一个初始化结果的才会被返回，并不影响你的逻辑，所以一般情况下，如果不在意上述问题，我们可以尽量采用这种方式去编写线程安全代码。以避免调用get加锁导致初始化性能损耗。
     */
    @JvmStatic
    fun getPUBLICATION(): LazyThreadSafetyMode =
        LazyThreadSafetyMode.PUBLICATION
}