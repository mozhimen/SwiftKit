package com.mozhimen.basick.utilk

import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.eventk.EventKHandler

/**
 * @ClassName UtilKToast
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 1:11
 * @Version 1.0
 */
object UtilKToast {
    private val _context = UtilKGlobal.instance.getApp()!!

    fun showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(_context, msg, duration).show()
    }

    fun showToast(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(_context, msgId, duration).show()
    }

    fun showToastOnMain(lifecycleOwner: LifecycleOwner, msg: String, duration: Int = Toast.LENGTH_SHORT) {
        EventKHandler(lifecycleOwner).post { showToast(msg, duration) }
    }

    fun showToastOnMain(lifecycleOwner: LifecycleOwner, msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
        EventKHandler(lifecycleOwner).post { showToast(msgId, duration) }
    }
}