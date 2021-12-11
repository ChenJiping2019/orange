package io.github.orange.line.serializer;

import io.github.orange.line.annotation.Property;

import java.lang.reflect.Type;

/**
 * @author orange
 * @Description:
 * @date 2021/12/6 16:40
 */
public interface ObjectSerializer
{
    void write(LineSerializer serializer, Object object, Type type, String fieldName, Property property);
}
