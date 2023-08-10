package com.mozhimen.abilityk.updatek.commons

/**
 * @ClassName IHotUpdateKListener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 15:35
 * @Version 1.0
 */
interface IUpdateKListener {
    fun onComplete()
    fun onFail(msg: String)
}