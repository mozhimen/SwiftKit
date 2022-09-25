package com.mozhimen.componentktest.netk.customs

import com.mozhimen.componentk.netk.commons.INetKInterceptor


/**
 * @ClassName HttpStatusInterceptor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/19 18:19
 * @Version 1.0
 */
class RouteInterceptor : INetKInterceptor {
    override fun intercept(chain: INetKInterceptor.INetKChain): Boolean {
        val response = chain.response()
        if (!chain.isRequestPeriod && response != null) {
            //结合ARouter的降级策略
            /*when (response.code) {
                RC_NEED_LOGIN -> {
                    ARouter.getInstance().build("account/login").navigation()
                }
                RC_AUTH_TOKEN_EXPIRED, RC_AUTH_TOKEN_INVALID, RC_USER_FORBID -> {
                    ARouter.getInstance().build("/degree/global/activity")
                        .withString("degree_title", "非法访问")
                        .withString("degree_dec", response.msg)
                        .withString("degree_action", response.errorData["helpUrl"])
                }
            }*/
        }
        return false
    }
}