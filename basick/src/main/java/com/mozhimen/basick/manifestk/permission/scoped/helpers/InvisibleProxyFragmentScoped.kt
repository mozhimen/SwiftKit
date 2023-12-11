package com.mozhimen.basick.manifestk.permission.scoped.helpers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.manifestk.permission.scoped.cons.CManifestKPermissionScoped
import com.mozhimen.basick.manifestk.permission.scoped.utils.ManifestKPermissionScopedUtil
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName InvisibleProxyFragmentScoped
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/10 23:27
 * @Version 1.0
 */
typealias IManifestKPermissionScopedListener = IA_Listener<Boolean>

class InvisibleProxyFragmentScoped : Fragment(), IUtilK {

    companion object {
        fun newInstance(strFilePath: String): InvisibleProxyFragmentScoped {
            return InvisibleProxyFragmentScoped().apply {
                arguments = Bundle().apply {
                    putString(CManifestKPermissionScoped.EXTRA_PERMISSION_REQUEST_SCOPED, strFilePath)
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////

    private val _strFilePath: String by lazy {
        arguments?.getString(CManifestKPermissionScoped.EXTRA_PERMISSION_REQUEST_SCOPED) ?: ""
    }

    private var _listener: IManifestKPermissionScopedListener? = null

    ///////////////////////////////////////////////////////////////////////

    @SuppressLint("WrongConstant")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CManifestKPermissionScoped.CODE_PERMISSION_REQUEST_SCOPED && data?.data != null) {
            requireActivity().contentResolver.takePersistableUriPermission(data.data!!, data.flags and (CIntent.FLAG_GRANT_READ_URI_PERMISSION or CIntent.FLAG_GRANT_WRITE_URI_PERMISSION)) //保存目录的访问权限
            savePermissionRes()
            _listener?.invoke(true)
            return
        }
        _listener?.invoke(false)
    }

    ///////////////////////////////////////////////////////////////////////

    fun request(listener: IManifestKPermissionScopedListener) {
        _listener = listener
        Log.d(TAG, "request: _strFilePath $_strFilePath")
        if (ManifestKPermissionScopedUtil.isPermissionStrFilePathProtectedGranted(requireActivity(), _strFilePath)) {
            _listener?.invoke(true)
        } else {
            ManifestKPermissionScopedUtil.requestPermissionForStrFilePathOfStrUriDocumentAndroid(requireActivity(), _strFilePath, CManifestKPermissionScoped.CODE_PERMISSION_REQUEST_SCOPED)
        }
    }

    ///////////////////////////////////////////////////////////////////////

    private fun savePermissionRes() {
        if (_strFilePath.contains(CManifestKPermissionScoped.STR_ANDROID_DATA))
            ScopedCache.hasPermissionAndroidData = true
        if (_strFilePath.contains(CManifestKPermissionScoped.STR_ANDROID_OBB))
            ScopedCache.hasPermissionAndroidObb = true
    }
}