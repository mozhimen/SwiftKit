package com.mozhimen.bindk.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.mozhimen.kotlin.elemk.kotlin.cons.CSuppress
import com.mozhimen.kotlin.utilk.java.lang.UtilKMethod
import com.mozhimen.kotlin.utilk.java.lang.UtilKType

/**
 * @ClassName UtilKViewBinding
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/2 15:47
 * @Version 1.0
 */
object UtilKViewBinding {
    @JvmStatic
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VB : ViewBinding> get_ofClass(clazz: Class<*>, layoutInflater: LayoutInflater/*, index: Int = 0*/): VB =
        (UtilKType.getClass_ofParent(clazz, ViewBinding::class.java) as? Class<VB>)?.run {
            get<VB>(this, layoutInflater)
        } ?: throw Exception("inflate activity vb fail!")

    @JvmStatic
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VB : ViewBinding> get_ofClass(clazz: Class<*>, layoutInflater: LayoutInflater, container: ViewGroup?/*, index: Int = 0*/): VB =
        (UtilKType.getClass_ofParent(clazz, ViewBinding::class.java) as? Class<VB>)?.run {
            get<VB>(this, layoutInflater, container)
        } ?: throw Exception("inflate fragment vb fail!")

    @JvmStatic
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VB : ViewBinding> get(clazzVB: Class<VB>, layoutInflater: LayoutInflater): VB =
//        clazzVB.getDeclaredMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB
        UtilKMethod.getDeclared_invoke_throw(clazzVB, "inflate", arrayOf(LayoutInflater::class.java), null, layoutInflater) as VB

    @JvmStatic
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VB : ViewBinding> get(clazzVB: Class<VB>, layoutInflater: LayoutInflater, container: ViewGroup?): VB =
//        clazzVB.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java).invoke(null, layoutInflater, container, false) as VB
        UtilKMethod.getDeclared_invoke_throw(clazzVB, "inflate", arrayOf(LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java), null, layoutInflater, container, false) as VB
}