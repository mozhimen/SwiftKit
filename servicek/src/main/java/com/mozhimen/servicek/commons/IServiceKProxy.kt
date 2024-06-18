package com.mozhimen.servicek.commons

/**
 * @ClassName IServiceProxy
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Version 1.0
 */
interface IServiceKProxy {
    fun getConnListener(): IBaseServiceConnListener?
    fun bindService()
    fun unbindService()
}