package com.mozhimen.basick.elemk.android.os

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.annotation.VisibleForTesting
import com.mozhimen.basick.elemk.java.lang.NodeRunnable
import com.mozhimen.basick.elemk.java.lang.NodeRunnableWrapper
import java.lang.ref.WeakReference
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * @ClassName WeakRefHandler
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/14 23:16
 * @Version 1.0
 */
/**
 * Memory safer implementation of android.os.Handler
 * <p/>
 * Original implementation of Handlers always keeps hard reference to handler in queue of execution.
 * If you create anonymous handler and post delayed message into it, it will keep all parent class
 * for that time in memory even if it could be cleaned.
 * <p/>
 * This implementation is trickier, it will keep WeakReferences to runnables and messages,
 * and GC could collect them once WeakHandler instance is not referenced any more
 * <p/>
 *
 * @see android.os.Handler
 *
 * Created by Dmytro Voronkevych on 17/06/2014.
 */
interface IWeakRefHandler {
    /**
     * Causes the Runnable r to be added to the message queue.
     * The runnable will be run on the thread to which this handler is
     * attached.
     *
     * @param r The Runnable that will be executed.
     *
     * @return Returns true if the Runnable was successfully placed in to the
     *         message queue.  Returns false on failure, usually because the
     *         looper processing the message queue is exiting.
     */
    fun post(runnable: Runnable): Boolean

    /**
     * Causes the Runnable r to be added to the message queue, to be run
     * at a specific time given by <var>uptimeMillis</var>.
     * <b>The time-base is {@link android.os.SystemClock#uptimeMillis}.</b>
     * The runnable will be run on the thread to which this handler is attached.
     *
     * @param r The Runnable that will be executed.
     * @param uptimeMillis The absolute time at which the callback should run,
     *         using the {@link android.os.SystemClock#uptimeMillis} time-base.
     *
     * @return Returns true if the Runnable was successfully placed in to the
     *         message queue.  Returns false on failure, usually because the
     *         looper processing the message queue is exiting.  Note that a
     *         result of true does not mean the Runnable will be processed -- if
     *         the looper is quit before the delivery time of the message
     *         occurs then the message will be dropped.
     */
    fun postAtTime(runnable: Runnable, uptimeMillis: Long): Boolean

    /**
     * Causes the Runnable r to be added to the message queue, to be run
     * at a specific time given by <var>uptimeMillis</var>.
     * <b>The time-base is {@link android.os.SystemClock#uptimeMillis}.</b>
     * The runnable will be run on the thread to which this handler is attached.
     *
     * @param r The Runnable that will be executed.
     * @param uptimeMillis The absolute time at which the callback should run,
     *         using the {@link android.os.SystemClock#uptimeMillis} time-base.
     *
     * @return Returns true if the Runnable was successfully placed in to the
     *         message queue.  Returns false on failure, usually because the
     *         looper processing the message queue is exiting.  Note that a
     *         result of true does not mean the Runnable will be processed -- if
     *         the looper is quit before the delivery time of the message
     *         occurs then the message will be dropped.
     *
     * @see android.os.SystemClock#uptimeMillis
     */
    fun postAtTime(runnable: Runnable, token: Any?, uptimeMillis: Long): Boolean

    /**
     * Causes the Runnable r to be added to the message queue, to be run
     * after the specified amount of time elapses.
     * The runnable will be run on the thread to which this handler
     * is attached.
     *
     * @param r The Runnable that will be executed.
     * @param delayMillis The delay (in milliseconds) until the Runnable
     *        will be executed.
     *
     * @return Returns true if the Runnable was successfully placed in to the
     *         message queue.  Returns false on failure, usually because the
     *         looper processing the message queue is exiting.  Note that a
     *         result of true does not mean the Runnable will be processed --
     *         if the looper is quit before the delivery time of the message
     *         occurs then the message will be dropped.
     */
    fun postDelayed(runnable: Runnable, delayMillis: Long): Boolean

    /**
     * Posts a message to an object that implements Runnable.
     * Causes the Runnable r to executed on the next iteration through the
     * message queue. The runnable will be run on the thread to which this
     * handler is attached.
     * <b>This method is only for use in very special circumstances -- it
     * can easily starve the message queue, cause ordering problems, or have
     * other unexpected side-effects.</b>
     *
     * @param r The Runnable that will be executed.
     *
     * @return Returns true if the message was successfully placed in to the
     *         message queue.  Returns false on failure, usually because the
     *         looper processing the message queue is exiting.
     */
    fun postAtFrontOfQueue(runnable: Runnable): Boolean

    /**
     * Remove any pending posts of Runnable r that are in the message queue.
     */
    fun removeCallbacks(runnable: Runnable)

    /**
     * Remove any pending posts of Runnable <var>r</var> with Object
     * <var>token</var> that are in the message queue.  If <var>token</var> is null,
     * all callbacks will be removed.
     */
    fun removeCallbacks(runnable: Runnable, token: Any?)

    /**
     * Pushes a message onto the end of the message queue after all pending messages
     * before the current time. It will be received in callback,
     * in the thread attached to this handler.
     *
     * @return Returns true if the message was successfully placed in to the
     *         message queue.  Returns false on failure, usually because the
     *         looper processing the message queue is exiting.
     */
    fun sendMessage(msg: Message?): Boolean

    /**
     * Sends a Message containing only the what value.
     *
     * @return Returns true if the message was successfully placed in to the
     *         message queue.  Returns false on failure, usually because the
     *         looper processing the message queue is exiting.
     */
    fun sendEmptyMessage(what: Int): Boolean

    /**
     * Sends a Message containing only the what value, to be delivered
     * after the specified amount of time elapses.
     * @see #sendMessageDelayed(android.os.Message, long)
     *
     * @return Returns true if the message was successfully placed in to the
     *         message queue.  Returns false on failure, usually because the
     *         looper processing the message queue is exiting.
     */
    fun sendEmptyMessageDelayed(what: Int, delayMillis: Long): Boolean

    /**
     * Sends a Message containing only the what value, to be delivered
     * at a specific time.
     * @see #sendMessageAtTime(android.os.Message, long)
     *
     * @return Returns true if the message was successfully placed in to the
     *         message queue.  Returns false on failure, usually because the
     *         looper processing the message queue is exiting.
     */
    fun sendEmptyMessageAtTime(what: Int, uptimeMillis: Long): Boolean

    /**
     * Enqueue a message into the message queue after all pending messages
     * before (current time + delayMillis). You will receive it in
     * callback, in the thread attached to this handler.
     *
     * @return Returns true if the message was successfully placed in to the
     *         message queue.  Returns false on failure, usually because the
     *         looper processing the message queue is exiting.  Note that a
     *         result of true does not mean the message will be processed -- if
     *         the looper is quit before the delivery time of the message
     *         occurs then the message will be dropped.
     */
    fun sendMessageDelayed(msg: Message?, delayMillis: Long): Boolean

    /**
     * Enqueue a message into the message queue after all pending messages
     * before the absolute time (in milliseconds) <var>uptimeMillis</var>.
     * <b>The time-base is {@link android.os.SystemClock#uptimeMillis}.</b>
     * You will receive it in callback, in the thread attached
     * to this handler.
     *
     * @param uptimeMillis The absolute time at which the message should be
     *         delivered, using the
     *         {@link android.os.SystemClock#uptimeMillis} time-base.
     *
     * @return Returns true if the message was successfully placed in to the
     *         message queue.  Returns false on failure, usually because the
     *         looper processing the message queue is exiting.  Note that a
     *         result of true does not mean the message will be processed -- if
     *         the looper is quit before the delivery time of the message
     *         occurs then the message will be dropped.
     */
    fun sendMessageAtTime(msg: Message?, uptimeMillis: Long): Boolean

    /**
     * Enqueue a message at the front of the message queue, to be processed on
     * the next iteration of the message loop.  You will receive it in
     * callback, in the thread attached to this handler.
     * <b>This method is only for use in very special circumstances -- it
     * can easily starve the message queue, cause ordering problems, or have
     * other unexpected side-effects.</b>
     *
     * @return Returns true if the message was successfully placed in to the
     *         message queue.  Returns false on failure, usually because the
     *         looper processing the message queue is exiting.
     */
    fun sendMessageAtFrontOfQueue(msg: Message?): Boolean

    /**
     * Remove any pending posts of messages with code 'what' that are in the
     * message queue.
     */
    fun removeMessages(what: Int)

    /**
     * Remove any pending posts of messages with code 'what' and whose obj is
     * 'object' that are in the message queue.  If <var>object</var> is null,
     * all messages will be removed.
     */
    fun removeMessages(what: Int, obj: Any?)

    /**
     * Remove any pending posts of callbacks and sent messages whose
     * <var>obj</var> is <var>token</var>.  If <var>token</var> is null,
     * all callbacks and messages will be removed.
     */
    fun removeCallbacksAndMessages(token: Any?)

    /**
     * Check if there are any pending posts of messages with code 'what' in
     * the message queue.
     */
    fun hasMessages(what: Int): Boolean

    /**
     * Check if there are any pending posts of messages with code 'what' and
     * whose obj is 'object' in the message queue.
     */
    fun hasMessages(what: Int, `object`: Any?): Boolean
    fun getLooper(): Looper
}

@Suppress("unused")
class WeakRefHandler : IWeakRefHandler {
    private val _callback: Handler.Callback?
    private val _weakCallbackRefHandler: WeakCallbackRefHandler
    private val _reentrantLock: Lock = ReentrantLock()

    @VisibleForTesting
    private val _nodeRunnableWrappers = NodeRunnableWrapper(_reentrantLock, null)

    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Default constructor associates this handler with the {@link Looper} for the
     * current thread.
     *
     * If this thread does not have a looper, this handler won't be able to receive messages
     * so an exception is thrown.
     */
    constructor() {
        _callback = null
        _weakCallbackRefHandler = WeakCallbackRefHandler()
    }

    /**
     * Constructor associates this handler with the {@link Looper} for the
     * current thread and takes a callback interface in which you can handle
     * messages.
     *
     * If this thread does not have a looper, this handler won't be able to receive messages
     * so an exception is thrown.
     *
     * @param callback The callback interface in which to handle messages, or null.
     */
    constructor(callback: Handler.Callback?) {
        _callback = callback // Hard referencing body
        _weakCallbackRefHandler = WeakCallbackRefHandler(WeakReference(callback)) // Weak referencing inside ExecHandler
    }

    /**
     * Use the provided {@link Looper} instead of the default one.
     *
     * @param looper The looper, must not be null.
     */
    constructor(looper: Looper) {
        _callback = null
        _weakCallbackRefHandler = WeakCallbackRefHandler(looper)
    }

    /**
     * Use the provided {@link Looper} instead of the default one and take a callback
     * interface in which to handle messages.
     *
     * @param looper The looper, must not be null.
     * @param callback The callback interface in which to handle messages, or null.
     */
    constructor(looper: Looper, callback: Handler.Callback) {
        _callback = callback
        _weakCallbackRefHandler = WeakCallbackRefHandler(WeakReference(callback), looper)
    }

    ////////////////////////////////////////////////////////////////////////////////

    override fun post(runnable: Runnable): Boolean {
        return _weakCallbackRefHandler.post(wrapRunnable(runnable))
    }

    override fun postAtTime(runnable: Runnable, uptimeMillis: Long): Boolean {
        return _weakCallbackRefHandler.postAtTime(wrapRunnable(runnable), uptimeMillis)
    }

    override fun postAtTime(runnable: Runnable, token: Any?, uptimeMillis: Long): Boolean {
        return _weakCallbackRefHandler.postAtTime(wrapRunnable(runnable), token, uptimeMillis)
    }

    override fun postDelayed(runnable: Runnable, delayMillis: Long): Boolean {
        return _weakCallbackRefHandler.postDelayed(wrapRunnable(runnable), delayMillis)
    }

    override fun postAtFrontOfQueue(runnable: Runnable): Boolean {
        return _weakCallbackRefHandler.postAtFrontOfQueue(wrapRunnable(runnable))
    }

    override fun removeCallbacks(runnable: Runnable) {
        val nodeRunnable = _nodeRunnableWrappers.remove(runnable)
        if (nodeRunnable != null) {
            _weakCallbackRefHandler.removeCallbacks(nodeRunnable)
        }
    }

    override fun removeCallbacks(runnable: Runnable, token: Any?) {
        val nodeRunnable = _nodeRunnableWrappers.remove(runnable)
        if (nodeRunnable != null) {
            _weakCallbackRefHandler.removeCallbacks(nodeRunnable, token)
        }
    }

    override fun sendMessage(msg: Message?): Boolean {
        return _weakCallbackRefHandler.sendMessage(msg!!)
    }

    override fun sendEmptyMessage(what: Int): Boolean {
        return _weakCallbackRefHandler.sendEmptyMessage(what)
    }

    override fun sendEmptyMessageDelayed(what: Int, delayMillis: Long): Boolean {
        return _weakCallbackRefHandler.sendEmptyMessageDelayed(what, delayMillis)
    }

    override fun sendEmptyMessageAtTime(what: Int, uptimeMillis: Long): Boolean {
        return _weakCallbackRefHandler.sendEmptyMessageAtTime(what, uptimeMillis)
    }

    override fun sendMessageDelayed(msg: Message?, delayMillis: Long): Boolean {
        return _weakCallbackRefHandler.sendMessageDelayed(msg!!, delayMillis)
    }

    override fun sendMessageAtTime(msg: Message?, uptimeMillis: Long): Boolean {
        return _weakCallbackRefHandler.sendMessageAtTime(msg!!, uptimeMillis)
    }

    override fun sendMessageAtFrontOfQueue(msg: Message?): Boolean {
        return _weakCallbackRefHandler.sendMessageAtFrontOfQueue(msg!!)
    }

    override fun removeMessages(what: Int) {
        _weakCallbackRefHandler.removeMessages(what)
    }

    override fun removeMessages(what: Int, obj: Any?) {
        _weakCallbackRefHandler.removeMessages(what, obj)
    }

    override fun removeCallbacksAndMessages(token: Any?) {
        _weakCallbackRefHandler.removeCallbacksAndMessages(token)
    }

    override fun hasMessages(what: Int): Boolean {
        return _weakCallbackRefHandler.hasMessages(what)
    }

    override fun hasMessages(what: Int, `object`: Any?): Boolean {
        return _weakCallbackRefHandler.hasMessages(what, `object`)
    }

    override fun getLooper(): Looper {
        return _weakCallbackRefHandler.looper
    }

    private fun wrapRunnable(runnable: Runnable): NodeRunnable {
        val wrapper = NodeRunnableWrapper(_reentrantLock, runnable)
        _nodeRunnableWrappers.insert(wrapper)
        return wrapper.nodeRunnable
    }
}