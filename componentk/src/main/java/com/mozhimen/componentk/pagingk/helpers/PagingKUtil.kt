package com.mozhimen.componentk.pagingk.helpers

import com.mozhimen.basick.utilk.kotlin.UtilKException
import com.mozhimen.componentk.pagingk.bases.BasePagingKRep

/**
 * @ClassName PagingKUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 17:36
 * @Version 1.0
 */
object PagingKUtil {
    @JvmStatic
    fun <T> createErrorPagingKRep(exception: Exception): BasePagingKRep<T> {
        val basePagingKRep = BasePagingKRep<T>()
        basePagingKRep.code = 10010
        basePagingKRep.msg = UtilKException.getStrMessageForException(exception)
        return basePagingKRep
    }
}