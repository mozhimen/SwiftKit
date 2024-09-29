package com.mozhimen.bindk.helpers

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mozhimen.basick.utils.addObserverDestroy
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @ClassName FragmentViewBindingDelegate
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/29 0:43
 * @Version 1.0
 */
class FragmentViewBindingDelegate<T : ViewBinding>(
    classes: Class<T>,
    fragment: Fragment
) : ReadOnlyProperty<Fragment, T> {

    var viewBinding: T? = null
    val bindView = classes.getMethod("bind", View::class.java)

    init {
        fragment.lifecycle.addObserverDestroy { destroyed() }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return viewBinding?.run {
            return this

        } ?: let {

            val bind = bindView.invoke(null, thisRef.view) as T
            return bind.apply { viewBinding = this }
        }
    }

    private fun destroyed() {
        viewBinding = null
    }
}