package com.mozhimen.basick.utilk

import android.widget.Toast
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
        Toast.makeText(UtilKGlobal.instance.getApp(), msg, duration).show()
    }

    fun showToast(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(UtilKGlobal.instance.getApp(), msgId, duration).show()
    }

    fun showToastOnMain(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        EventKHandler(_context).post { showToast(msg, duration) }
    }

    fun showToastOnMain(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
        EventKHandler(_context).post { showToast(msgId, duration) }
    }
}