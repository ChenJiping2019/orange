package io.github.orange.line.serializer;

import io.github.orange.line.LineException;
import io.github.orange.line.annotation.Property;
import io.github.orange.line.parser.LineParser;
import io.github.orange.line.parser.deserializer.ObjectDeserializer;
import io.github.orange.line.util.WriteUtil;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * @author: orange
 * 数字类型数据处理
 */
public class NumberCodec implements ObjectSerializer, ObjectDeserializer
{
    public static NumberCodec instance = new NumberCodec();

    @Override
    public <T> T deserialze(LineParser parser, String input, Field field)
    {

        Property property = field.getAnnotation(Property.class);

        DecimalFormat format = null;

        if(property.pattern() != null && !"".equals(property.pattern().trim()))
        {
            format = new DecimalFormat(property.pattern());
        }

        Class<?> clazz = field.getType();

        Object value = null;

        if(format != null)
        {
            Number temp = null;

            try
            {
                temp = format.parse(input);
            }
            catch (ParseException e)
            {
                throw new LineException("number parse fail:", e);
            }


            if(clazz == int.class || clazz == Integer.class)
            {
                value = temp.intValue();

                return (T) value;
            }

            if(clazz == float.class || clazz == Float.class)
            {
                value = temp.floatValue();
                return (T) value;
            }

            if(clazz == double.class || clazz == Double.class)
            {
                value = temp.doubleValue();
                return (T) value;
            }

            if(clazz == short.class || clazz == Short.class)
            {
                value = temp.shortValue();
                return (T) value;
            }

            if(clazz == BigDecimal.class)
            {
                value = new BigDecimal(temp.toString());
                return (T) value;
            }
        }
        else
        {
            if(clazz == int.class || clazz == Integer.class)
            {
                value = Integer.valueOf(input);

                return (T) value;
            }

            if(clazz == float.class || clazz == Float.class)
            {
                value = Float.valueOf(input);
                return (T) value;
            }

            if(clazz == double.class || clazz == Double.class)
            {
                value = Double.valueOf(input);
                return (T) value;
            }

            if(clazz == short.class || clazz == Short.class)
            {
                value = Short.valueOf(input);
                return (T) value;
            }

            if(clazz == BigDecimal.class)
            {
                value = new BigDecimal(input);
                return (T) value;
            }
        }

        return (T) value;
    }

    @Override
    public void write(LineSerializer serializer, Object object, Type type, String fieldName, Property property)
    {
        StringWriter out = serializer.out;

        DecimalFormat format = null;

        if(property.pattern() != null && !"".equals(property.pattern().trim()))
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

        WriteUtil.write(out, value);
    }
}
