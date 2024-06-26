package com.mozhimen.basick.utilk.androidx.core

import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * @ClassName UtilKWindowCompat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/26 22:28
 * @Version 1.0
 */
object UtilKWindowCompat {
    @JvmStatic
    fun getWindowInsetsController(window: Window): WindowInsetsControllerCompat =
        WindowCompat.getInsetsController(window, window.decorView)

    @JvmStatic
    fun hideSystemBars(window: Window) {
        getWindowInsetsController(window).hide(UtilKWindowInsetsCompat.getSystemBars())
//        // Configure the behavior of the hidden system bars.
//        windowInsetsController.systemBarsBehavior =
//            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//
//        // Add a listener to update the behavior of the toggle fullscreen button when
//        // the system bars are hidden or revealed.
//        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->
//            // You can hide the caption bar even when the other system bars are visible.
//            // To account for this, explicitly check the visibility of navigationBars()
//            // and statusBars() rather than checking the visibility of systemBars().
//            if (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars()) || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())) {
//                binding.toggleFullscreenButton.setOnClickListener {
//                    // Hide both the status bar and the navigation bar.
//                    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
//                }
//            } else {
//                binding.toggleFullscreenButton.setOnClickListener {
//                    // Show both the status bar and the navigation bar.
//                    windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
//                }
//            }
//            ViewCompat.onApplyWindowInsets(view, windowInsets)
//        }
    }
}