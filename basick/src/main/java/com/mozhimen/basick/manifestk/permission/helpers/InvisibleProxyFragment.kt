package com.mozhimen.basick.manifestk.permission.helpers

import androidx.fragment.app.Fragment
import com.mozhimen.basick.elemk.android.content.cons.CPackageManager
import com.mozhimen.basick.manifestk.permission.commons.IManifestKPermissionListener

/**
 * @ClassName InvisibleFragment
 * @Description TODO
 * @Author mozhimen
 * @Version 1.0
 */
class InvisibleProxyFragment : Fragment() {
    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    ///////////////////////////////////////////////////////////////////////

    private var _listener: IManifestKPermissionListener? = null

    ///////////////////////////////////////////////////////////////////////

    fun request(permissions: Array<out String>, listener: IManifestKPermissionListener) {
        _listener = listener
        requestPermissions(permissions, PERMISSION_REQUEST_CODE)
    }

    ///////////////////////////////////////////////////////////////////////

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val deniedList = ArrayList<String>()
            for ((position, result) in grantResults.withIndex()) {
                if (result != CPackageManager.PERMISSION_GRANTED)
                    deniedList.add(permissions[position])
            }
            val isAllGranted = deniedList.isEmpty()
            _listener?.invoke(isAllGranted, deniedList)
        }
    }
}