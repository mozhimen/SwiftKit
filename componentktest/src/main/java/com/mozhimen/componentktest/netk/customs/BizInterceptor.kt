package com.mozhimen.componentktest.netk.customs

import com.mozhimen.componentk.netk.async.commons.INetKChain
import com.mozhimen.componentk.netk.async.commons.INetKInterceptor

/**
 * @ClassName BizInterceptor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:07
 * @Version 1.0
 */
class BizInterceptor : INetKInterceptor {

    override fun intercept(chain: INetKChain): Boolean {
        if (chain.isRequestPeriod) {
            //你的全局请求头在这里增加
            /*val request = chain.request()
            request.addHeaders("auth-token", "xxx")*/
        }
        return false
    }
}