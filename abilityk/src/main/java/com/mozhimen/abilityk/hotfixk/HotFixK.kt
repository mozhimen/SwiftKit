package com.mozhimen.abilityk.hotfixk

import android.content.Context
import android.util.Log
import com.mozhimen.abilityk.hotfixk.helpers.DexConverter
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.basick.utilk.UtilKReflect
import java.io.File
import java.io.IOException
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.ArrayList

/**
 * @ClassName HotFixK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/20 12:44
 * @Version 1.0
 */
object HotFixK {
    private const val TAG = "HotFixK>>>>>"

    @Throws(IllegalAccessException::class, NoSuchFieldException::class, InvocationTargetException::class, NoSuchMethodException::class)
    fun fix(context: Context, dexFilePath: String) {
        val loader: ClassLoader = context.classLoader
        val pathListField: Field = UtilKReflect.findField(loader, "pathList")
        val dexPathList: Any = pathListField[loader] ?: kotlin.run {
            Log.e(TAG, "fix: findField-pathList fail")
            return
        }
        val files = ArrayList<File>()
        files.add(File(dexFilePath))
        val patchDexElements: Array<Any> = makeDexElements(dexPathList, files, loader)
        expandFieldArray(dexPathList, patchDexElements)
        DexConverter.triggerPMDexOptOnDemand(context, dexFilePath, UtilKFile.optimizedPathFor(File(dexFilePath)))
    }

    @Throws(InvocationTargetException::class, IllegalAccessException::class, NoSuchMethodException::class)
    private fun makeDexElements(
        dexPathList: Any,
        files: ArrayList<File>,
        loader: ClassLoader
    ): Array<Any> {
        val makeDexElements: Method = UtilKReflect.findMethod(
            dexPathList, "makeDexElements",
            MutableList::class.java,
            File::class.java,
            MutableList::class.java,
            ClassLoader::class.java
        )
        val exceptions: ArrayList<IOException> = ArrayList<IOException>()
        return makeDexElements.invoke(dexPathList, files, null, exceptions, loader) as Array<Any>
    }

    @Throws(IllegalAccessException::class, NoSuchFieldException::class)
    private fun expandFieldArray(instance: Any, extraElements: Array<Any>) {
        val dexElementsFiled: Field = UtilKReflect.findField(instance, "dexElements")
        val original: Array<Any> = dexElementsFiled[instance] as Array<Any>
        //构建新的element数组，先把修复bug的dexElement数组放进去，再把原来的放进去，最后更改到dexPathList对象的dexElement
        //getComponentType得到数组中元素的类型 ，我们知道是Element,但是无法直接访问到该类。
        val combined = java.lang.reflect.Array.newInstance(original.javaClass.componentType!!, original.size + extraElements.size) as Array<Any>
        System.arraycopy(extraElements, 0, combined, 0, extraElements.size)
        System.arraycopy(original, 0, combined, extraElements.size, original.size)
        dexElementsFiled[instance] = combined
    }
}