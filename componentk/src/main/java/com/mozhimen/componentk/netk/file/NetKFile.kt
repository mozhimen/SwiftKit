package com.mozhimen.componentk.netk.file

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.permissionk.annors.APermissionKRequire
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.componentk.netk.file.download.FileDownloadTaskMgr


/**
 * @ClassName NetKFile
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 14:12
 * @Version 1.0
 */
@APermissionKRequire(CPermission.INTERNET, CPermission.READ_EXTERNAL_STORAGE, CPermission.WRITE_EXTERNAL_STORAGE)
class NetKFile(owner: LifecycleOwner) {
    companion object {
        private const val TAG = "NetKFile>>>>>"
    }

    private val _fileDownloadTaskMgr by lazy { FileDownloadTaskMgr(owner) }

    fun download(): FileDownloadTaskMgr =
        _fileDownloadTaskMgr

//    companion object {
//        @JvmStatic
//        val instance = NetKFileDownloadProvider.holder
//    }
//
//    private object NetKFileDownloadProvider {
//        val holder = NetKFileDownload()
//    }
//
//    private val _netKFileDownload: NetKFileDownload by lazy { NetKFileDownload() }
//
//    fun download(): NetKFileDownload {
//        return _netKFileDownload
//    }

//    object Download {
//        @JvmStatic
//        fun start(url: String, filePath: String, fileName: String, threadCount: Int): DownloadManger {
//            val downloadManger: DownloadManger = DownloadManger.getInstance(context)
//            downloadManger.init(url, path, name, childTaskCount)
//            return downloadManger
//        }
//
//        @JvmStatic
//        fun start(url: String, file: File, threadCount: Int): DownloadManger {
//            val downloadManger: DownloadManger = DownloadManger.getInstance(context)
//            downloadManger.init(url, path, name, childTaskCount)
//            return downloadManger
//        }
//    }

}