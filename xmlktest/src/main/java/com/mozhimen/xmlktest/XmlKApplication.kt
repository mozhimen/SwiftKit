package com.mozhimen.xmlktest

import android.app.Application


/**
 * @ClassName XmlKApplication
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/16 15:11
 * @Version 1.0
 */
class XmlKApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //AdaptKAutoSize.instance.init(640, 400)
    }
}