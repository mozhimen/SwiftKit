package com.mozhimen.componentk.navigatek

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.navigation.*
import androidx.navigation.fragment.DialogFragmentNavigator
import com.mozhimen.componentk.navigatek.annors.NavigateKType
import com.mozhimen.componentk.navigatek.helpers.NavigateKHelper

object NavigateK {

    fun <T> buildNavGraph(
        activity: FragmentActivity,
        containerId: Int,
        @NavigateKType navigateKType: Int = NavigateKType.FRAGMENT,
        vararg component: T
    ) {
        val navController = activity.findNavController(containerId)
        val childFragmentManager = activity.supportFragmentManager.findFragmentById(containerId)!!.childFragmentManager

        val iterator: Iterator<GuideKPkgPage> = pkgConfig.pkgPages.iterator()
        val navigatorProvider = navController.navigatorProvider

        val graphNavigator = navigatorProvider.getNavigator(NavGraphNavigator::class.java)
        val navGraph = NavGraph(graphNavigator)

        val navigateKHelper = NavigateKHelper(activity, childFragmentManager, containerId)
        navigatorProvider.addNavigator(navigateKHelper)
        while (iterator.hasNext()) {
            val page = iterator.next()
            when (navigateKType) {
                NavigateKType.ACTIVITY -> {
                    val navigator = navigatorProvider.getNavigator(ActivityNavigator::class.java)
                    val node: ActivityNavigator.Destination = navigator.createDestination()
                    node.id = page.pageInfo.id
                    node.setComponentName(ComponentName(activity.packageName, page.pageInfo.clazzName))
                    navGraph.addDestination(node)
                }
                NavigateKType.FRAGMENT -> {
                    val node = navigateKHelper.createDestination()
                    node.id = page.pageInfo.id
                    node.className = page.pageInfo.clazzName
                    navGraph.addDestination(node)
                }
                NavigateKType.DIALOG -> {
                    val navigator = navigatorProvider.getNavigator(
                        DialogFragmentNavigator::class.java
                    )
                    val node: DialogFragmentNavigator.Destination = navigator.createDestination()
                    node.id = page.pageInfo.id
                    node.setClassName(page.pageInfo.clazzName)
                    navGraph.addDestination(node)
                }
            }
        }
        if (pkgConfig.indexDefault in pkgConfig.pkgPages.indices) {
            navGraph.setStartDestination(pkgConfig.pkgPages.filter { page -> page.pageInfo.index == pkgConfig.indexDefault }[0].pageInfo.id)
        }
        controller.graph = navGraph
    }
}