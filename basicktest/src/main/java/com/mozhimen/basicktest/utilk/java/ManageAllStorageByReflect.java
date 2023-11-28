package com.mozhimen.basicktest.utilk.java;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * @ClassName ManageAllStorgeByReflect
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/11/28 17:10
 * @Version 1.0
 */
public class ManageAllStorageByReflect {
    public static boolean reflect() {
        boolean bool = true;
        if (Build.VERSION.SDK_INT > 29)
            try {
                Class<?> clazz = Class.forName("android.os.Environment");
                Method method = Class.forName(clazz.getName()).getDeclaredMethod("isExternalStorageManager", new Class[0]);
                method.setAccessible(true);
                bool = ((Boolean) method.invoke(null, new Object[0])).booleanValue();
            } catch (ClassNotFoundException classNotFoundException) {
//                NoClassDefFoundError noClassDefFoundError = new NoClassDefFoundError();
//                this(classNotFoundException.getMessage());
//                throw noClassDefFoundError;
                classNotFoundException.printStackTrace();
            } catch (Exception exception) {
                Log.d("ManageAllStorage>>>>>", "reflect: xxxxxxxxxxxxxxxxxxxxx");
                exception.printStackTrace();
                bool = false;
            }
        Log.d("ManageAllStorage>>>>>", "reflect: " + bool);
        return bool;
    }
}
