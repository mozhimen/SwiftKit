package com.mozhimen.basick.elemk.android.os

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.mozhimen.basick.elemk.android.os.bases.BaseWeakCallbackHandler
import java.lang.ref.WeakReference
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * @ClassName MemorySafeHandler
 * @Description  * Memory safer implementation of android.os.Handler
 * Original implementation of Handlers always keeps hard reference to handler in queue of execution.
 * If you create anonymous handler and post delayed message into it, it will keep all self class
 * for that time in memory even if it could be cleaned.
 * This implementation is trickier, it will keep WeakReferences to runnables and messages,
 * and GC could collect them once WeakHandler instance is not referenced any more
 * @see Handler
 * Created by Dmytro Voronkevych on 17/06/2014.
 * git:https://github.com/badoo/android-weak-handler/blob/master/src/main/java/com/badoo/mobile/util/WeakHandler.java
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 22:50
 * @Version 1.0
 */
class MemorySafeHandler {
    private val _callback: Handler.Callback?// hard reference to Callback. We need to keep callback in memory
    private val _baseWeakCallbackHandler: BaseWeakCallbackHandler
    private val _lock: Lock = ReentrantLock()
    private val _runnables = ChainedRef(_lock, null)

    /**
     * 1 Default constructor associates this handler with the [Looper] for the
     * current thread.
     * 2 Constructor associates this handler with the [Looper] for the
     * current thread and takes a callback interface in which you can handle
     * messages.
     * If this thread does not have a looper, this handler won't be able to receive messages
     * so an exception is thrown.
     * @param callback The callback interface in which to handle messages, or null.
     */
//    @JvmOverloads
//    constructor(callback: Handler.Callback? = null) {
//        _callback = callback // Hard referencing body
//        _baseWeakCallbackHandler = BaseWeakCallbackHandler(WeakReference(callback)) // Weak referencing inside ExecHandler
//    }

    /**
     * Use the provided [Looper] instead of the default one and take a callback
     * interface in which to handle messages.
     * @param looper   The looper, must not be null.
     * @param callback The callback interface in which to handle messages, or null.
     */
    @JvmOverloads
    constructor(looper: Looper, callback: Handler.Callback? = null) {
        _callback = callback
        _baseWeakCallbackHandler = BaseWeakCallbackHandler(looper, WeakReference(callback))
    }

    /**
     * Causes the Runnable r to be added to the message queue.
     * The runnable will be run on the thread to which this handler is
     * attached.
     * @param runnable The Runnable that will be executed.
     * @return Returns true if the Runnable was successfully placed in to the
     * message queue.  Returns false on failure, usually because the
     * looper processing the message queue is exiting.
     */
    fun post(runnable: Runnable): Boolean {
        return _baseWeakCallbackHandler.post(wrapRunnable(runnable))
    }

    /**
     * Causes the Runnable r to be added to the message queue, to be run
     * at a specific time given by <var>uptimeMillis</var>.
     * **The time-base is [android.os.SystemClock.uptimeMillis].**
     * The runnable will be run on the thread to which this handler is attached.
     * @param runnable            The Runnable that will be executed.
     * @param uptimeMillis The absolute time at which the callback should run,
     * using the [android.os.SystemClock.uptimeMillis] time-base.
     * @return Returns true if the Runnable was successfully placed in to the
     * message queue.  Returns false on failure, usually because the
     * looper processing the message queue is exiting.  Note that a
     * result of true does not mean the Runnable will be processed -- if
     * the looper is quit before the delivery time of the message
     * occurs then the message will be dropped.
     */
    fun postAtTime(runnable: Runnable, uptimeMillis: Long): Boolean {
        return _baseWeakCallbackHandler.postAtTime(wrapRunnable(runnable), uptimeMillis)
    }

    /**
     * Causes the Runnable r to be added to the message queue, to be run
     * at a specific time given by <var>uptimeMillis</var>.
     * **The time-base is [android.os.SystemClock.uptimeMillis].**
     * The runnable will be run on the thread to which this handler is attached.
     * @param runnable            The Runnable that will be executed.
     * @param uptimeMillis The absolute time at which the callback should run,
     * using the [android.os.SystemClock.uptimeMillis] time-base.
     * @return Returns true if the Runnable was successfully placed in to the
     * message queue.  Returns false on failure, usually because the
     * looper processing the message queue is exiting.  Note that a
     * result of true does not mean the Runnable will be processed -- if
     * the looper is quit before the delivery time of the message
     * occurs then the message will be dropped.
     * @see android.os.SystemClock.uptimeMillis
     */
    fun postAtTime(runnable: Runnable, token: Any, uptimeMillis: Long): Boolean {
        return _baseWeakCallbackHandler.postAtTime(wrapRunnable(runnable), token, uptimeMillis)
    }

    /**
     * Causes the Runnable r to be added to the message queue, to be run
     * after the specified amount of time elapses.
     * The runnable will be run on the thread to which this handler
     * is attached.
     * @param runnable           The Runnable that will be executed.
     * @param delayMillis The delay (in milliseconds) until the Runnable
     * will be executed.
     * @return Returns true if the Runnable was successfully placed in to the
     * message queue.  Returns false on failure, usually because the
     * looper processing the message queue is exiting.  Note that a
     * result of true does not mean the Runnable will be processed --
     * if the looper is quit before the delivery time of the message
     * occurs then the message will be dropped.
     */
    fun postDelayed(runnable: Runnable, delayMillis: Long): Boolean {
        return _baseWeakCallbackHandler.postDelayed(wrapRunnable(runnable), delayMillis)
    }

    /**
     * Posts a message to an object that implements Runnable.
     * Causes the Runnable r to executed on the nextPage iteration through the
     * message queue. The runnable will be run on the thread to which this
     * handler is attached.
     * **This method is only for use in very special circumstances -- it
     * can easily starve the message queue, cause ordering problems, or have
     * other unexpected side-effects.**
     * @param runnable The Runnable that will be executed.
     * @return Returns true if the message was successfully placed in to the
     * message queue.  Returns false on failure, usually because the
     * looper processing the message queue is exiting.
     */
    fun postAtFrontOfQueue(runnable: Runnable): Boolean {
        return _baseWeakCallbackHandler.postAtFrontOfQueue(wrapRunnable(runnable))
    }

    /**
     * Remove any pending posts of Runnable r that are in the message queue.
     */
    fun removeCallbacks(runnable: Runnable) {
        val tempRunnable = _runnables.remove(runnable)
        if (tempRunnable != null) {
            _baseWeakCallbackHandler.removeCallbacks(tempRunnable)
        }
    }

    /**
     * Remove any pending posts of Runnable <var>r</var> with Object
     * <var>token</var> that are in the message queue.  If <var>token</var> is null,
     * all callbacks will be removed.
     */
    fun removeCallbacks(runnable: Runnable, token: Any) {
        val tempRunnable = _runnables.remove(runnable)
        if (tempRunnable != null) {
            _baseWeakCallbackHandler.removeCallbacks(tempRunnable, token)
        }
    }

    /**
     * Pushes a message onto the end of the message queue after all pending messages
     * before the current time. It will be received in callback,
     * in the thread attached to this handler.
     *
     * @return Returns true if the message was successfully placed in to the
     * message queue.  Returns false on failure, usually because the
     * looper processing the message queue is exiting.
     */
    fun sendMessage(msg: Message): Boolean {
        return _baseWeakCallbackHandler.sendMessage(msg)
    }

    /**
     * Sends a Message containing only the what value.
     *
     * @return Returns true if the message was successfully placed in to the
     * message queue.  Returns false on failure, usually because the
     * looper processing the message queue is exiting.
     */
    fun sendEmptyMessage(what: Int): Boolean {
        return _baseWeakCallbackHandler.sendEmptyMessage(what)
    }

    /**
     * Sends a Message containing only the what value, to be delivered
     * after the specified amount of time elapses.
     *
     * @return Returns true if the message was successfully placed in to the
     * message queue.  Returns false on failure, usually because the
     * looper processing the message queue is exiting.
     * @see .sendMessageDelayed
     */
    fun sendEmptyMessageDelayed(what: Int, delayMillis: Long): Boolean {
        return _baseWeakCallbackHandler.sendEmptyMessageDelayed(what, delayMillis)
    }

    /**
     * Sends a Message containing only the what value, to be delivered
     * at a specific time.
     *
     * @return Returns true if the message was successfully placed in to the
     * message queue.  Returns false on failure, usually because the
     * looper processing the message queue is exiting.
     * @see .sendMessageAtTime
     */
    fun sendEmptyMessageAtTime(what: Int, uptimeMillis: Long): Boolean {
        return _baseWeakCallbackHandler.sendEmptyMessageAtTime(what, uptimeMillis)
    }

    /**
     * Enqueue a message into the message queue after all pending messages
     * before (current time + delayMillis). You will receive it in
     * callback, in the thread attached to this handler.
     *
     * @return Returns true if the message was successfully placed in to the
     * message queue.  Returns false on failure, usually because the
     * looper processing the message queue is exiting.  Note that a
     * result of true does not mean the message will be processed -- if
     * the looper is quit before the delivery time of the message
     * occurs then the message will be dropped.
     */
    fun sendMessageDelayed(msg: Message, delayMillis: Long): Boolean {
        return _baseWeakCallbackHandler.sendMessageDelayed(msg, delayMillis)
    }

    /**
     * Enqueue a message into the message queue after all pending messages
     * before the absolute time (in milliseconds) <var>uptimeMillis</var>.
     * **The time-base is [android.os.SystemClock.uptimeMillis].**
     * You will receive it in callback, in the thread attached
     * to this handler.
     * @param uptimeMillis The absolute time at which the message should be
     * delivered, using the
     * [android.os.SystemClock.uptimeMillis] time-base.
     * @return Returns true if the message was successfully placed in to the
     * message queue.  Returns false on failure, usually because the
     * looper processing the message queue is exiting.  Note that a
     * result of true does not mean the message will be processed -- if
     * the looper is quit before the delivery time of the message
     * occurs then the message will be dropped.
     */
    fun sendMessageAtTime(msg: Message, uptimeMillis: Long): Boolean {
        return _baseWeakCallbackHandler.sendMessageAtTime(msg, uptimeMillis)
    }

    /**
     * Enqueue a message at the front of the message queue, to be processed on
     * the nextPage iteration of the message loop.  You will receive it in
     * callback, in the thread attached to this handler.
     * **This method is only for use in very special circumstances -- it
     * can easily starve the message queue, cause ordering problems, or have
     * other unexpected side-effects.**
     * @return Returns true if the message was successfully placed in to the
     * message queue.  Returns false on failure, usually because the
     * looper processing the message queue is exiting.
     */
    fun sendMessageAtFrontOfQueue(msg: Message): Boolean {
        return _baseWeakCallbackHandler.sendMessageAtFrontOfQueue(msg)
    }

    /**
     * Remove any pending posts of messages with code 'what' that are in the
     * message queue.
     */
    fun removeMessages(what: Int) {
        _baseWeakCallbackHandler.removeMessages(what)
    }

    /**
     * Remove any pending posts of messages with code 'what' and whose obj is
     * 'object' that are in the message queue.  If <var>object</var> is null,
     * all messages will be removed.
     */
    fun removeMessages(what: Int, obj: Any) {
        _baseWeakCallbackHandler.removeMessages(what, obj)
    }

    /**
     * Remove any pending posts of callbacks and sent messages whose
     * <var>obj</var> is <var>token</var>.  If <var>token</var> is null,
     * all callbacks and messages will be removed.
     */
    fun removeCallbacksAndMessages(token: Any) {
        _baseWeakCallbackHandler.removeCallbacksAndMessages(token)
    }

    /**
     * Check if there are any pending posts of messages with code 'what' in
     * the message queue.
     */
    fun hasMessages(what: Int): Boolean {
        return _baseWeakCallbackHandler.hasMessages(what)
    }

    /**
     * Check if there are any pending posts of messages with code 'what' and
     * whose obj is 'object' in the message queue.
     */
    fun hasMessages(what: Int, obj: Any): Boolean {
        return _baseWeakCallbackHandler.hasMessages(what, obj)
    }

    val looper: Looper
        get() = _baseWeakCallbackHandler.looper

    private fun wrapRunnable(runnable: Runnable): WeakRunnable {
        val hardRef = ChainedRef(_lock, runnable)
        _runnables.insertAfter(hardRef)
        return hardRef.wrapper
    }

    class WeakRunnable(
        private val _weakRunnable: WeakReference<Runnable>,
        private val _weakChainRef: WeakReference<ChainedRef>
    ) : Runnable {
        override fun run() {
            val runnable = _weakRunnable.get()
            val chainedRef = _weakChainRef.get()
            chainedRef?.remove()
            runnable?.run()
        }
    }

    class ChainedRef(var lock: Lock, val runnable: Runnable?) {
        var next: ChainedRef? = null
        var prev: ChainedRef? = null
        val wrapper: WeakRunnable = WeakRunnable(WeakReference(runnable), WeakReference(this))

        fun remove(): WeakRunnable {
            lock.lock()
            try {
                if (prev != null) {
                    prev!!.next = next
                }
                if (next != null) {
                    next!!.prev = prev
                }
                prev = null
                next = null
            } finally {
                lock.unlock()
            }
            return wrapper
        }

        fun insertAfter(candidate: ChainedRef) {
            lock.lock()
            try {
                if (next != null) {
                    next!!.prev = candidate
                }
                candidate.next = next
                next = candidate
                candidate.prev = this
            } finally {
                lock.unlock()
            }
        }

        fun remove(obj: Runnable): WeakRunnable? {
            lock.lock()
            try {
                var curr = next // Skipping head
                while (curr != null) {
                    if (curr.runnable === obj) { // We do comparison exactly how Handler does inside
                        return curr.remove()
                    }
                    curr = curr.next
                }
            } finally {
                lock.unlock()
            }
            return null
        }
    }
}
