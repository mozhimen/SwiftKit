package com.mozhimen.bindk.impls.viewbinding

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mozhimen.bindk.bases.viewbinding.BaseDelegateVB

/**
 * @ClassName FragmentViewBindingDelegate
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/29 0:43
 * @Version 1.0
 */
class DelegateVBFragment<VB : ViewBinding>(
    clazz: Class<VB>,
    fragment: Fragment
) : BaseDelegateVB<Fragment, VB>(clazz, fragment) {

//    var viewBinding: VB? = null
//    val bindView = clazz.getMethod("bind", View::class.java)

//    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
//        return viewBinding?.run {
//            return this
//
//        } ?: let {
//
//            val bind = bindView.invoke(null, thisRef.view) as VB
//            return bind.apply { viewBinding = this }
//        }
//    }
}