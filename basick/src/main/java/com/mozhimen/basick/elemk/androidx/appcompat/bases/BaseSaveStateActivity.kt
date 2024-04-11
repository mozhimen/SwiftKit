package com.mozhimen.basick.elemk.androidx.appcompat.bases

import android.os.Bundle
import android.os.PersistableBundle
import android.view.KeyEvent

/**
 * @ClassName BaseSaveStateActivityVB
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
abstract class BaseSaveStateActivity : BaseActivity() {

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