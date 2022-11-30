package com.mozhimen.basick.utilk

import android.widget.Toast
import com.mozhimen.basick.utilk.context.UtilKApplication


/**
 * @ClassName UtilKToast
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 1:11
 * @Version 1.0
 */
object UtilKToast {
    private val _context = UtilKApplication.instance.get()

    @JvmStatic
    fun show(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(_context, msg, duration).show()
    }

    @JvmStatic
    fun show(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(_context, msgId, duration).show()
    }

    @JvmStatic
    fun showOnMain(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        if (UtilKThread.isMainLooper()) {
            show(msg, duration)
        } else {
            UtilKHandler.prepareAndLoop { show(msg, duration) }
        }
    }

    @JvmStatic
    fun showOnMain(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
        if (UtilKThread.isMainLooper()) {
            show(msgId, duration)
        } else {
            UtilKHandler.prepareAndLoop { show(msgId, duration) }
        }
    }
}