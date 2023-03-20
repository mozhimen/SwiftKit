package com.mozhimen.componentk.navigatek

import android.content.ComponentName
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.navigation.*
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import com.mozhimen.basick.utilk.content.UtilKContext
import com.mozhimen.componentk.navigatek.mos.MNavigateKPageInfo
import java.util.*

object NavigateK {

    private const val TAG = "NavigateK>>>>>"
    private const val dest_type_activity = "Activity"
    private const val dest_type_fragment = "Fragment"
    private const val dest_type_dialog = "Dialog"

    fun getId(clazz: Class<*>): Int {
        return kotlin.math.abs(clazz.name.hashCode())
    }

    fun buildNavGraph(
        activity: FragmentActivity,
        containerId: Int,
        clazzes: List<Class<*>>,
        defaultFragmentId: Int = 0
    ): NavController {
        val navController = activity.findNavController(containerId)
        //val childFragmentManager = activity.supportFragmentManager.findFragmentById(containerId)!!.childFragmentManager
        val pageInfos = clazzes2PageInfos(clazzes)
        Log.d(TAG, "buildNavGraph: $pageInfos")
        val iterator: Iterator<MNavigateKPageInfo> = pageInfos.iterator()

        val navigatorProvider = navController.navigatorProvider
        val graphNavigator = navigatorProvider.getNavigator(NavGraphNavigator::class.java)
        val navGraph = NavGraph(graphNavigator)

//        val navigateKHelper = NavigateKHelper(activity, childFragmentManager, containerId)
//        navigatorProvider.addNavigator(navigateKHelper)
        while (iterator.hasNext()) {
            val page = iterator.next()
            when (page.destType) {
                dest_type_activity -> {
                    val navigator = navigatorProvider.getNavigator(ActivityNavigator::class.java)
                    val node: ActivityNavigator.Destination = navigator.createDestination()
                    node.id = page.id
                    node.setComponentName(ComponentName(UtilKContext.getPackageName(activity), page.clazzName))
                    navGraph.addDestination(node)
                }
                dest_type_fragment -> {
                    val navigator = navigatorProvider.getNavigator(FragmentNavigator::class.java)
                    val node: FragmentNavigator.Destination = navigator.createDestination()
                    node.id = page.id
                    node.setClassName(page.clazzName)
                    navGraph.addDestination(node)
                }
                dest_type_dialog -> {
                    val navigator = navigatorProvider.getNavigator(DialogFragmentNavigator::class.java)
                    val node: DialogFragmentNavigator.Destination = navigator.createDestination()
                    node.id = page.id
                    node.setClassName(page.clazzName)
                    navGraph.addDestination(node)
                }
            }
        }
        val fragmentIds = arrayListOf<Int>()
        clazzes.forEach {
            fragmentIds.add(it.hashCode())
        }
        Log.d(TAG, "buildNavGraph: defaultFragmentId $defaultFragmentId fragmentIds $fragmentIds")
        navGraph.setStartDestination(if (defaultFragmentId != 0 && defaultFragmentId in fragmentIds) defaultFragmentId else pageInfos[0].id)
        navController.graph = navGraph
        return navController
    }

    private fun clazzes2PageInfos(clazzes: List<Class<*>>): List<MNavigateKPageInfo> {
        val navigateKPageInfos: ArrayList<MNavigateKPageInfo> = arrayListOf()
        clazzes.forEach {
            val destType = getDestinationType(it) ?: throw Exception("this class is not fragment, dialog, or activity")
            val clazzName = it.name
            val id = kotlin.math.abs(clazzName.hashCode())
            navigateKPageInfos.add(MNavigateKPageInfo(destType, id, clazzName))
        }
        return navigateKPageInfos
    }

    /**
     * 获取页面Type,Activity/Fragment/Dialog
     * @param clazz Class<*>
     * @return String?
     */
    private fun getDestinationType(clazz: Class<*>): String? {
        val superClazzName: String = clazz.superclass.toString()
        return when {
            superClazzName.contains(dest_type_activity) -> {
                dest_type_activity
            }
            superClazzName.contains(dest_type_fragment) -> {
                dest_type_fragment
            }
            superClazzName.contains(dest_type_dialog) -> {
                dest_type_dialog
            }
            else -> null
        }
    }
}