package com.mozhimen.app.abilityk.restfulk

import com.mozhimen.abilityk.restfulk.commons._Interceptor


/**
 * @ClassName HttpStatusInterceptor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/19 18:19
 * @Version 1.0
 */
class HttpStatusInterceptor : _Interceptor {
    override fun intercept(IChain: _Interceptor.IChain): Boolean {
        val response = IChain.response()
        if (!IChain.isRequestPeriod && response != null) {
            /*when (response.code) {
                ResponseK.RC_NEED_LOGIN -> {
                    ARouter.getInstance().build("account/login").navigation()
                }
                ResponseK.RC_AUTH_TOKEN_EXPIRED , ResponseK.RC_AUTH_TOKEN_INVALID , ResponseK.RC_USER_FORBID -> {
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