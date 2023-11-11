package com.mozhimen.componentk.netk.app.install

import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.android.content.bases.BaseBroadcastReceiver
import com.mozhimen.basick.elemk.android.content.bases.BaseBroadcastReceiverProxy2
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.android.content.UtilKPackageManager
import com.mozhimen.basick.utilk.android.os.UtilKRom
import com.mozhimen.componentk.netk.app.NetKApp
import com.mozhimen.componentk.installk.manager.InstallKManager
import com.mozhimen.componentk.netk.app.install.helpers.NetKAppInstallReceiver
import com.mozhimen.componentk.netk.app.task.db.AppTask
import java.lang.ref.WeakReference

/**
 * @ClassName InstallKFlyme
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/9 9:29
 * @Version 1.0
 */
@OptInApiInit_InApplication
@OptInApiCall_BindLifecycle
@OptInApiInit_ByLazy
class NetKAppInstallProxy(
    context: Context,
    owner: LifecycleOwner,
    receiver: BaseBroadcastReceiver = NetKAppInstallReceiver(),
) : BaseBroadcastReceiverProxy2(
    context,
    owner,
    receiver,
    if (UtilKRom.isFlyme())
        arrayOf(CIntent.ACTION_PACKAGE_REMOVED)
    else
        arrayOf(CIntent.ACTION_PACKAGE_ADDED, CIntent.ACTION_PACKAGE_REPLACED, CIntent.ACTION_PACKAGE_REMOVED)
), IStackKListener {

    private var _appTask: AppTask? = null

    /////////////////////////////////////////////////////////////////////////////

    fun setAppTask(task: AppTask) {
        _appTask = task
    }

    /////////////////////////////////////////////////////////////////////////////

    override fun registerReceiver() {
        if (UtilKRom.isFlyme()) {
            StackKCb.instance.addFrontBackListener(this)
        }
        val intentFilter = IntentFilter()
        if (_actions.isNotEmpty()) {
            for (action in _actions)
                intentFilter.addAction(action)
        }
        intentFilter.addDataScheme("package")
        _activity.registerReceiver(_receiver, intentFilter)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        if (UtilKRom.isFlyme()) {
            StackKCb.instance.removeFrontBackListener(this)
        }
        super.onDestroy(owner)
    }

    override fun onChanged(isFront: Boolean, activityRef: WeakReference<Activity>) {
        _appTask?.let {
            if (UtilKPackageManager.hasPackage(_activity, it.apkPackageName)) {
                InstallKManager.onPackageAdded(_activity, it.apkPackageName)
                NetKApp.onInstallSuccess(it)
            }
        }
        _appTask = null
    }
}