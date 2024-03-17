package com.mozhimen.basick.manifestk.permission.scoped.helpers

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.android.provider.cons.CDocumentsContract
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.manifestk.permission.scoped.cons.CManifestKPermissionScoped
import com.mozhimen.basick.manifestk.permission.scoped.utils.ManifestKPermissionScopedUtil
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.kotlin.strUri2uri

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
        UtilKLogWrapper.dt(TAG, "request: _strFilePath $_strFilePath")
        if (ManifestKPermissionScopedUtil.isPermissionStrFilePathProtectedGranted(requireActivity(), _strFilePath)) {
            _listener?.invoke(true)
        } else {
            val uriDocumentAndroid: Uri = ManifestKPermissionScopedUtil.getStrUriDocumentAndroidForStrFilePath(_strFilePath).strUri2uri() //调用方法，把path转换成可解析的uri文本
            val documentFile: DocumentFile? = DocumentFile.fromTreeUri(requireContext(), uriDocumentAndroid)
            val intent = Intent(CIntent.ACTION_OPEN_DOCUMENT_TREE).apply {
                addFlags(
                    CIntent.FLAG_GRANT_READ_URI_PERMISSION
                            or CIntent.FLAG_GRANT_WRITE_URI_PERMISSION
                            or CIntent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                            or CIntent.FLAG_GRANT_PREFIX_URI_PERMISSION
                )
            }
            if (UtilKBuildVersion.isAfterV_26_8_O()) {
                intent.putExtra(CDocumentsContract.EXTRA_INITIAL_URI, documentFile!!.uri)
            }
            startActivityForResult(intent, CManifestKPermissionScoped.CODE_PERMISSION_REQUEST_SCOPED) //开始授权
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