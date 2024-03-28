package com.mozhimen.basick.utilk.java.lang;

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

    public static int getField_ofInt(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getField(obj, fieldName).getInt(obj);
    }

    public static Field getField(Object obj, String fieldName) throws NoSuchFieldException {
        return getField(obj.getClass(), fieldName);
    }

    //仅能获取类本身的属性成员（包括私有、共有、保护）
    public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        for (Class<?> tempClazz = clazz; tempClazz != null; tempClazz = tempClazz.getSuperclass()) {
            try {
                Field field = tempClazz.getDeclaredField(fieldName);
                if (!field.isAccessible()) field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                // ignore and search next
            }
        }
        throw new NoSuchFieldException("Field " + fieldName + " not found in " + clazz);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //获取类及其基类所有的field
    public static List<Field> getFields_ofAll(Object obj) {
        return getFields_ofAll(obj.getClass());
    }

    //获取类及其基类所有的field
    public static List<Field> getFields_ofAll(Class<?> clazz) {
        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> superClass = clazz.getSuperclass();
        while (superClass != null) {
            Field[] superFields = superClass.getDeclaredFields();
            fields.addAll(Arrays.asList(superFields));
            superClass = superClass.getSuperclass();
        }
        return fields;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Method getMethod(Object obj, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        return getMethod(obj.getClass(), methodName, parameterTypes);
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        for (Class<?> tempClazz = clazz; tempClazz != null; tempClazz = tempClazz.getSuperclass()) {
            try {
                Method method = tempClazz.getDeclaredMethod(methodName, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                // ignore and search next
            }
        }
        throw new NoSuchMethodException("Method " + methodName + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + clazz);
    }
}
