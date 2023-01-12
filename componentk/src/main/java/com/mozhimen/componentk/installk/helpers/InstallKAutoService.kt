package com.mozhimen.componentk.installk.helpers

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission

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
@AManifestKRequire(CPermission.BIND_ACCESSIBILITY_SERVICE)
class InstallKAutoService : AccessibilityService() {
    companion object {
        private const val TAG = "InstallKSmartService>>>>>"
    }

    private var _handledMap: MutableMap<Int, Boolean?> = HashMap()

    @SuppressLint("LongLogTag")
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (!event.packageName.toString().contains("packageinstaller")) {            //不写完整包名，是因为某些手机(如小米)安装器包名是自定义的
            return
        }

        val nodeInfo = event.source
        if (nodeInfo != null) {
            val eventType = event.eventType
            if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                if (_handledMap[event.windowId] == null) {
                    Log.d(TAG, "onAccessibilityEvent: nodeInfo $nodeInfo")
                    if (iterateNodesAndHandle(nodeInfo)) {
                        _handledMap[event.windowId] = true
                    } else {
                        Log.d(TAG, "onAccessibilityEvent: iterateNodesAndHandle false")
                    }
                }
            }
        } else {
//            Log.v(TAG, "onAccessibilityEvent: nodeInfo null")
//            performGlobalAction(GLOBAL_ACTION_RECENTS) // 打开最近页面
//            _handler.postDelayed({
//                performGlobalAction(GLOBAL_ACTION_BACK) // 返回安装页面
//            }, 1000)
        }

    }

    @SuppressLint("LongLogTag")
    private fun iterateNodesAndHandle(nodeInfo: AccessibilityNodeInfo?): Boolean {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount
            if (
                "android.widget.Button" == nodeInfo.className ||
                "androidx.appcompat.widget.AppCompatButton" == nodeInfo.className ||
                "com.google.android.material.button.MaterialButton" == nodeInfo.className ||
                "android.widget.TextView" == nodeInfo.className ||
                "androidx.appcompat.widget.AppCompatTextView" == nodeInfo.className ||
                "com.google.android.material.textview.MaterialTextView" == nodeInfo.className
            ) {
                val nodeContent = nodeInfo.text?.toString() ?: ""
                Log.v(TAG, "iterateNodesAndHandle button content $nodeContent")
                if (nodeContent.isNotEmpty() && nodeContent.length <= 4 && (
                            "安装" == nodeContent
//                                    ||
//                                    "完成" == nodeContent ||
//                                    "确定" == nodeContent ||
//                                    "继续" == nodeContent ||
//                                    "继续更新" == nodeContent ||
//                                    "install" == nodeContent ||
//                                    "done" == nodeContent
                            )
                ) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    return true
                }
            } else if (
                "android.widget.ScrollView" == nodeInfo.className ||
                "androidx.core.widget.NestedScrollView" == nodeInfo.className
            ) {
                Log.v(TAG, "iterateNodesAndHandle scrollview")
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