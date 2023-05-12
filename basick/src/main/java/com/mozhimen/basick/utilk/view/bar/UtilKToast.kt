package com.mozhimen.basick.utilk.view.bar

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.mozhimen.basick.utilk.os.thread.UtilKHandler
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.exts.isFinishingOrDestroyed


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
    fun show(msg: String, duration: Int = Toast.LENGTH_SHORT, context: Context = _context) {
        if (context is Activity) {
            if (!context.isFinishingOrDestroyed()) {
                makeToast(context, msg, duration)
            }
        } else {
            makeToast(context, msg, duration)
        }
    }

    /**
     * show
     * @param msgId Int
     * @param duration Int
     */
    @JvmStatic
    fun show(msgId: Int, duration: Int = Toast.LENGTH_SHORT, context: Context = _context) {
        if (context is Activity) {
            if (!context.isFinishingOrDestroyed()) {
                makeToast(context, msgId, duration)
            }
        } else {
            makeToast(context, msgId, duration)
        }
    }

    /**
     * 在主线程show
     * @param msg String
     * @param duration Int
     */
    @JvmStatic
    fun showOnMain(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        if (UtilKHandler.isMainLooper()) {
            show(msg, duration)
        } else {
            UtilKHandler.postOnMain { show(msg, duration) }
        }
    }

    /**
     * 在主线程show
     * @param msg String
     * @param duration Int
     */
    @JvmStatic
    fun showOnMain(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
        if (UtilKHandler.isMainLooper()) {
            show(msgId, duration)
        } else {
            UtilKHandler.postOnMain { show(msgId, duration) }
        }
    }

    @JvmStatic
    fun makeToast(context: Context, msg: String, duration: Int) {
        Toast.makeText(context, msg, duration).show()
    }

    @JvmStatic
    fun makeToast(context: Context, msgId: Int, duration: Int) {
        Toast.makeText(context, msgId, duration).show()
    }
}