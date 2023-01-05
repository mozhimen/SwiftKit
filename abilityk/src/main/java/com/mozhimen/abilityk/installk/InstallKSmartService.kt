package com.mozhimen.abilityk.installk

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * @ClassName InstallKSmartService
 * @Description you must register service

 * AndroidManifest.xml
    <service
    android:name=".installk.InstallKSmartService"
    android:label="HotupdateK程序更新"
    android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
    android:exported="true">
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

class InstallKSmartService : AccessibilityService() {
    companion object {
        private const val TAG = "InstallKSmartService>>>>>"
    }

    private var _handledMap: MutableMap<Int, Boolean?> = HashMap()

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val nodeInfo = event.source
        if (nodeInfo != null) {
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
    }

    @SuppressLint("LongLogTag")
    private fun iterateNodesAndHandle(nodeInfo: AccessibilityNodeInfo?): Boolean {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount
            if ("android.widget.Button" == nodeInfo.className) {
                val nodeContent = nodeInfo.text.toString()
                Log.d(TAG, "content is $nodeContent")
                if ("安装" == nodeContent || "完成" == nodeContent || "确定" == nodeContent) {
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