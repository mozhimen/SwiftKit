package com.mozhimen.componentk.navigatek.bases

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.Navigator
import androidx.navigation.NavigatorProvider
import androidx.navigation.fragment.R
import com.mozhimen.basick.utilk.android.content.UtilKResource
import com.mozhimen.componentk.navigatek.temps.ShowHideFragmentNavigator


/**
 * @ClassName BaseDestination
 * @Description NavDestination specific to {@link androidx.navigation.fragment.FragmentNavigator}
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/25 11:19
 * @Version 1.0
 */
/**
 * Construct a new fragment destination. This destination is not valid until you set the
 * Fragment via {@link #setClassName(String)}.
 *
 * @param fragmentNavigator The {@link androidx.navigation.fragment.FragmentNavigator} which this destination
 *                          will be associated with. Generally retrieved via a
 *                          {@link NavController}'s
 *                          {@link NavigatorProvider#getNavigator(Class)} method.
 */
@NavDestination.ClassType(Fragment::class)
class BaseFragmentDestination(fragmentNavigator: BaseFragmentNavigator) : NavDestination(fragmentNavigator) {

    /**
     * Construct a new fragment destination. This destination is not valid until you set the
     * Fragment via {@link #setClassName(String)}.
     *
     * @param navigatorProvider The {@link NavController} which this destination
     *                          will be associated with.
     */
    constructor(navigatorProvider: NavigatorProvider) : this(navigatorProvider.getNavigator(BaseFragmentNavigator::class.java))

    @CallSuper
    override fun onInflate(context: Context, attrs: AttributeSet) {
        super.onInflate(context, attrs)
        UtilKResource.obtainAttributes(attrs, R.styleable.FragmentNavigator, context).use { array ->
            val className = array.getString(R.styleable.FragmentNavigator_android_name)
            className?.let { setClassName(it) }
        }
    }

    /**
     * Set the Fragment class name associated with this destination
     *
     * @param className The class name of the Fragment to show when you navigate to this
     *                  destination
     * @return this {@link Destination}
     */
    fun setClassName(className: String): BaseFragmentDestination {
        _className = className
        return this
    }

    private var _className: String? = null

    /**
     * Gets the Fragment's class name associated with this destination
     *
     * @throws IllegalStateException when no Fragment class was set.
     */
    val className: String
        get() {
            checkNotNull(_className) { "Fragment class was not set" }
            return _className as String
        }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(super.toString())
        sb.append(" class=")
        if (_className == null) {
            sb.append("null")
        } else {
            sb.append(_className)
        }
        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is BaseFragmentDestination) return false
        return super.equals(other) && _className == other._className
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + _className.hashCode()
        return result
    }
}