package com.mozhimen.basick.utilk

import android.widget.Toast
import com.mozhimen.basick.elemk.handler.bases.BaseWeakHandler
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
    fun showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(_context, msg, duration).show()
    }

    @JvmStatic
    fun showToast(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(_context, msgId, duration).show()
    }

    @JvmStatic
    fun showToastOnMain(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        BaseWeakHandler(_context).post { showToast(msg, duration) }
    }

    @JvmStatic
    fun showToastOnMain(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
        BaseWeakHandler(_context).post { showToast(msgId, duration) }
    }
}