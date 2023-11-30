package com.mozhimen.basicktest.utilk.java;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.storage.StorageManager;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

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

    public static String[] reflect(Context paramContext) {
        String[] arrayOfString;
        byte b = 0;
        StorageManager storageManager = (StorageManager) paramContext.getSystemService("storage");
        Class<?> clazz = (Class) null;
        try {
            clazz = Class.forName("android.os.storage.StorageVolume");
            Method method2 = storageManager.getClass().getMethod("getVolumeList", new Class[0]);
            Method method1 = clazz.getMethod("getPath", new Class[0]);
            Object object = method2.invoke(storageManager, new Object[0]);
            int i = Array.getLength(object);
            arrayOfString = new String[i];
            while (true) {
                if (b < i) {
                    arrayOfString[b] = (String) method1.invoke(Array.get(object, b), new Object[0]);
                    b++;
                    continue;
                }
                return arrayOfString;
            }
        } catch (Exception exception) {
            arrayOfString = (String[]) null;
        }
        return arrayOfString;
    }
}
