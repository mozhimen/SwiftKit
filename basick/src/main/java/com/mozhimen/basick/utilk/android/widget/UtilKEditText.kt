package com.mozhimen.basick.utilk.android.widget

import android.text.Editable
import android.text.InputFilter
import android.widget.EditText
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.android.view.bases.BaseTextWatcher
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.elemk.commons.IA_BListener
import com.mozhimen.basick.utilk.kotlin.asStringTrim
import com.mozhimen.basick.utilk.kotlinx.coroutines.UtilKFlow.asSearchFlow
import com.mozhimen.basick.utilk.kotlinx.coroutines.asEditTextChangeFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @ClassName UtilKViewTextEdit
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 0:28
 * @Version 1.0
 */
fun EditText.getValue(): String =
    UtilKEditText.getValue(this)

fun EditText.applyInputMaxLength(inputMaxLength: Int) {
    UtilKEditText.applyInputMaxLength(this, inputMaxLength)
}

fun EditText.addTextChangeWatcher(onTextChanged: IA_Listener<String>/*(newText: String) -> Unit*/) {
    UtilKEditText.addTextChangeWatcher(this, onTextChanged)
}

object UtilKEditText {

    @ExperimentalCoroutinesApi
    @FlowPreview
    @JvmStatic
    fun applyDebounceTextChangeListener(
        editText: EditText,
        scope: CoroutineScope,
        searchBlock: suspend CoroutineScope.(String) -> List<String>,
        resBlock: IAB_Listener<EditText, List<String>>,
        thresholdMillis: Long = 500
    ) {
        editText.asEditTextChangeFlow().filter { it.isNotEmpty() }.debounce(thresholdMillis).flatMapLatest { asSearchFlow(it.toString(), scope, searchBlock) }.flowOn(Dispatchers.IO).onEach {
            resBlock(editText, it)
        }.launchIn(scope)
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    @JvmStatic
    fun applySuspendDebounceTextChangeListener(
        editText: EditText,
        scope: CoroutineScope,
        searchBlock: suspend CoroutineScope.(String) -> List<String>,
        resBlock: suspend CoroutineScope.(EditText, List<String>) -> Unit,
        thresholdMillis: Long = 500
    ) {
        editText.asEditTextChangeFlow().filter { it.isNotEmpty() }.debounce(thresholdMillis).flatMapLatest { asSearchFlow(it.toString(), scope, searchBlock) }.flowOn(Dispatchers.IO).onEach {
            scope.resBlock(editText, it)
        }.launchIn(scope)
    }

    /**
     * 最多可输入的字符数
     * @param editText EditText
     * @param inputMaxLength Int
     */
    @JvmStatic
    fun applyInputMaxLength(editText: EditText, inputMaxLength: Int) {
        if (inputMaxLength > 0) editText.filters = arrayOf(InputFilter.LengthFilter(inputMaxLength))
    }

    /**
     * 获取text
     * @param editText EditText
     * @return String
     */
    @JvmStatic
    fun getValue(editText: EditText): String =
        editText.text.asStringTrim()

    /**
     * 变化观察
     * @param editText EditText
     * @param onTextChanged Function1<[@kotlin.ParameterName] String, Unit>
     */
    @JvmStatic
    fun addTextChangeWatcher(editText: EditText, onTextChanged: IA_Listener<String>/*(newTextStr: String) -> Unit*/) {
        editText.addTextChangedListener(object : BaseTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                onTextChanged(s.toString())
            }
        })
    }
}