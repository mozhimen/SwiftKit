package com.mozhimen.app.abilityk.restfulk

import com.mozhimen.abilityk.restfulk.commons.InterceptorK

/**
 * @ClassName HttpStatusInterceptor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/19 18:19
 * @Version 1.0
 */
class HttpStatusInterceptor : InterceptorK {
    override fun intercept(chain: InterceptorK.Chain): Boolean {
        val responseK = chain.response()
        if (!chain.isRequestPeriod && responseK != null) {
            /*when (responseK.code) {
                ResponseK.RC_NEED_LOGIN -> {
                    ARouter.getInstance().build("account/login").navigation()
                }
                ResponseK.RC_AUTH_TOKEN_EXPIRED , ResponseK.RC_AUTH_TOKEN_INVALID , ResponseK.RC_USER_FORBID -> {
                    ARouter.getInstance().build("/degree/global/activity")
                        .withString("degree_title", "非法访问")
                        .withString("degree_dec", responseK.msg)
                        .withString("degree_action", responseK.errorData["helpUrl"])
                }
            }*/
        }
        return false
    }
}