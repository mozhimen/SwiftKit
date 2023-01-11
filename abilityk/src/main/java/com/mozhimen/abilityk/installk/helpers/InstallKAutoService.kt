package com.mozhimen.abilityk.installk.helpers

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.permissionk.annors.APermissionRequire

/**
 * @ClassName InstallKSmartService
 * @Description you must register service

 * AndroidManifest.xml
<service
android:name=".installk.InstallKSmartService"
android:enabled="true"
android:exported="true"
android:label="@string/installk_label"
android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
<intent-filter>
<action android:name="android.accessibilityservice.AccessibilityService" />
</intent-filter>
<meta-data
android:name="android.accessibilityservice"
android:resource="@xml/installk_smart_accessibility_service_config" />
</service>

 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/4 22:39
 * @Version 1.0
 */
@APermissionRequire(CPermission.BIND_ACCESSIBILITY_SERVICE)
class InstallKAutoService : AccessibilityService() {
    companion object {
        private const val TAG = "InstallKSmartService>>>>>"
    }

    private var _handledMap: MutableMap<Int, Boolean?> = HashMap()
    private val _handler = Handler(Looper.getMainLooper())

    @SuppressLint("LongLogTag")
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        Log.d(TAG, "onAccessibilityEvent: $event")

        if (!event.packageName.toString().contains("packageinstaller")) {
            //不写完整包名，是因为某些手机(如小米)安装器包名是自定义的
            return
        }

        val nodeInfo = event.source
        if (nodeInfo == null) {
            Log.i(TAG, "eventNode: null, 重新获取eventNode...")
            performGlobalAction(GLOBAL_ACTION_RECENTS) // 打开最近页面
            _handler.postDelayed({
                performGlobalAction(GLOBAL_ACTION_BACK) // 返回安装页面
            }, 320)
            return
        }

        val eventType = event.eventType
        if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (_handledMap[event.windowId] == null) {
                val handled = iterateNodesAndHandle(nodeInfo)
                if (handled) {
                    _handledMap[event.windowId] = true
                }
            }
        }
    }

    @SuppressLint("LongLogTag")
    private fun iterateNodesAndHandle(nodeInfo: AccessibilityNodeInfo?): Boolean {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount
            if ("android.widget.Button" == nodeInfo.className) {
                val nodeContent = nodeInfo.text.toString()
                Log.d(TAG, "content is $nodeContent")
                if (nodeContent.isNotEmpty() && ("安装" == nodeContent || "完成" == nodeContent || "确定" == nodeContent || "install" == nodeContent || "done" == nodeContent)) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    return true
                }
            } else if ("android.widget.ScrollView" == nodeInfo.className) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
            }
            for (i in 0 until childCount) {
                val childNodeInfo = nodeInfo.getChild(i)
                if (iterateNodesAndHandle(childNodeInfo)) {
                    return true
                }
            }
        }
        return false
    }

    override fun onInterrupt() {

    }
}