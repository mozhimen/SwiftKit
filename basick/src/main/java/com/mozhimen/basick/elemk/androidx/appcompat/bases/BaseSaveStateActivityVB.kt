package com.mozhimen.basick.elemk.androidx.appcompat.bases

import android.os.Bundle
import android.os.PersistableBundle
import android.view.KeyEvent
import androidx.databinding.ViewDataBinding

/**
 * @ClassName BaseSaveStateActivityVB
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/26 15:00
 * @Version 1.0
 */
abstract class BaseSaveStateActivityVB<VB : ViewDataBinding> : BaseActivityVB<VB> {

    /**
     * 针对Hilt(@JvmOverloads kotlin默认参数值无效)
     * @constructor
     */
    constructor() :  super()

    //////////////////////////////////////////////////////////////////////////////

    private var _stateSaved = false
    
    val isActivityFinishing get() = _stateSaved

    //////////////////////////////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _stateSaved = false
    }

    override fun onStart() {
        super.onStart()
        _stateSaved = false
    }

    override fun onResume() {
        super.onResume()
        _stateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        // Not call super won't help us, still get crash
        super.onSaveInstanceState(outState, outPersistentState)
        _stateSaved = true
    }

    override fun onStop() {
        super.onStop()
        _stateSaved = true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (!_stateSaved) {
            super.onKeyDown(keyCode, event)
        } else {
            // State already saved, so ignore the event
            true
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (!_stateSaved)
            super.onBackPressed()
    }
}