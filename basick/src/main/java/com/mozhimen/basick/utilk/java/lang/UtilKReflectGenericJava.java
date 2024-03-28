package com.mozhimen.basick.utilk.java.lang;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @ClassName UtilKReflectGenericJava
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/18 14:21
 * @Version 1.0
 */

public class UtilKReflectGenericJava {

    public static boolean isInstance_ofGenericType(Object obj, Class<?> genericClass) {
        Type type = obj.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            return rawType.equals(genericClass);
        }
        return false;
    }
}
