package com.mozhimen.app.abilitymk.restfulmk

/**
 * @ClassName RouteInterceptor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/19 19:39
 * @Version 1.0
 */
/*@Interceptor(priority = 9, name = "route_interceptor")
class RouteInterceptor : IInterceptor {
    private var mContext: Context? = null

    override fun init(context: Context?) {
        this.mContext = context
    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        val flag = postcard!!.extra

        when {
            (flag and Routes.EXTRA_LOGIN) != 0 -> {
                //login
                callback!!.onInterrupt(RuntimeException("need login"))
                loginIntercept()
                showToast("请先登录")
            }
            (flag and Routes.EXTRA_AUTHENTICATION) != 0 -> {
                //authentication
                callback!!.onInterrupt(RuntimeException("need authentication"))
                showToast("请先实名认证")
            }
            (flag and Routes.EXTRA_VIP) != 0 -> {
                //vip
                callback!!.onInterrupt(RuntimeException("need become vip"))
                showToast("请先加入会员")
            }
            else -> {
                callback!!.onContinue(postcard)
            }
        }
    }

    private fun loginIntercept() {
        Handler(Looper.getMainLooper()).post {
            ARouter.getInstance().build("/account/login").navigation()
        }
    }

    private fun showToast(content: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(mContext!!, content, Toast.LENGTH_SHORT).show()
        }
    }
}*/
