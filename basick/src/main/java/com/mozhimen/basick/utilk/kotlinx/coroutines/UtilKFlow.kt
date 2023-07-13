package com.mozhimen.basick.utilk.kotlinx.coroutines

import android.text.Editable
import android.view.View
import android.widget.EditText
import com.mozhimen.basick.elemk.android.view.bases.BaseTextWatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

/**
 * @ClassName UtilKFlow
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/4 17:29
 * @Version 1.0
 */
fun <T> Flow<T>.throttleFirst(thresholdMillis: Long): Flow<T> =
    UtilKFlow.throttleFirst(this, thresholdMillis)

fun View.asViewClickFlow(): Flow<Unit> =
    UtilKFlow.asViewClickFlow(this)

fun EditText.asEditTextChangeFlow(): Flow<CharSequence> =
    UtilKFlow.asEditTextChangeFlow(this)

object UtilKFlow {
    @JvmStatic
    fun asViewClickFlow(view: View): Flow<Unit> = callbackFlow {
        view.setOnClickListener { this.trySend(Unit).isSuccess }
        awaitClose { view.setOnClickListener(null) }
    }

    @JvmStatic
    fun asEditTextChangeFlow(editText: EditText): Flow<CharSequence> = callbackFlow {
        val textWatcher = object : BaseTextWatcher() {
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
}