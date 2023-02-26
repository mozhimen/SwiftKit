package com.mozhimen.basick.utilk.view.bar

import android.widget.Toast
import com.mozhimen.basick.utilk.os.thread.UtilKHandler
import com.mozhimen.basick.utilk.os.thread.UtilKThread
import com.mozhimen.basick.utilk.content.UtilKApplication


/**
 * @ClassName UtilKToast
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 1:11
 * @Version 1.0
 */
object UtilKToast {
    private val _context = UtilKApplication.instance.get()

    /**
     * show
     * @param msg String
     * @param duration Int
     */
    @JvmStatic
    fun show(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(_context, msg, duration).show()
    }

    /**
     * show
     * @param msgId Int
     * @param duration Int
     */
    @JvmStatic
    fun show(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(_context, msgId, duration).show()
    }

    /**
     * 在主线程show
     * @param msg String
     * @param duration Int
     */
    @JvmStatic
    fun showOnMain(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        if (UtilKThread.isMainLooper()) {
            show(msg, duration)
        } else {
            UtilKHandler.prepareAndLoop { show(msg, duration) }
        }
    }

    /**
     * 在主线程show
     * @param msg String
     * @param duration Int
     */
    @JvmStatic
    fun showOnMain(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
        if (UtilKThread.isMainLooper()) {
            show(msgId, duration)
        } else {
            UtilKHandler.prepareAndLoop { show(msgId, duration) }
        }
    }
}