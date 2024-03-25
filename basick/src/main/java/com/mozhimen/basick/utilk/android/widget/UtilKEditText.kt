package com.mozhimen.basick.utilk.android.widget

import android.text.Editable
import android.text.InputFilter
import android.widget.EditText
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.android.view.commons.ITextWatcher
import com.mozhimen.basick.elemk.android.view.cons.CEditorInfo
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.utilk.kotlin.obj2stringTrim
import com.mozhimen.basick.utilk.kotlinx.coroutines.UtilKFlow.getSearchFlow
import com.mozhimen.basick.utilk.kotlinx.coroutines.getEditTextChangeFlow
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
val EditText.value: String get() = UtilKEditText.getValue(this)

//////////////////////////////////////////////////////////////////////////////////////////////

fun EditText.applyOnEditorActionListener_ofSearch(block: I_Listener) {
    UtilKEditText.applyOnEditorActionListener_ofSearch(this, block)
}

@FlowPreview
@ExperimentalCoroutinesApi
fun EditText.applyDebounceTextChangeListener(
    scope: CoroutineScope,
    searchBlock: suspend CoroutineScope.(String) -> List<String>,
    resBlock: IAB_Listener<EditText, List<String>>,
    thresholdMillis: Long = 500
) {
    UtilKEditText.applyDebounceTextChangeListener(this, scope, searchBlock, resBlock, thresholdMillis)
}

@FlowPreview
@ExperimentalCoroutinesApi
fun EditText.applySuspendDebounceTextChangeListener(
    scope: CoroutineScope,
    searchBlock: suspend CoroutineScope.(String) -> List<String>,
    resBlock: suspend CoroutineScope.(EditText, List<String>) -> Unit,
    thresholdMillis: Long = 500
) {
    UtilKEditText.applySuspendDebounceTextChangeListener(this, scope, searchBlock, resBlock, thresholdMillis)
}

fun EditText.addTextChangedObserver(onTextChanged: IA_Listener<String>/*(newText: String) -> Unit*/) {
    UtilKEditText.addTextChangedObserver(this, onTextChanged)
}

fun EditText.clearText() {
    UtilKEditText.clearText(this)
}

//////////////////////////////////////////////////////////////////////////////////////////////

object UtilKEditText {

    @JvmStatic
    fun getText(editText: EditText): Editable =
        editText.text

    //获取text
    @JvmStatic
    fun getValue(editText: EditText): String =
        getText(editText).obj2stringTrim()

    //////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyOnEditorActionListener_ofSearch(editText: EditText, block: I_Listener) {
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == CEditorInfo.IME_ACTION_SEARCH) {
                block.invoke()
                return@setOnEditorActionListener true
            }
            false
        }
    }

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
        editText.getEditTextChangeFlow().filter { it.isNotEmpty() }.debounce(thresholdMillis).flatMapLatest { getSearchFlow(it.toString(), scope, searchBlock) }.flowOn(Dispatchers.IO).onEach {
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
        editText.getEditTextChangeFlow().filter { it.isNotEmpty() }.debounce(thresholdMillis).flatMapLatest { getSearchFlow(it.toString(), scope, searchBlock) }.flowOn(Dispatchers.IO).onEach {
            scope.resBlock(editText, it)
        }.launchIn(scope)
    }

    //变化观察
    @JvmStatic
    fun addTextChangedObserver(editText: EditText, onTextChanged: IA_Listener<String>/*(newTextStr: String) -> Unit*/) {
        editText.addTextChangedListener(object : ITextWatcher {
            override fun afterTextChanged(s: Editable?) {
                onTextChanged(s.toString())
            }
        })
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun clearText(editText: EditText) {
        editText.text.clear()
    }
}