package com.mozhimen.manifestk.permission.helpers

import androidx.annotation.RequiresApi
import com.mozhimen.kotlin.elemk.android.os.cons.CVersCode
import com.mozhimen.kotlin.elemk.androidx.fragment.InvisibleProxyFragment_ofAndroid
import com.mozhimen.kotlin.elemk.androidx.fragment.InvisibleProxyFragment_ofAndroidx

/**
 * @ClassName InvisibleFragment
 * @Description TODO
 * @Author mozhimen
 * @Version 1.0
 */
class InvisiblePermissionProxyFragment_ofAndroidx : InvisibleProxyFragment_ofAndroidx() {
    fun request(permissions: Array<out String>) {
        requestPermissions(permissions, _requestCodePermission)
    }
}

class InvisiblePermissionProxyFragment_ofAndroid : InvisibleProxyFragment_ofAndroid() {
    @RequiresApi(CVersCode.V_23_6_M)
    fun request(permissions: Array<out String>) {
        requestPermissions(permissions, _requestCodePermission)
    }
}