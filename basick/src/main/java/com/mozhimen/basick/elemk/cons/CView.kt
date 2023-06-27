package com.mozhimen.basick.elemk.cons

import android.view.View
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.annors.ADescription

/**
 * @ClassName CView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 23:24
 * @Version 1.0
 */
object CView {
    object SystemUi {
        const val FLAG_VISIBLE = View.SYSTEM_UI_FLAG_VISIBLE

        //弱化状态栏和导航栏的图标-->
        // 设置状态栏和导航栏中的图标变小,低调模式，隐藏不重要的状态栏图标，导航栏中相应的图标都变成了一个小点。点击状态栏或者导航栏还原成正常的状态
        const val FLAG_LOW_PROFILE = View.SYSTEM_UI_FLAG_LOW_PROFILE

        //隐藏导航栏，用户点击屏幕会显示导航栏-->
        // 隐藏导航栏，点击屏幕任意区域，导航栏将重新出现，并且不会自动消失
        const val FLAG_HIDE_NAVIGATION = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        //隐藏状态栏-->
        // 隐藏状态栏，点击屏幕区域不会出现，需要从状态栏位置下拉才会出现
        const val FLAG_FULLSCREEN = View.SYSTEM_UI_FLAG_FULLSCREEN//隐藏状态栏，点击屏幕区域不会出现，需要从状态栏位置下拉才会出现

        //稳定的布局，不会随系统栏的隐藏、显示而变化-->
        // 稳定布局，主要是在全屏和非全屏切换时，布局不要有大的变化。一般和View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN、View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION搭配使用。同时，android:fitsSystemWindows要设置为true
        @ADescription("FLAG_LAYOUT_FULLSCREEN", "FLAG_LAYOUT_HIDE_NAVIGATION", "android:fitsSystemWindows要设置为true")
        const val FLAG_LAYOUT_STABLE = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        //拓展布局到导航栏后面-->
        // 将布局内容拓展到导航栏和状态栏的后面
        const val FLAG_LAYOUT_HIDE_NAVIGATION = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        //拓展布局到状态栏后面-->
        // 将布局内容拓展到状态栏的后面
        const val FLAG_LAYOUT_FULLSCREEN = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        //沉浸模式，用户可以交互的界面-->
        //使状态栏和导航栏真正的进入沉浸模式,即全屏模式，如果没有设置这个标志，设置全屏时，我们点击屏幕的任意位置，就会恢复为正常模式
        //所以，View.SYSTEM_UI_FLAG_IMMERSIVE都是配合View.SYSTEM_UI_FLAG_FULLSCREEN和View.SYSTEM_UI_FLAG_HIDE_NAVIGATION一起使用的
        //在没有设置View.SYSTEM_UI_FLAG_IMMERSIVE时，随便点击屏幕就可以解除隐藏导航栏的状态。所以，设置View.SYSTEM_UI_FLAG_IMMERSIVE就是真正进入沉浸模式
        const val FLAG_IMMERSIVE = View.SYSTEM_UI_FLAG_IMMERSIVE

        //沉浸模式，用户可以交互的界面。同时，用户上下拉系统栏时，会自动隐藏系统栏-->
        //跟View.SYSTEM_UI_FLAG_IMMERSIVE一样
        //但是，它在全屏模式下，用户上下拉状态栏或者导航栏时，这些系统栏只是以半透明的状态显示出来，并且在一定时间后会自动消失
        const val FLAG_IMMERSIVE_STICKY = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        //设置状态栏的颜色
        @RequiresApi(CVersionCode.V_23_6_M)
        const val FLAG_LIGHT_STATUS_BAR = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        @RequiresApi(CVersionCode.V_26_8_O)
        const val FLAG_LIGHT_NAVIGATION_BAR = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }
}