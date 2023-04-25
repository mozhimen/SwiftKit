package com.mozhimen.componentk.installk.cons

/**
 * @ClassName EInstallMode
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/7 0:05
 * @Version 1.0
 */
enum class EInstallMode {
    AUTO,//自适配
    ROOT,//root安装
    SILENCE,//静默安装//可能需要自己退出再点开
    SMART,//智能安装
    HAND//手动安装
}