package com.mozhimen.app.abilitymk.restfulmk

import com.mozhimen.abilitymk.restfulmk.commons.InterceptorMK

/**
 * @ClassName HttpStatusInterceptor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/19 18:19
 * @Version 1.0
 */
class HttpStatusInterceptor : InterceptorMK {
    override fun intercept(chain: InterceptorMK.Chain): Boolean {
        val responseMK = chain.response()
        if (!chain.isRequestPeriod && responseMK != null) {
            /*when (responseMK.code) {
                ResponseMK.RC_NEED_LOGIN -> {
                    ARouter.getInstance().build("account/login").navigation()
                }
                ResponseMK.RC_AUTH_TOKEN_EXPIRED , ResponseMK.RC_AUTH_TOKEN_INVALID , ResponseMK.RC_USER_FORBID -> {
                    ARouter.getInstance().build("/degree/global/activity")
                        .withString("degree_title", "非法访问")
                        .withString("degree_dec", responseMK.msg)
                        .withString("degree_action", responseMK.errorData["helpUrl"])
                }
            }*/
        }
        return false
    }
}