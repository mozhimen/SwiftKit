package com.mozhimen.basicsk.utilk

/**
 * @ClassName StackTraceUtil
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 18:42
 * @Version 1.0
 */
object UtilKStackTrace {
    /**
     * Get the real stack trace and then crop it with a max depth.
     *
     * @param stackTrace the full stack trace
     * @param maxDepth   the max depth of real stack trace that will be cropped, 0 means no limitation
     * @return the cropped real stack trace
     */
    fun getCroppedRealStackTrack(
        stackTrace: Array<StackTraceElement?>,
        ignoredPackage: String,
        maxDepth: Int
    ): Array<StackTraceElement?> {
        return cropStackTrace(
            getRealStackTrack(
                stackTrace,
                ignoredPackage
            ), maxDepth
        )
    }

    /**
     * Get the real stack trace, all elements that come from XLog library would be dropped.
     * 获取除忽略包名之外的堆栈信息
     * @param stackTrace the full stack trace
     * @return the real stack trace, all elements come from system and library user
     */
    fun getRealStackTrack(
        stackTrace: Array<StackTraceElement?>,
        ignorePackage: String?
    ): Array<StackTraceElement?> {
        var ignoreDepth = 0
        val allDepth = stackTrace.size
        require(allDepth > 0) { "stackTrace's size is 0" }
        var className: String
        for (i in allDepth - 1 downTo 0) {
            className = stackTrace[i]!!.className
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ignoreDepth = i + 1
                break
            }
        }
        val realDepth = allDepth - ignoreDepth
        val realStack = arrayOfNulls<StackTraceElement>(realDepth)
        System.arraycopy(stackTrace, ignoreDepth, realStack, 0, realDepth)
        return realStack
    }

    /**
     * 裁剪堆栈信息 Crop the stack trace with a max depth.
     *
     * @param callStack the original stack trace
     * @param maxDepth  the max depth of real stack trace that will be cropped,
     *                  0 means no limitation
     * @return the cropped stack trace
     */
    fun cropStackTrace(
        callStack: Array<StackTraceElement?>,
        maxDepth: Int
    ): Array<StackTraceElement?> {
        var realDepth = callStack.size
        if (maxDepth > 0) {
            realDepth = maxDepth.coerceAtMost(realDepth)
        }
        val realStack = arrayOfNulls<StackTraceElement>(realDepth)
        System.arraycopy(callStack, 0, realStack, 0, realDepth)
        return realStack
    }
}