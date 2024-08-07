package com.mozhimen.basick.lintk.optins.permission

import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission

/**
 * @ClassName OPermission_ACCESS_NETWORK_STATE
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/4
 * @Version 1.0
 */
@AManifestKRequire(CPermission.REPLACE_EXISTING_PACKAGE)
@RequiresOptIn("The api is must add this permission to yout AndroidManifest.xml or dynamic call requestPermission. 需要声明此权限到你的AndroidManifest.xml或动态申请权限", RequiresOptIn.Level.ERROR)
annotation class OPermission_REPLACE_EXISTING_PACKAGE
