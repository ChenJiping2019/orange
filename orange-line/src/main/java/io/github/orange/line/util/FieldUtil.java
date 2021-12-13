package io.github.orange.line.util;

import io.github.orange.line.LineException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author orange
 * {@link java.lang.reflect.Field}工具类
 */
public class FieldUtil
{
    /**
     * 获取{@link Field}对应的方法名
     * @param prefix 方法名前缀
     * @param fieldName
     * @return
     */
    public static String getMethodName(String prefix, String fieldName)
    {

        StringBuilder methodName = new StringBuilder(prefix);

        methodName.append(fieldName.substring(0, 1).toUpperCase());

        methodName.append(fieldName.substring(1));

        return methodName.toString();
    }

    /**
     * 获取该类声明的所有标记{@link Annotation}的字段，包括父类字段
     * @param clazz
     * @param annotationClass
     * @return
     */
    public static Field[] getFields(Class<?> clazz, Class<? extends Annotation> annotationClass)
    {
        List<Field> fieldList = new ArrayList<>();

        Class<?> clazzz = clazz;

        while(clazzz != Object.class)
        {
            for(Field field : clazzz.getDeclaredFields())
            {
                if(field.isAnnotationPresent(annotationClass))
                {
                    fieldList.add(field);
                }
            }

            clazzz = clazzz.getSuperclass();
        }

        return fieldList.toArray(new Field[]{});
    }

    /**
     * 根据fieldName从对象中获取对应的值
     * @param target
     * @param fieldName
     * @param methodNamePrefix 方法名前缀
     * @return
     */
    public static Object getValue(Object target, String fieldName, String methodNamePrefix)
    {
        Object value = null;

        String methodName = getMethodName(methodNamePrefix, fieldName);

        Method method = null;

        try
        {
            method = target.getClass().getMethod(methodName);

            if(method.getReturnType() == void.class)
            {
                return value;
            }

            value = method.invoke(target);
        }
        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            throw new LineException("get value fail:", e);
        }

        return value;
    }

    public static void setValue(Object target, Field field,  Object val)
    {
        String methodName = getMethodName("set", field.getName());

        try
        {
            Method method = target.getClass().getMethod(methodName, field.getType());

            method.invoke(target, val);
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            throw new LineException("set value fail:", e);
        }


    }
}
