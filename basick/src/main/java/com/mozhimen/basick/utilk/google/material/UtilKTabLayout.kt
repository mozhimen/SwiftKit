package com.mozhimen.basick.utilk.google.material

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.mozhimen.basick.elemk.google.material.commons.IOnTabSelectedListener
import com.mozhimen.basick.utilk.android.view.applyLayoutParams_ofMatch
import com.mozhimen.basick.utilk.android.widget.applyTypeface_ofBold
import com.mozhimen.basick.utilk.commons.IUtilK

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

fun TabLayout.applyTabMode_ofScrollable() {
    UtilKTabLayout.applyTabMode_ofScrollable(this)
}

object UtilKTabLayout : IUtilK {
    @JvmStatic
    fun applyTabTextSize(tabLayout: TabLayout, unselectedTextSize: Float, selectedTextSize: Float) {
        addOnTabSelectedListener(tabLayout, object : IOnTabSelectedListener {
            override fun onTabSelected(tab: Tab) {
                val customView: View? = tab.customView
                if (customView == null) tab.setCustomView(TextView(tabLayout.context).apply {
                    id = android.R.id.text1
                    gravity = Gravity.CENTER
                    applyLayoutParams_ofMatch()
                })
                tab.customView?.findViewById<TextView>(android.R.id.text1)?.let {
                    it.setTextColor(tabLayout.tabTextColors)
                    it.typeface = Typeface.DEFAULT_BOLD
                    it.textSize = selectedTextSize
                }
            }

            override fun onTabUnselected(tab: Tab) {
                val customView: View? = tab.customView
                if (customView == null) tab.setCustomView(TextView(tabLayout.context).apply {
                    id = android.R.id.text1
                    gravity = Gravity.CENTER
                    applyLayoutParams_ofMatch()
                })
                tab.customView?.findViewById<TextView>(android.R.id.text1)?.let {
                    it.setTextColor(tabLayout.tabTextColors)
                    it.applyTypeface_ofBold()
                    it.typeface = Typeface.DEFAULT
                    it.textSize = unselectedTextSize
                }
            }
        })
    }

    @JvmStatic
    fun applyTabMode_ofScrollable(tabLayout: TabLayout) {
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
    }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun addOnTabSelectedListener(tabLayout: TabLayout, listener: TabLayout.OnTabSelectedListener) {
        tabLayout.addOnTabSelectedListener(listener)
    }
}