package io.github.orange.line.parser.deserializer;

import io.github.orange.line.annotation.Property;
import io.github.orange.line.parser.LineParser;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author orange
 * 反序列化接口
 */
public interface ObjectDeserializer
{
    <T> T deserialze(LineParser parser, String input, Field field);
}
