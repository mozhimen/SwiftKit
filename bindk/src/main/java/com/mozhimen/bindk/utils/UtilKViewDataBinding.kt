package com.mozhimen.bindk.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.mozhimen.kotlin.elemk.kotlin.cons.CSuppress
import com.mozhimen.kotlin.utilk.java.lang.UtilKMethod
import com.mozhimen.kotlin.utilk.java.lang.UtilKType
import kotlin.Exception

/**
 * @ClassName UtilKViewDataBinding
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 22:19
 * @Version 1.0
 */
object UtilKViewDataBinding {
    @JvmStatic
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VDB : ViewDataBinding> get_ofClass(clazz: Class<*>, layoutInflater: LayoutInflater/*, index: Int = 0*/): VDB =
        UtilKType.getClass_ofParent(clazz, ViewDataBinding::class.java)?.run {
            get<VDB>(this, layoutInflater)
        } ?: throw Exception("inflate activity vb fail!")

    @JvmStatic
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VDB : ViewDataBinding> get_ofClass(clazz: Class<*>, layoutInflater: LayoutInflater, container: ViewGroup?/*, index: Int = 0*/): VDB =
        UtilKType.getClass_ofParent(clazz, ViewDataBinding::class.java)?.run {
            get<VDB>(this, layoutInflater, container)
        } ?: throw Exception("inflate fragment vb fail!")

    @JvmStatic
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VDB : ViewDataBinding> get(clazzVDB: Class<*>, layoutInflater: LayoutInflater): VDB =
//        clazzVDB.getDeclaredMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VDB
        UtilKMethod.getDeclared_invoke_throw(clazzVDB, "inflate", arrayOf(LayoutInflater::class.java), null, layoutInflater) as VDB

    @JvmStatic
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VDB : ViewDataBinding> get(clazzVDB: Class<*>, layoutInflater: LayoutInflater, container: ViewGroup?/*, index: Int = 0*/): VDB =
//        clazzVDB.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java).invoke(null, layoutInflater, container, false) as VDB
        UtilKMethod.getDeclared_invoke_throw(clazzVDB, "inflate", arrayOf(LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java), null, layoutInflater, container, false) as VDB
}