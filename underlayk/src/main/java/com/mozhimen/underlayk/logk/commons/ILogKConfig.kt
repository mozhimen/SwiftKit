package com.mozhimen.underlayk.logk.commons

import com.mozhimen.basick.utilk.bases.IUtilK


/**
 * @ClassName ILogKConfig
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/10 13:09
 * @Version 1.0
 */
interface ILogKConfig : IUtilK {
    fun injectJsonParser(): ILogKJsonParser?
    fun getGlobalTag(): String
    fun getPrinters(): Array<ILogKPrinter>?
    fun getStackTraceDepth(): Int
    fun isEnable(): Boolean
    fun isIncludeThread(): Boolean
}