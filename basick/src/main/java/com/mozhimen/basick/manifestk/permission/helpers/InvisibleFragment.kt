package com.mozhimen.basick.manifestk.permission.helpers

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import com.mozhimen.basick.elemk.commons.IAB_Listener

/**
 * @ClassName InvisibleFragment
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:07
 * @Version 1.0
 */
typealias IManifestKPermissionListener = IAB_Listener<Boolean, List<String>> /*(Boolean, List<String>) -> Unit*/

class InvisibleFragment : Fragment() {
    private var _listener: IManifestKPermissionListener? = null

    fun requestNow(listener: IManifestKPermissionListener, vararg permissions: String) {
        _listener = listener
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            _listener?.let { it(allGranted, deniedList) }
        }
    }
}