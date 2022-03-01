package com.mozhimen.abilitymk.hotfixmk;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName HotFixMK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/14 14:34
 * @Version 1.0
 */
public class HotFixMK {
    public static void fix(Context context, File patchDexFile) throws IllegalAccessException, InvocationTargetException {
        ClassLoader classLoader = context.getClassLoader();
        Field pathListField = findField(classLoader, "pathList");
        Object pathList = pathListField.get(classLoader);

        List<File> files = Arrays.asList(patchDexFile);
        Object[] pathDexElements = makeDexElements(pathList, files, classLoader);

        combineDexElements(pathList, pathDexElements);
    }

    private static void combineDexElements(Object pathList, Object[] patchDexElements) throws IllegalAccessException {
        Field dexElementsField = findField(pathList, "dexElements");
        Object[] original = (Object[]) dexElementsField.get(pathList);
        Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), patchDexElements.length + original.length);

        System.arraycopy(patchDexElements, 0, combined, 0, patchDexElements.length);
        System.arraycopy(original, 0, combined, patchDexElements.length, original.length);
        dexElementsField.set(pathList, combined);
    }

    private static Object[] makeDexElements(Object pathList, List<File> files, ClassLoader classLoader) throws InvocationTargetException, IllegalAccessException {
        Method method = findMethod(pathList, "makeDexElements", List.class, File.class, List.class, ClassLoader.class);
        if (method == null) return null;
        ArrayList<IOException> ioExceptions = new ArrayList<IOException>();
        return (Object[]) method.invoke(pathList, ioExceptions, null, ioExceptions, classLoader);
    }

    private static Method findMethod(Object instance, String methodName, Class<?>... parameterTypes) {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static Field findField(Object instance, String fieldName) {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
