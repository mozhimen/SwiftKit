package com.mozhimen.basick.utilk.androidx.core

import android.view.View
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * @ClassName UtilKViewCompat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/22
 * @Version 1.0
 */
object UtilKViewCompat {
    /**
     * setContentView(R.layout.activity_main)
     * ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
     *     val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
     *     v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
     *     insets
     * }
     */
    @JvmStatic
    fun applyWindowInsets(rootView: View) {
        applyOnApplyWindowInsetsListener(rootView) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @JvmStatic
    fun applyOnApplyWindowInsetsListener(view: View, listener: OnApplyWindowInsetsListener) {
        ViewCompat.setOnApplyWindowInsetsListener(view, listener)
    }
}