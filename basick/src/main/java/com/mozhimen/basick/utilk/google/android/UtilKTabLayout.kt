package com.mozhimen.basick.utilk.google.android

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.mozhimen.basick.elemk.google.android.commons.IOnTabSelectedListener
import com.mozhimen.basick.utilk.android.view.applyResizeSizeMax
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName UtilKTabLayout
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/28 16:35
 * @Version 1.0
 */
fun TabLayout.applyTabTextSize(unselectedTextSize: Float, selectedTextSize: Float) {
    UtilKTabLayout.applyTabTextSize(this, unselectedTextSize, selectedTextSize)
}

fun TabLayout.applyTabModeScrollable() {
    UtilKTabLayout.applyTabModeScrollable(this)
}

object UtilKTabLayout : IUtilK {
    @JvmStatic
    fun applyTabTextSize(tabLayout: TabLayout, unselectedTextSize: Float, selectedTextSize: Float) {
        tabLayout.addOnTabSelectedListener(object : IOnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val customView: View? = tab.customView
                if (customView == null) tab.setCustomView(TextView(tabLayout.context).apply {
                    id = android.R.id.text1
                    gravity = Gravity.CENTER
                    applyResizeSizeMax()
                })
                tab.customView?.findViewById<TextView>(android.R.id.text1)?.let {
                    it.setTextColor(tabLayout.tabTextColors)
                    it.typeface = Typeface.DEFAULT_BOLD
                    it.textSize = selectedTextSize
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val customView: View? = tab.customView
                if (customView == null) tab.setCustomView(TextView(tabLayout.context).apply {
                    id = android.R.id.text1
                    gravity = Gravity.CENTER
                    applyResizeSizeMax()
                })
                tab.customView?.findViewById<TextView>(android.R.id.text1)?.let {
                    it.setTextColor(tabLayout.tabTextColors)
                    it.typeface = Typeface.DEFAULT
                    it.textSize = unselectedTextSize
                }
            }
        })
    }

    @JvmStatic
    fun applyTabModeScrollable(tabLayout: TabLayout) {
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
    }
}