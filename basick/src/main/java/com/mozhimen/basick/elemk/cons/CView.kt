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
    object SystemUiFlag {
        /**
         * 字体白色
         */
        @ADescription("状态栏", "一般模式", "设置字体颜色")
        const val VISIBLE = View.SYSTEM_UI_FLAG_VISIBLE

        /**
         * 弱化状态栏和导航栏的图标
         * 设置状态栏和导航栏中的图标变小, 低调模式, 隐藏不重要的状态栏图标, 导航栏中相应的图标都变成了一个小点.点击状态栏或者导航栏还原成正常的状态
         */
        @ADescription("状态栏", "低调模式/颜色模式", "隐藏不重要的状态栏图标")
        const val LOW_PROFILE = View.SYSTEM_UI_FLAG_LOW_PROFILE

        /**
         * 隐藏导航栏, 用户点击屏幕会显示导航栏
         * 隐藏导航栏, 点击屏幕任意区域, 导航栏将重新出现, 并且不会自动消失
         * 但是用户的任何交互，都会导致此Flag被系统清除，进而导航栏自动重新显示，同时
         * @See FULLSCREEN 也会被自动清除，因此StatusBar也会同时显示出来
         */
        @ADescription("导航栏", "沉浸模式", "隐藏,点击屏幕才会显示导航栏")
        const val HIDE_NAVIGATION = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        /**
         * 隐藏状态栏
         * 隐藏状态栏, 点击屏幕区域不会出现, 需要从状态栏位置下拉才会出现
         * @see EGesture.PULL_DOWN_STATUS_BAR
         * @see EGesture.WINDOW_CHANGE 才会出现
         * @See CWinMgr.Lpf.FULLSCREEN 有相同视觉效果。不同在于，此Flag一般用在暂时需要全屏的情形（如：阅读应用，全屏视频等），以便让用户的注意力暂时集中在内容上，
         * 而如果只是简单的需要一直停留在全屏状态（如：游戏应用），使用
         * @See CWinMgr.Lpf.FULLSCREEN 则是更好的选择。
         * 此Flag会因为各种的交互（如：跳转到其他应用,下拉StatusBar，弹出键盘）的发送而被系统清除
         */
        @ADescription("状态栏", "沉浸模式", "隐藏,点击屏幕区域不会出现,状态栏位置下拉才会出现")
        const val FULLSCREEN = View.SYSTEM_UI_FLAG_FULLSCREEN//隐藏状态栏, 点击屏幕区域不会出现, 需要从状态栏位置下拉才会出现

        /**
         * 稳定的布局, 不会随系统栏的隐藏、显示而变化
         * 稳定布局, 主要是在全屏和非全屏切换时, 布局不要有大的变化.一般和
         * @See LAYOUT_FULLSCREEN
         * @See LAYOUT_HIDE_NAVIGATION
         * 搭配使用.同时 android:fitsSystemWindows=true
         */
        @ADescription("系统栏", "沉浸模式/全屏模式", "稳定的布局,不会随系统栏的隐藏/显示而变化", "须FLAG_LAYOUT_FULLSCREEN", "FLAG_LAYOUT_HIDE_NAVIGATION", "android:fitsSystemWindows要设置为true")
        const val LAYOUT_STABLE = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        /**
         * 拓展布局到导航栏后面
         * 当使用此Flag时，设置fitSystemWindow=true的view，会被系统自动添加大小为NavigationBar高度相同的paddingBottom。当window设置
         * @see CWinMgr.Lpf.TRANSLUCENT_NAVIGATION 时，此Flag会被系统会自动添加
         */
        @ADescription("系统栏", "沉浸模式/全屏模式", "将布局内容拓展到导航栏和状态栏的后面")
        const val LAYOUT_HIDE_NAVIGATION = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        /**
         * 拓展布局到状态栏后面
         * 当使用此Flag时，设置fitSystemWindow=true的view，会被系统自动添加大小为statusBar和ActionBar高度之和相同的paddingTop。当window设置
         * @see CWinMgr.Lpf.TRANSLUCENT_STATUS 时，此Flag会被系统会自动添加
         */
        @ADescription("状态栏", "沉浸模式/全屏模式", "将布局内容拓展到状态栏的后面")
        const val LAYOUT_FULLSCREEN = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        /**
         * 沉浸模式, 避免某些用户交互造成系统自动清除全屏状态
         * 使状态栏和导航栏真正的进入沉浸模式,即全屏模式, 如果没有设置这个标志, 设置全屏时, 我们点击屏幕的任意位置, 就会恢复为正常模式, 所以配合
         * @See FULLSCREEN
         * @see HIDE_NAVIGATION 一起使用的, 当使用FLAG_HIDE_NAVIGATION 隐藏导航栏时，配合此特性，只有
         * @see EGesture.PULL_UP_NAVIGATION_BAR,
         * @see EGesture.WINDOW_CHANGE 操作会导致导航栏的隐藏状态被系统自动清除；否则，任何交互都会导致导航栏的隐藏状态被系统自动清除。
         * 此标识只有配合FLAG_HIDE_NAVIGATION才有作用
         */
        @ADescription("系统栏", "沉浸模式")
        const val IMMERSIVE = View.SYSTEM_UI_FLAG_IMMERSIVE

        /**
         * 沉浸模式, 用户可以交互的界面.同时, 用户上下拉系统栏时, 会自动隐藏系统栏跟
         * @see IMMERSIVE 一样
         * 但是, 它在全屏模式下, 用户上下拉状态栏或者导航栏时, 这些系统栏只是以半透明的状态显示出来, 并且在一定时间后会自动消失, 用
         * @see HIDE_NAVIGATION 隐藏导航栏，配合使用此Flag,只有用户的
         * @see EGesture.WINDOW_CHANGE 第四种操作会导致状态栏或（和）导航栏的隐藏状态被系统自动清除。否则任何交互都会导致相应状态的清除。
         * 此Flag只有配合
         * @see FULLSCREEN
         * @see HIDE_NAVIGATION 使用时才会起作用
         */
        @ADescription("系统栏", "沉浸模式", "用户上下拉系统栏时, 会自动隐藏系统栏")
        const val IMMERSIVE_STICKY = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY


        /**
         * 设置状态栏的颜色
         * 在使用了FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS 并且没有使用 FLAG_TRANSLUCENT_STATUS的时候才有效，也就是只有在状态栏全透明的时候才有效
         * 另在一Activity多Fragment，各Fragment之间字体效果可能要求不同，需动态设置字体颜色
         *  if (isDark) getWindow().getDecorView().setSystemUiVisibility(CView.System.FLAG_LIGHT_STATUS_BAR);//黑色
         *  else getWindow().getDecorView().setSystemUiVisibility(CView.SystemUi.FLAG_VISIBLE);//白色
         */
        @ADescription("设置状态栏的颜色")
        @RequiresApi(CVersCode.V_23_6_M)
        const val LIGHT_STATUS_BAR = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        @ADescription("设置导航栏的颜色")
        @RequiresApi(CVersCode.V_26_8_O)
        const val LIGHT_NAVIGATION_BAR = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }
}