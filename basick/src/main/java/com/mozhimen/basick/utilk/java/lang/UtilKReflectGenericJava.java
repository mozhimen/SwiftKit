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

//    @RequiresApi(CVersCode.V_24_7_N)
//    public <T> Type getParentGenericTypeByT(Class<?> clazz, Class<T> tClazz) {
//        Class<?> superClazz = clazz.getSuperclass();
//        Type genericSuperClazz = clazz.getGenericSuperclass();
//        if (!(genericSuperClazz instanceof ParameterizedType)) {
//            if (superClazz != null) {
//                return getParentGenericTypeByT(superClazz);
//            } else
//                return null;
//        }
//        List<Type> types = Arrays.stream(((ParameterizedType) genericSuperClazz).getActualTypeArguments()).filter(a -> a instanceof Class<T>).collect(Collectors.toList());
//        if (!types.isEmpty()) {
//            for (Type type : types) {
//                Class<?> clz = (Class<?>) type;
//                if (clz ) {
//                    return clz;
//                }
//            }
//        }
//        if (superClazz != null)
//            return getParentGenericTypeByT(superClazz);
//        else
//            return null;
//    }

    public static boolean isInstanceOfGenericType(Object obj, Class<?> genericClass) {
        Type type = obj.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            return rawType.equals(genericClass);
        }
        return false;
    }
}
