package com.mozhimen.componentk.netk.file.download

import android.content.Context
import android.net.Uri
import androidx.annotation.DrawableRes
import com.mozhimen.basick.utilk.android.util.UtilKLog.et
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basick.utilk.androidx.core.UtilKNotificationManagerCompat
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.componentk.BuildConfig
import com.mozhimen.componentk.R
import com.mozhimen.componentk.netk.file.download.annors.ADownloadEngine
import com.mozhimen.componentk.netk.file.download.annors.ANotificationVisibility
import com.mozhimen.componentk.netk.file.download.bases.BaseDownloader
import com.mozhimen.componentk.netk.file.download.commons.IDownloadListener
import java.io.File

/**
 *
 * @author by chiclaim@google.com
 */
class DownloadRequest(
    val context: Context,
    val url: String,
    @ADownloadEngine
    val engine: Int = ADownloadEngine.EMBED
) : BaseUtilK() {
    /**
     * 文件下载的目标地址
     *
     * 如果下载引擎为 DownloadManager，那么[destinationUri]必须是外部存储路径，不能是当前应用的黑盒目录（因为是系统应用来下载）
     */
    var destinationUri: Uri? = null
        private set
        get() {
            return if (field == null) {
                Uri.fromFile(File(DownloadUtils.getDownloadDir(context), url.substringAfterLast("/")))
            } else {
                field
            }
        }

    /**
     * Whether to ignore the local file, if true, it will be downloaded again
     */
    var ignoreLocal = false
        private set

    /**
     * 本次下载是否为更新当前的APP，如果是，则会自动处理弹出安装界面
     */
    var needInstall = false
        private set

    var notificationVisibility = ANotificationVisibility.VISIBLE_NOTIFY_COMPLETED
        private set

    var notificationSmallIcon = -1
        private set
        get() {
            return if (field == -1) context.applicationInfo.icon else field
        }

    var notificationTitle: CharSequence? = null
        private set
        get() {
            return if (field == null) getDefaultTitle() else field
        }
    var notificationContent: CharSequence? = null
        private set

    /**
     * 当通知栏被禁用，是否提示
     */
    var showNotificationDisableTip = false
        private set

    internal var fromNotifier = false
        private set

    internal fun setFromNotifier(isFromNotifier: Boolean): DownloadRequest {
        this.fromNotifier = isFromNotifier
        return this
    }

    fun setDestinationUri(uri: Uri): DownloadRequest {
        this.destinationUri = uri
        return this
    }

    fun setShowNotificationDisableTip(show: Boolean): DownloadRequest {
        this.showNotificationDisableTip = show
        return this
    }

    fun setNotificationSmallIcon(@DrawableRes smallIcon: Int): DownloadRequest {
        this.notificationSmallIcon = smallIcon
        return this
    }

    fun setIgnoreLocal(ignore: Boolean): DownloadRequest {
        this.ignoreLocal = ignore
        return this
    }


    fun setNeedInstall(need: Boolean): DownloadRequest {
        this.needInstall = need
        return this
    }

    fun setNotificationTitle(title: CharSequence): DownloadRequest {
        this.notificationTitle = title
        return this
    }

    fun setNotificationContent(content: CharSequence): DownloadRequest {
        this.notificationContent = content
        return this
    }


    /**
     * 设置通知栏可见性，默认为 [NOTIFIER_VISIBLE_NOTIFY_COMPLETED]
     * [NOTIFIER_VISIBLE] [NOTIFIER_HIDDEN] [NOTIFIER_VISIBLE_NOTIFY_COMPLETED] [NOTIFIER_VISIBLE_NOTIFY_ONLY_COMPLETION]
     */
    fun setNotificationVisibility(@ANotificationVisibility visibility: Int): DownloadRequest {
        notificationVisibility = visibility
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DownloadRequest) return false

        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        return url.hashCode()
    }

    private fun getDefaultTitle(): String {
        return context.getString(
            R.string.netk_file_notifier_title_placeholder,
            context.applicationInfo.loadLabel(context.packageManager)
        )
    }

    private var _listeners: MutableSet<IDownloadListener>? = null

    private fun initListenerList() {
        if (_listeners == null) {
            synchronized(this) {
                if (_listeners == null) {
                    _listeners = mutableSetOf()
                }
            }
        }
    }

    /**
     * 注册下载监听。当下载完成或下载失败会自动移除监听
     */
    fun registerListener(listener: IDownloadListener): DownloadRequest {
        initListenerList()
        _listeners?.add(listener)
        return this
    }

    fun unregisterListener(listener: IDownloadListener) {
        if (_listeners == null) return
        _listeners?.remove(listener)
    }

    internal fun onStart() {
        this._listeners?.forEach {
            it.onDownloadStart()
        }
    }

    internal fun onComplete(uri: Uri) {
        this._listeners?.forEach {
            it.onDownloadComplete(uri)
        }
        DownloaderManager.remove(this)
        _listeners?.clear()
    }

    internal fun onFailed(e: Throwable) {
        this._listeners?.forEach {
            it.onDownloadFailed(e)
        }
        DownloaderManager.remove(this)
        _listeners?.clear()
    }

    internal fun onProgressUpdate(percent: Int) {
        this._listeners?.forEach {
            it.onProgressUpdate(percent)
        }
    }

    private fun createDownloader(@ADownloadEngine engine: Int): BaseDownloader {
        return when (engine) {
            ADownloadEngine.SYSTEM_DM -> SystemDownloader(this)
            else -> EmbedDownloader(this)
        }
    }

    internal fun download() {
        val downloader = createDownloader(engine)
        downloader.download()
    }


    /**
     * 开始下载（运行在 Service 中）
     * 如果下载任务已经在运行，则返回 false，否则返回 true
     */
    fun startDownload(): Boolean {
        if (DownloaderManager.isRunning(this)) {
            if (BuildConfig.DEBUG) et(TAG, "下载任务已经存在")
            return false
        }
        if (notificationVisibility != ANotificationVisibility.HIDDEN && showNotificationDisableTip&&!UtilKNotificationManagerCompat.areNotificationsEnabled()) {
                R.string.netk_file_notification_disable.showToast()
        }
        val downloader = createDownloader(engine)
        downloader.startDownload()
        return true
    }

}