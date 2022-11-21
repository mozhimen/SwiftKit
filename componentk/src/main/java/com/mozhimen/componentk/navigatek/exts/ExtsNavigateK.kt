package com.mozhimen.componentk.navigatek.exts

import com.mozhimen.componentk.navigatek.NavigateK


/**
 * @ClassName ExtsKNavigateK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/11 16:54
 * @Version 1.0
 */
fun Class<*>.getNavigateKId(): Int {
    return NavigateK.getId(this)
}