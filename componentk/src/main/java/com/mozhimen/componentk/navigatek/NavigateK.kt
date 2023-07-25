package com.mozhimen.componentk.navigatek

import android.content.ComponentName
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.navigation.*
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.kotlin.collections.joinElement2List
import com.mozhimen.componentk.navigatek.cons.CNavigateKDestinationType
import com.mozhimen.componentk.navigatek.helpers.getDestinationId
import com.mozhimen.componentk.navigatek.mos.MNavigateKConfig
import com.mozhimen.componentk.navigatek.mos.MNavigateKPageInfo
import com.mozhimen.componentk.navigatek.temps.ShowHideFragmentNavigator
import java.util.*

class NavigateK(private val _activity: FragmentActivity) : BaseUtilK() {

    private var _mNavigateKConfig: MNavigateKConfig = MNavigateKConfig()

    fun setNavigateKConfig(config: MNavigateKConfig): NavigateK {
        _mNavigateKConfig = config
        return this
    }

    fun setupNavGraph(
        containerId: Int,
        clazzes: List<Class<*>>,
        defaultDestinationId: Int = 0
    ): NavController {
        if (clazzes.isEmpty()) throw Exception("clazzes must not be empty!")

        //1
        val navController = _activity.findNavController(containerId)
        /////////////////////////////////////////////////////////////////////////////

        //2
        val navigatorProvider =
            navController.navigatorProvider.apply {
                if (_mNavigateKConfig.isFragmentShowHide) {
                    addNavigator(ShowHideFragmentNavigator(_activity, _activity.supportFragmentManager.findFragmentById(containerId)!!.childFragmentManager, containerId))
                }
            }
        /////////////////////////////////////////////////////////////////////////////

        //3
        val navGraph = NavGraph(navigatorProvider.getNavigator(NavGraphNavigator::class.java))
        /////////////////////////////////////////////////////////////////////////////

        //4
        addDestinations(navGraph, navigatorProvider, clazzes, defaultDestinationId)
        /////////////////////////////////////////////////////////////////////////////

        //5
        navController.graph = navGraph
        /////////////////////////////////////////////////////////////////////////////
        return navController
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun addDestinations(navGraph: NavGraph, navigatorProvider: NavigatorProvider, clazzes: List<Class<*>>, defaultDestinationId: Int) {
        val pageInfos = createPageInfos(clazzes).also { Log.d(TAG, "addDestinations: $it") }
        for (pageInfo in pageInfos)
            addDestination(navGraph, createDestination(navigatorProvider, pageInfo) ?: continue)
        val destinationIds = pageInfos.joinElement2List { it.id }.also { Log.d(TAG, "addDestinations: defaultDestinationId $defaultDestinationId destinationIds $it") }
        navGraph.setStartDestination(if (defaultDestinationId != 0 && defaultDestinationId in destinationIds) defaultDestinationId else destinationIds[0])
    }

    private fun createDestination(navigatorProvider: NavigatorProvider, pageInfo: MNavigateKPageInfo): NavDestination? {
        return when (pageInfo.destType) {
            CNavigateKDestinationType.ACTIVITY -> {
                val destination: ActivityNavigator.Destination = navigatorProvider.getNavigator(ActivityNavigator::class.java).createDestination()
                destination.setComponentName(ComponentName(UtilKContext.getPackageName(_activity), pageInfo.clazzName))
                destination.id = pageInfo.id
                destination
            }

            CNavigateKDestinationType.FRAGMENT -> {
                val destination = if (_mNavigateKConfig.isFragmentShowHide) {
                    navigatorProvider.getNavigator(ShowHideFragmentNavigator::class.java).createDestination().apply { setClassName(pageInfo.clazzName) }
                } else navigatorProvider.getNavigator(FragmentNavigator::class.java).createDestination().apply { setClassName(pageInfo.clazzName) }
                destination.id = pageInfo.id
                destination
            }

            CNavigateKDestinationType.DIALOG -> {
                val destination: DialogFragmentNavigator.Destination = navigatorProvider.getNavigator(DialogFragmentNavigator::class.java).createDestination()
                destination.setClassName(pageInfo.clazzName)
                destination.id = pageInfo.id
                destination
            }

            else -> null
        }
    }

    private fun addDestination(navGraph: NavGraph, destination: NavDestination) {
        navGraph.addDestination(destination)
    }

    private fun createPageInfos(clazzes: List<Class<*>>): List<MNavigateKPageInfo> {
        val infos: ArrayList<MNavigateKPageInfo> = arrayListOf()
        for (clazz in clazzes) {
            val destType = getDestinationType(clazz) ?: throw Exception("this class is not fragment, dialog, or activity")
            val clazzName = clazz.name
            val id = clazz.getDestinationId()/* kotlin.math.abs(clazzName.hashCode())*/
            infos.add(MNavigateKPageInfo(destType, id, clazzName))
        }
        return infos
    }

    /**
     * 获取页面Type,Activity/Fragment/Dialog
     * @param clazz Class<*>
     * @return String?
     */
    private fun getDestinationType(clazz: Class<*>): String? {
        val superClazzName: String = clazz.superclass.toString()
        return when {
            superClazzName.contains(CNavigateKDestinationType.ACTIVITY) -> CNavigateKDestinationType.ACTIVITY
            superClazzName.contains(CNavigateKDestinationType.FRAGMENT) -> CNavigateKDestinationType.FRAGMENT
            superClazzName.contains(CNavigateKDestinationType.DIALOG) -> CNavigateKDestinationType.DIALOG
            else -> null
        }
    }
}