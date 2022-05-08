package com.mozhimen.abilityk.pushk

/**
 * @ClassName PushKMgr
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/10 13:27
 * @Version 1.0
 */
object PushKMgr {
    fun init() {
        initUmengPushSdk()
        /*initOEMPushSdk()*/
    }

    private fun initUmengPushSdk () {
        //在此处调用基础组件包提供的初始化函数相应信息可在应用管理->应用信息中找到http://message.l
        //参数一:当前上下文context;
        //参数二:应用申请的AppKey(需替换);
        //参数三:渠道名称;
        //参数四:设备类型,必须参数,传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机;传参数为UMConfig
        //参数五:Push推送业务的secret填充Umeng Message Secret对应信息(需替换)
        /*UMConfigure.init(
            ,
            "替换为Appkey,服务后台位置:应用管理->应用信息-> Appkey",
            "Umeng",
            UMConf igure .DEVICE_ _TYPE_ _PHONE,
            "替换为秘钥信息,服务后台位置:应用管理->应用信息-> Umeng Message Secret"
        );*/
    }

}