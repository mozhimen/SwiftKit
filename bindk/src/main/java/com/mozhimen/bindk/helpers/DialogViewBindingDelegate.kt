package com.mozhimen.bindk.helpers

import android.app.Dialog
import android.view.LayoutInflater
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.mozhimen.basick.utils.addObserverDestroy
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @ClassName DialogViewBindingDelegate
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/29 0:38
 * @Version 1.0
 */
class DialogViewBindingDelegate<T : ViewBinding>(
    classes: Class<T>,
    lifecycle: Lifecycle? = null
) : ReadOnlyProperty<Dialog, T> {

    private var viewBinding: T? = null
    private var layoutInflater = classes.getMethod("inflate", LayoutInflater::class.java)

    init {
        lifecycle?.addObserverDestroy { destroyed() }
    }

    override fun getValue(thisRef: Dialog, property: KProperty<*>): T {
        return viewBinding?.run {
            this

        } ?: let {

            val bind = layoutInflater.invoke(null, thisRef.layoutInflater) as T
            thisRef.setContentView(bind.root)
            return bind.apply { viewBinding = this }
        }

    }

    private fun destroyed() {
        viewBinding = null
    }
}