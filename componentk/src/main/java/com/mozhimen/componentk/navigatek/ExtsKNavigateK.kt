package com.mozhimen.componentk.navigatek


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