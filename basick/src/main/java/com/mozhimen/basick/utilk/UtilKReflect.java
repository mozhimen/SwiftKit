package com.mozhimen.basick.utilk;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName UtilKReflect
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/20 16:59
 * @Version 1.0
 */
public class UtilKReflect {
    private static final String TAG = "UtilKReflect>>>>>";

    /**
     * 获取类及其基类所有的field
     *
     * @param instance
     * @return
     */
    public static List<Field> getAllFields(Object instance) {
        return getAllFields(instance.getClass());
    }

    /**
     * 获取类及其基类所有的field
     *
     * @param clazz
     * @return
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> superClass = clazz.getSuperclass();
        while (superClass != null) {
            Field[] superFields = superClass.getDeclaredFields();
            fields.addAll(Arrays.asList(superFields));
            superClass = superClass.getSuperclass();
        }
        return fields;
    }

    /**
     * 获取method
     *
     * @param instance
     * @param name
     * @param parameterTypes
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getMethod(Object instance, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        return getMethod(instance.getClass(), name, parameterTypes);
    }

    /**
     * 获取method
     *
     * @param clazz
     * @param name
     * @param parameterTypes
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        for (Class<?> tempClazz = clazz; tempClazz != null; tempClazz = tempClazz.getSuperclass()) {
            try {
                Method method = tempClazz.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                // ignore and search next
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + clazz);
    }

    /**
     * 获取field
     *
     * @param instance
     * @param name
     * @return
     * @throws NoSuchFieldException
     */
    public static Field getField(Object instance, String name) throws NoSuchFieldException {
        return getField(instance.getClass(), name);
    }

    /**
     * 获取field
     *
     * @param clazz
     * @param name
     * @return
     * @throws NoSuchMethodException
     */
    public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
        for (Class<?> tempClazz = clazz; tempClazz != null; tempClazz = tempClazz.getSuperclass()) {
            try {
                Field field = tempClazz.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                // ignore and search next
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + clazz);
    }
}
