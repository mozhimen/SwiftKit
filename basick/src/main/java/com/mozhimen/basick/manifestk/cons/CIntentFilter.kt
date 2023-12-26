package com.mozhimen.basick.manifestk.cons

/**
 * @ClassName CIntentFilter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/26
 * @Version 1.0
 */
object CIntentFilter {
    const val ACTION_VIEW_CATEGORY_DEFAULT_BROWSABLE = """
                    <!--外部浏览器拉起策略-->
            <intent-filter>
                <!--必有项-->
                <action android:name="android.intent.action.VIEW" />
                <!--表示该页面可以被隐式调用，必须加上该项-->
                <category android:name="android.intent.category.DEFAULT" />
                <!--BROWSABLE指定该Activity能被浏览器安全调用-->
                <category android:name="android.intent.category.BROWSABLE" />
                <!--协议部分-->
                <!--声明自定义scheme，类似于http, https-->
                <data
                    android:host="com.xx.xxx"
                    android:scheme="http?" />
            </intent-filter>
    """
}