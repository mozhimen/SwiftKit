package com.mozhimen.abilityk.hotupdatek.commons

import com.liulishuo.okdownload.DownloadTask

/**
 * @ClassName IHotUpdateKListener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 15:35
 * @Version 1.0
 */
interface IHotupdateKListener {
    fun onComplete()
    fun onFail(msg: String)
}