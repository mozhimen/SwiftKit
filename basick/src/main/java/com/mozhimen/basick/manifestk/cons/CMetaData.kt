package com.mozhimen.basick.manifestk.cons


/**
 * @ClassName CMetaData
 * @Description



 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/12 13:49
 * @Version 1.0
 */
object CMetaData {
    const val DISABLE_SAFE_BROWSING = """
                    <!--关闭内置浏览器安全策略-->
            <meta-data
                android:name="android.webkit.WebView.EnableSafeBrowsing"
                android:value="false" />
    """

    const val MAX_ASPECT = """
                    <!--在全屏的时候，避免出现一些屏幕黑边-->
            <meta-data
                android:name="android.max_aspect"
                android:value="2.4" />
    """

    const val NOTCH_SUPPORT = """
                    <!--适配华为（huawei）刘海屏-->
            <meta-data
                android:name="android.notch_support"
                android:value="true" />
    """

    const val NOTCH_CONFIG = """
                    <!--适配小米（xiaomi）刘海屏-->
            <meta-data
                android:name="notch.config"
                android:value="portrait|landscape" />
    """
}