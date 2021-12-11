package io.github.orange.line.serializer;

import io.github.orange.line.annotation.Property;
import io.github.orange.line.parser.LineParser;
import io.github.orange.line.parser.deserializer.ObjectDeserializer;
import io.github.orange.line.util.WriteUtil;

import java.io.StringWriter;
import java.lang.reflect.Type;
import java.text.DecimalFormat;

/**
 * @author: orange
 * @description: 数字类型数据处理
 * @create: 2021/12/10 13:30
 */
public class NumberCodec implements ObjectSerializer, ObjectDeserializer
{
    public static NumberCodec instance = new NumberCodec();

    @Override
    public <T> T deserialze(LineParser parser, Type type, String fieldName, Property property)
    {
        return null;
    }

    @Override
    public void write(LineSerializer serializer, Object object, Type type, String fieldName, Property property)
    {
        StringWriter out = serializer.out;

        DecimalFormat format = null;

        if(property.pattern() != null)
        {
            format = new DecimalFormat(property.pattern());
        }

        String value = null;

        if(format != null)
        {
            value = format.format(object);
        }
        else
        {
            value = object.toString();
        }

        WriteUtil.write(out, format.format(value));
    }
}
