package com.mozhimen.basick.utilk.java.util

import android.text.TextUtils
import android.util.Log
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.org.json.UtilKJson
import com.mozhimen.basick.utilk.java.lang.UtilKCurrentThread
import java.util.logging.Logger

/**
 * @ClassName StackTraceUtil
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 18:42
 * @Version 1.0
 */
object UtilKStackTrace : BaseUtilK() {

    /**
     * 获取真正的堆栈跟踪，然后用最大深度裁剪它。
     * @param stackTrace Array<StackTraceElement?> 完整的堆栈跟踪
     * @param ignoredPackage String
     * @param maxDepth Int 将被裁剪的真实堆栈跟踪的最大深度，O表示没有限制
     * @return Array<StackTraceElement?> 裁剪后的真实堆栈跟踪
     */
    @JvmStatic
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
    @JvmStatic
    @Throws(Exception::class)
    fun getRealStackTrack(
        stackTrace: Array<StackTraceElement?>,
        ignorePackage: String?
    ): Array<StackTraceElement?> {
        var ignoreDepth = 0
        val allDepth = stackTrace.size
        require(allDepth > 0) { "$TAG stackTrace's size is 0" }
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
    @JvmStatic
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

    @JvmStatic
    fun getCurrentStackTrace(clazz: Class<*>): StackTraceElement? {
        val stackTrace = UtilKCurrentThread.getStackTrace()
        var stackOffset = getStackTraceOffset(stackTrace, clazz)
        if (stackOffset == -1) {
            stackOffset = getStackTraceOffset(stackTrace, Logger::class.java)
            if (stackOffset == -1) {
                stackOffset = getStackTraceOffset(stackTrace, Log::class.java)
                if (stackOffset == -1) {
                    return null
                }
            }
        }
        return stackTrace[stackOffset]
    }

    @JvmStatic
    fun getStackTraceOffset(stackTraceElements: Array<StackTraceElement>, clazz: Class<*>): Int {
        var logIndex = -1
        for (i in stackTraceElements.indices) {
            val element = stackTraceElements[i]
            val tClass = element.className
            if (TextUtils.equals(tClass, clazz.name)) {
                logIndex = i
            } else {
                if (logIndex > -1) break
            }
        }
        if (logIndex != -1) {
            logIndex++
            if (logIndex >= stackTraceElements.size) {
                logIndex = stackTraceElements.size - 1
            }
        }
        return logIndex
    }

    @JvmStatic
    fun getStackTraceInfo(msg: String): String {
        val element = getCurrentStackTrace(this::class.java)
        var clazzName = "unknown"
        var methodName = "unknown"
        var lineNumber = -1
        if (element != null) {
            clazzName = element.fileName
            methodName = element.methodName
            lineNumber = element.lineNumber
        }
        return "  ($clazzName:$lineNumber) #$methodName: \n${UtilKJson.wrapJsonStr(msg)}"
    }
}