package io.github.orange.line.parser;

import io.github.orange.line.Constants;
import io.github.orange.line.LineException;
import io.github.orange.line.annotation.Property;
import io.github.orange.line.parser.deserializer.ObjectDeserializer;
import io.github.orange.line.serializer.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author orange
 * @Description: 反序列化解析配置
 * @date 2021/12/6 16:57
 */
public class ParserConfig
{
    public final static ParserConfig globalInstance = new ParserConfig();

    private final Map<Type, ObjectDeserializer> deserializers;

    private String separator;

    private String dateFormat;

    public ParserConfig()
    {
        deserializers = new ConcurrentHashMap<>();

        this.separator = Constants.DEFAULT_SEPARATOR_REGEX;

        this.dateFormat = Constants.DEFAULT_DATE_FORMAT;

        this.initDeserializers();
    }

    private void initDeserializers()
    {
        put(String.class, StringCodec.instance);
        put(Date.class, DateCodec.instance);
        put(int.class, NumberCodec.instance);
        put(float.class, NumberCodec.instance);
        put(double.class, NumberCodec.instance);
        put(long.class, NumberCodec.instance);
        put(short.class, NumberCodec.instance);
        put(Integer.class, NumberCodec.instance);
        put(Float.class, NumberCodec.instance);
        put(Double.class, NumberCodec.instance);
        put(Long.class, NumberCodec.instance);
        put(Short.class, NumberCodec.instance);
        put(BigDecimal.class, NumberCodec.instance);
        put(boolean.class, BooleanCodec.instance);
        put(Boolean.class, BooleanCodec.instance);
        put(char.class, CharacterCodec.instance);
        put(Character.class, CharacterCodec.instance);
    }


    public String getSeparator()
    {
        return separator;
    }

    public void setSeparator(String separator)
    {
        this.separator = separator;
    }

    public ObjectDeserializer getDeserializer(Type type, Field field)
    {
        Property property = field.getAnnotation(Property.class);

        ObjectDeserializer deserializer = null;

        if(property.deserializer() != null &&  ObjectDeserializer.class.isAssignableFrom(property.deserializer()))
        {
            try
            {
                deserializer = (ObjectDeserializer) property.deserializer().newInstance();
            }
            catch (InstantiationException |IllegalAccessException e)
            {
                throw new LineException("new instance of ObjectDeserializer fail:" + property.deserializer() + " not support:", e);
            }
        }


        if(deserializer == null)
        {
            deserializer = this.deserializers.get(type);
        }

        if(deserializer == null)
        {
            throw new LineException("the field type " + type.getTypeName() + " not support");
        }

        return deserializer;
    }

    public String getDateFormat()
    {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    public boolean put(Type type, ObjectDeserializer deserializer)
    {
        this.deserializers.put(type, deserializer);
        return true;
    }
}
