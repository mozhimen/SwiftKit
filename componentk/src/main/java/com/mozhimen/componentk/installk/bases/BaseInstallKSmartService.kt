package com.mozhimen.componentk.installk.bases

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName InstallKSmartService
 * @Description you must register service

 * AndroidManifest.xml (example)

<service
android:name="com.mozhimen.componentk.installk.bases.BaseInstallKSmartService"
android:enabled="true"
android:exported="true"
android:label="@string/install_label"
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
@RequiresApi(CVersCode.V_16_41_J)
open class BaseInstallKSmartService : AccessibilityService(), IUtilK {
//    private var _handledMap: MutableMap<Int, Boolean?> = HashMap()

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val strPackageName = event.packageName.toString()
        if (!strPackageName.contains("packageinstaller") && !strPackageName.contains("accessibility") && !strPackageName.contains("settings")) return

        val nodeInfo = event.source
        if (nodeInfo != null) {
            val eventType = event.eventType
            if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                Log.d(TAG, "onAccessibilityEvent: nodeInfo packageName ${nodeInfo.packageName} clazzName ${nodeInfo.className}")
                iterateNodesAndHandle(nodeInfo)
            }
        } else {
//            performGlobalAction(GLOBAL_ACTION_RECENTS) // 打开最近页面
//            _handler.postDelayed({
//                performGlobalAction(GLOBAL_ACTION_BACK) // 返回安装页面
//            }, 1000)
        }

    }

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
                            "更新" == nodeContent || "设置" == nodeContent ||//SAMSUNG
                                    "继续" == nodeContent || "继续更新" == nodeContent ||//XIAOMI
                                    "重新安装" == nodeContent || "继续安装" == nodeContent || "安装" == nodeContent ||//OPPO
                                    "打开" == nodeContent || "打开应用" == nodeContent || "完成" == nodeContent
//                                    ||
//                                    "完成" == nodeContent ||
//                                    "确定" == nodeContent ||
//                                    "继续" == nodeContent ||
//                                    "继续更新" == nodeContent ||
//                                    "install" == nodeContent ||
//                                    "done" == nodeContent
                            )
                ) {
                    if (("android.widget.Button" == nodeInfo.className ||
                                "androidx.appcompat.widget.AppCompatButton" == nodeInfo.className ||
                                "com.google.android.material.button.MaterialButton" == nodeInfo.className)
                    ) {
                        if (nodeInfo.isEnabled && nodeInfo.isClickable) {
                            Log.v(TAG, "iterateNodesAndHandle button ${nodeInfo.text} click")
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            return true
                        } else {
                            Log.v(TAG, "iterateNodesAndHandle button click disable")
                        }
                    } else if (
                        "android.widget.TextView" == nodeInfo.className ||
                        "androidx.appcompat.widget.AppCompatTextView" == nodeInfo.className ||
                        "com.google.android.material.textview.MaterialTextView" == nodeInfo.className
                    ) {
                        if (nodeInfo.isEnabled && nodeInfo.isClickable) {
                            Log.v(TAG, "iterateNodesAndHandle textview ${nodeInfo.text} click")
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            return true
                        } else {
                            Log.v(TAG, "iterateNodesAndHandle textview click disable")
                            if (nodeInfo.parent != null) {
                                Log.v(TAG, "iterateNodesAndHandle textview parent ${nodeInfo.text} click")
                                nodeInfo.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                return true
                            } else {
                                Log.v(TAG, "iterateNodesAndHandle textview parent null")
                            }
                        }
                    }

                }
            } else if (
                "android.widget.ScrollView" == nodeInfo.className ||
                "androidx.core.widget.NestedScrollView" == nodeInfo.className
            ) {
                Log.v(TAG, "iterateNodesAndHandle scrollview forward")
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