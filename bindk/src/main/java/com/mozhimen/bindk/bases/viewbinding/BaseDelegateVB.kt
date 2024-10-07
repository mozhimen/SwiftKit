package com.mozhimen.bindk.bases.viewbinding

import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.mozhimen.basick.utils.addObserverOnDestroy
import com.mozhimen.bindk.utils.UtilKViewBinding
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.kotlin.utilk.java.lang.UtilKMethod
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @ClassName BaseViewBindingDelegate
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/10/6 20:16
 * @Version 1.0
 */
abstract class BaseDelegateVB<L : LifecycleOwner, VB : ViewBinding>(
    protected val _clazz: Class<VB>,
    protected var _obj: L?
) : ReadOnlyProperty<L, VB?>, IUtilK {
    protected var _viewBinding: VB? = null

    init {
        _obj!!.lifecycle.addObserverOnDestroy(::recycle)
    }

    ///////////////////////////////////////////////////////////////////////

    open fun getLayoutInflater(thisRef: L): LayoutInflater =
        UtilKMethod.getDeclared_invoke_throw(_obj!!, "getLayoutInflater", arrayOf(), thisRef) as? LayoutInflater ?: throw NullPointerException("getLayoutInflater is null")

    open fun getViewBinding(thisRef: L): VB =
        UtilKViewBinding.get(_clazz, getLayoutInflater(thisRef))

    ///////////////////////////////////////////////////////////////////////

    override operator fun getValue(thisRef: L, property: KProperty<*>): VB {
        if (_viewBinding != null)
            return _viewBinding!!
        val viewBinding =
            try {
                _obj ?: throw NullPointerException("_obj is null")
                getViewBinding(thisRef)
            } catch (e: Exception) {
                UtilKLogWrapper.e(TAG, "getValue: ", e)
                throw e
            }
//            thisRef.setContentView(bind.root)
        return viewBinding.also { _viewBinding = it }
    }

    ///////////////////////////////////////////////////////////////////////

    @CallSuper
    protected open fun recycle() {
        _viewBinding = null
        _obj = null
    }
}