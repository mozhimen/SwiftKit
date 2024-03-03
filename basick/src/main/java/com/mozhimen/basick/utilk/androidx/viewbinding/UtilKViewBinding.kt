package com.mozhimen.basick.utilk.androidx.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress
import com.mozhimen.basick.utilk.java.lang.UtilKReflectGenericKotlin

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
    fun <VB : ViewBinding> get(clazz: Class<*>, inflater: LayoutInflater/*, index: Int = 0*/): VB =
        UtilKReflectGenericKotlin.getParentGenericTypeByTClazz(clazz, ViewBinding::class.java)?.run {
            getDeclaredMethod("inflate", LayoutInflater::class.java).invoke(null, inflater) as VB
        } ?: throw Exception("inflate activity vb fail!")

    @JvmStatic
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VB : ViewBinding> get(clazz: Class<*>, inflater: LayoutInflater, container: ViewGroup?/*, index: Int = 0*/): VB =
        UtilKReflectGenericKotlin.getParentGenericTypeByTClazz(clazz, ViewBinding::class.java)?.run {
            getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java).invoke(null, inflater, container, false) as VB
        } ?: throw Exception("inflate fragment vb fail!")

}