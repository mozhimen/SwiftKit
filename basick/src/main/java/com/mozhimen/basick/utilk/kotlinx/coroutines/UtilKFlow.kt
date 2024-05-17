package com.mozhimen.basick.utilk.kotlinx.coroutines

import android.view.View
import android.widget.EditText
import com.mozhimen.basick.elemk.android.view.commons.ITextWatcher
import com.mozhimen.basick.elemk.commons.ISuspendA_Listener
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * @ClassName UtilKFlow
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/4 17:29
 * @Version 1.0
 */
fun View.getViewClickFlow(): Flow<Unit> =
    UtilKFlow.getViewClickFlow(this)

fun EditText.getEditTextChangeFlow(): Flow<CharSequence> =
    UtilKFlow.getEditTextChangeFlow(this)

fun <T> Flow<T>.throttleFirst(thresholdMillis: Long): Flow<T> =
    UtilKFlow.throttleFirst(this, thresholdMillis)

fun <T> Flow<T>.batch_ofTime(maxMillis: Int): Flow<List<T>> =
    UtilKFlow.batch_ofTime(this, maxMillis)

fun <T> Flow<T>.batch_ofSizeTime(maxSize: Int, maxMillis: Int): Flow<List<T>> =
    UtilKFlow.batch_ofSizeTime(this, maxSize, maxMillis)

suspend fun <T> Flow<T>.collectSafe(block: ISuspendA_Listener<T>) {
    UtilKFlow.collectSafe(this, block)
}

suspend fun <T> Flow<T>.collectSafe(onGenerate: ISuspendA_Listener<T>, onError: ISuspendA_Listener<Throwable>) {
    UtilKFlow.collectSafe(this, onGenerate, onError)
}

/////////////////////////////////////////////////////////////////////////////////////

object UtilKFlow {
    @JvmStatic
    fun getSearchFlow(str: String, scope: CoroutineScope, block: suspend CoroutineScope.(String) -> List<String>) =
        flow { emit(scope.block(str)) }

    @JvmStatic
    fun getViewClickFlow(view: View): Flow<Unit> = callbackFlow {
        view.setOnClickListener { this.trySend(Unit).isSuccess }
        awaitClose { view.setOnClickListener(null) }
    }

    @JvmStatic
    fun getEditTextChangeFlow(editText: EditText): Flow<CharSequence> = callbackFlow {
        val textWatcher = object : ITextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {// 在文本变化后向流发射数据
                s?.let { this@callbackFlow.trySend(it).isSuccess }
            }
        }
        editText.addTextChangedListener(textWatcher) // 设置输入框监听器
        awaitClose { editText.removeTextChangedListener(textWatcher) } // 阻塞以保证流一直运行
    }

    @JvmStatic
    fun <T> throttleFirst(flow: Flow<T>, thresholdMillis: Long): Flow<T> = flow {
        var lastTime = 0L // 上次发射数据的时间
        flow.collect { upstream ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime > thresholdMillis) {// 时间差超过阈值则发送数据并记录时间
                lastTime = currentTime
                emit(upstream)
            }
        }
    }

    @JvmStatic
    fun <T> batch_ofTime(flow: Flow<T>, maxMillis: Int): Flow<List<T>> =
        batch_ofSizeTime(flow, Int.MAX_VALUE, maxMillis)

    @JvmStatic
    fun <T> batch_ofSizeTime(flow: Flow<T>, maxSize: Int, maxMillis: Int): Flow<List<T>> = flow {
        val batch = mutableListOf<T>()
        var lastEmission = System.currentTimeMillis()

        flow.collect { value ->
            batch.add(value)
            if (batch.size >= maxSize || System.currentTimeMillis() > lastEmission + maxMillis) {
                emit(batch.toList())
                batch.clear()
                lastEmission = System.currentTimeMillis()
            }
        }

        if (batch.isNotEmpty()) emit(batch)
    }

    ///////////////////////////////////////////////////////////////////////////

    @JvmStatic
    suspend fun <T> collectSafe(flow: Flow<T>, block: ISuspendA_Listener<T>) {
        collectSafe(flow, block, UtilKLogWrapper::e)
    }

    @JvmStatic
    suspend fun <T> collectSafe(flow: Flow<T>, onGenerate: ISuspendA_Listener<T>, onError: ISuspendA_Listener<Throwable>) {
        flow
            .catch {
                UtilKLogWrapper.e(it)
            }
            .collect {
                try {
                    onGenerate(it)
                } catch (e: Throwable) {
                    onError(e)
                }
            }
    }


}