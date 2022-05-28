package com.mozhimen.basick.utilk

/**
 * @ClassName StackTraceUtil
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 18:42
 * @Version 1.0
 */
object UtilKStackTrace {

    /**
     * 获取真正的堆栈跟踪，然后用最大深度裁剪它。
     * @param stackTrace Array<StackTraceElement?> 完整的堆栈跟踪
     * @param ignoredPackage String
     * @param maxDepth Int 将被裁剪的真实堆栈跟踪的最大深度，O表示没有限制
     * @return Array<StackTraceElement?> 裁剪后的真实堆栈跟踪
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
     * 获取真正的堆栈跟踪，所有来自XLog库的元素都将被删除
     * 获取除忽略包名之外的堆栈信息
     * @param stackTrace Array<StackTraceElement?> 完整的堆栈跟踪
     * @param ignorePackage String?
     * @return Array<StackTraceElement?> 真正的堆栈跟踪, 所有元素都来自system和library user
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
     * 使用最大深度裁剪堆栈跟踪.
     * @param callStack Array<StackTraceElement?> 原始堆栈跟踪
     * @param maxDepth Int 将被裁剪的真实堆栈跟踪的最大深度, O表示没有限制
     * @return Array<StackTraceElement?> 裁剪后的堆栈跟踪
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