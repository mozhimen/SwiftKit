package com.mozhimen.basick.utilk.google.material

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.core.view.contains
import androidx.core.view.indices
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @ClassName UtilKBottomNavigationView
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/9 23:14
 * @Version 1.0
 */
object UtilKBottomNavigationView {

    @SuppressLint("RestrictedApi")
    @JvmStatic
    fun getBottomNavigationMenuView(bottomNavigationView: BottomNavigationView): BottomNavigationMenuView =
        bottomNavigationView.getChildAt(0) as BottomNavigationMenuView//获取底部菜单view

    @SuppressLint("RestrictedApi")
    @JvmStatic
    fun getBottomNavigationItemView(bottomNavigationMenuView: BottomNavigationMenuView, index: Int): BottomNavigationItemView? =
        if (index in bottomNavigationMenuView.indices) {
            bottomNavigationMenuView.getChildAt(index) as BottomNavigationItemView
        } else null

//    @SuppressLint("RestrictedApi")
//    @JvmStatic
//    fun applyBottomNavigationItemViewAddOrSetBadge(bottomNavigationView: BottomNavigationView, textView: TextView, index: Int, count: Int) {
//        val bottomNavigationMenuView = getBottomNavigationMenuView(bottomNavigationView)
//        val bottomNavigationItemView = getBottomNavigationItemView(bottomNavigationMenuView, index) ?: return//获取第2个itemView
//        val badgeView = if (bottomNavigationItemView.contains(textView)) {
//            bottomNavigationItemView.findViewById(textView.hashCode())
//        } else {
//            bottomNavigationItemView.addView(textView.apply {
//                id = textView.hashCode()
//            })//把badgeView添加到itemView中
//            textView
//        }
//        badgeView.text = count.toString()
//        badgeView.visibility = if (count > 0) View.VISIBLE else View.GONE
//    }
}