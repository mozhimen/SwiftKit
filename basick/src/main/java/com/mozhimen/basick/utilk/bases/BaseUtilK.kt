package com.mozhimen.basick.utilk.bases

import com.mozhimen.basick.utilk.android.app.UtilKApplication


/**
 * @ClassName BaseUtilK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 10:54
 * @Version 1.0
 */
open class BaseUtilK : IUtilK {
    protected val _context by lazy { UtilKApplication.instance.applicationContext }
}