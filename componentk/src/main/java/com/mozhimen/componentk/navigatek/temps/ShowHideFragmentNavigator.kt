package com.mozhimen.componentk.navigatek.temps

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.componentk.navigatek.bases.BaseFragmentDestination
import com.mozhimen.componentk.navigatek.bases.BaseExtras
import com.mozhimen.componentk.navigatek.bases.BaseFragmentNavigator
import com.mozhimen.componentk.navigatek.cons.CNavigateKCons
import java.util.ArrayDeque


/**
 * @ClassName BaseNavigator
 * @Description 浅改造
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/25 10:50
 * @Version 1.0
 */
/**
 * Navigator that navigates through {@link FragmentTransaction fragment transactions}. Every
 * destination using this Navigator must set a valid Fragment class name with
 * <code>android:name</code> or {@link Destination#setClassName(String)}.
 * <p>
 * The current Fragment from FragmentNavigator's perspective can be retrieved by calling
 * {@link FragmentManager#getPrimaryNavigationFragment()} with the FragmentManager
 * passed to this FragmentNavigator.
 * <p>
 * Note that the default implementation does Fragment transactions
 * asynchronously, so the current Fragment will not be available immediately
 * (i.e., in callbacks to {@link NavController.OnDestinationChangedListener}).
 */
@Navigator.Name("navigatek_fragment")
open class ShowHideFragmentNavigator(
    context: Context,
    fragmentManager: FragmentManager,
    containerId: Int
) : BaseFragmentNavigator(context, fragmentManager, containerId) {
    private val _backStack = ArrayDeque<Int>()

    /**
     * {@inheritDoc}
     * <p>
     * This method must call
     * {@link FragmentTransaction#setPrimaryNavigationFragment(Fragment)}
     * if the pop succeeded so that the newly visible Fragment can be retrieved with
     * {@link FragmentManager#getPrimaryNavigationFragment()}.
     * <p>
     * Note that the default implementation pops the Fragment
     * asynchronously, so the newly visible Fragment from the back stack
     * is not instantly available after this call completes.
     */
    override fun popBackStack(): Boolean {
        if (_backStack.isEmpty()) {
            return false
        }
        if (fragmentManager.isStateSaved) {
            Log.d(TAG, "Ignoring popBackStack() call: FragmentManager has already saved its state")
            return false
        }
        fragmentManager.popBackStack(
            generateBackStackName(_backStack.size, _backStack.peekLast()), FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        _backStack.removeLast()
        return true
    }

    override fun createDestination(): BaseFragmentDestination {
        return BaseFragmentDestination(this)
    }

    /**
     * Instantiates the Fragment via the FragmentManager's
     * [FragmentFactory].
     *
     *
     * Note that this method is **not** responsible for calling
     * [Fragment.setArguments] on the returned Fragment instance.
     *
     * @param context         Context providing the correct [ClassLoader]
     * @param fragmentManager FragmentManager the Fragment will be added to
     * @param className       The Fragment to instantiate
     * @param args            The Fragment's arguments, if any
     * @return A new fragment instance.
     */
    @Suppress(CSuppress.DeprecatedCallableAddReplaceWith)
    @Deprecated(
        """Set a custom {@link FragmentFactory} via
      {@link FragmentManager#setFragmentFactory(FragmentFactory)} to control
      instantiation of Fragments."""
    )
    open fun instantiateFragment(
        context: Context,
        fragmentManager: FragmentManager,
        className: String,
        args: Bundle?
    ): Fragment {
        return fragmentManager.fragmentFactory.instantiate(UtilKContext.getClassLoader(context), className)
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method should always call
     * {@link FragmentTransaction#setPrimaryNavigationFragment(Fragment)}
     * so that the Fragment associated with the new destination can be retrieved with
     * {@link FragmentManager#getPrimaryNavigationFragment()}.
     * <p>
     * Note that the default implementation commits the new Fragment
     * asynchronously, so the new Fragment is not instantly available
     * after this call completes.
     */
    @SuppressWarnings("deprecation")
    /* Using instantiateFragment for forward compatibility */ /* Using instantiateFragment for forward compatibility */
    override fun navigate(
        destination: BaseFragmentDestination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ): NavDestination? {
        if (fragmentManager.isStateSaved) {
            Log.d(TAG, "Ignoring navigate() call: FragmentManager has already" + " saved its state")
            return null
        }

        val ft = createFragmentTransaction(destination, args, navOptions)

        ///////////////////////////////////////////////////////////////////////////////////////

        @IdRes val destId = destination.id
        val initialNavigation = _backStack.isEmpty()
        val isSingleTaskReplacement = !initialNavigation && _backStack.contains(destId)
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && _backStack.peekLast() == destId) // Build first class singleTop behavior for fragments
        val isAdded: Boolean
        if (initialNavigation) {
            isAdded = true
            Log.d(TAG, "navigate: initialNavigation")
        } else if (isSingleTaskReplacement) {
            isAdded = false
            Log.d(TAG, "navigate: isSingleTaskReplacement")
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (_backStack.size > 1) {
                // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                fragmentManager.popBackStack(generateBackStackName(_backStack.size, _backStack.peekLast()), FragmentManager.POP_BACK_STACK_INCLUSIVE)
                ft.addToBackStack(generateBackStackName(_backStack.size, destId))
            }
            isAdded = false
            Log.d(TAG, "navigate: isSingleTopReplacement")
        } else {
            ft.addToBackStack(generateBackStackName(_backStack.size + 1, destId))
            isAdded = true
            Log.d(TAG, "navigate: else")
        }

        if (navigatorExtras is BaseExtras) {
            for ((key, value)/*: Map.Entry<View, String>*/ in navigatorExtras.sharedElements) {
                ft.addSharedElement(key, value)
            }
        }
        ft.commit()
        // The commit succeeded, update our view of the world
        return if (isAdded) {
            _backStack.add(destId)
            Log.d(TAG, "navigate: " + _backStack.size)
            destination
        } else if (isSingleTaskReplacement) {
            destination
        } else {
            null
        }
    }

    open fun createFragmentTransaction(
        destination: BaseFragmentDestination,
        args: Bundle?,
        navOptions: NavOptions?
    ): FragmentTransaction {
        var className = destination.className
        if (className[0] == '.') {
            className = UtilKPackage.getPackageName() + className
        }

        //android.fragment.app.xxx->xxx
        val tag = className.substring(className.lastIndexOf(".") + 1)
        var frag = fragmentManager.findFragmentByTag(tag)
        if (frag == null) frag = instantiateFragment(context, fragmentManager, className, args)
        frag.arguments = args
        val ft = fragmentManager.beginTransaction()

        ///////////////////////////////////////////////////////////////////////////////

        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        //ft.replace(mContainerId, frag);
        val fragments = fragmentManager.fragments
        for (fragment in fragments) ft.hide(fragment!!)
        if (!frag.isAdded) ft.add(containerId, frag, tag)
        ft.show(frag)
        ft.setPrimaryNavigationFragment(frag)
        ft.setReorderingAllowed(true)
        return ft
    }

    override fun onSaveState(): Bundle? {
        val b = Bundle()
        val backStack = IntArray(_backStack.size)
        var index = 0
        for (id in _backStack) {
            backStack[index++] = id
        }
        b.putIntArray(CNavigateKCons.KEY_BACK_STACK_IDS, backStack)
        return b
    }

    override fun onRestoreState(savedState: Bundle) {
        val backStack = savedState.getIntArray(CNavigateKCons.KEY_BACK_STACK_IDS)
        if (backStack != null) {
            _backStack.clear()
            for (destId in backStack) {
                _backStack.add(destId)
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String {
        return "$backStackIndex-$destId"
    }
}