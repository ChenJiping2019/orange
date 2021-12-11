package io.github.orange.line.serializer;

import io.github.orange.line.Constants;
import io.github.orange.line.LineException;
import io.github.orange.line.annotation.Property;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author orange
 * @Description: 序列化配置
 * @date 2021/12/6 16:42
 */
public class SerializeConfig
{
    public final static SerializeConfig globalInstance  = new SerializeConfig();

    private final Map<Type, ObjectSerializer> serializers;

    private String separator;

    private int length;

    private String dateFormat;

    public SerializeConfig()
    {
        serializers = new ConcurrentHashMap<>();

        this.separator = Constants.DEFAULT_SEPARATOR;

        this.length = Constants.MAX_LENGTH;

        this.dateFormat = Constants.DEFAULT_DATE_FORMAT;

        this.initSerializers();
    }

    private void initSerializers()
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

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public String getDateFormat()
    {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat)
    {
        this.dateFormat = dateFormat;
    }


    public boolean put(Type type, ObjectSerializer value)
    {
        this.serializers.put(type, value);

        return true;
    }

    public ObjectSerializer getSerializer(Type type, Field field)
    {
        Property property = field.getAnnotation(Property.class);

        ObjectSerializer serializer = null;

        if(property.serializer() != null &&  ObjectSerializer.class.isAssignableFrom(property.serializer()))
        {
            try
            {
                serializer = (ObjectSerializer) property.serializer().newInstance();

            }
            catch(InstantiationException | IllegalAccessException e)
            {
                throw new LineException("new instance of ObjectSerializer fail:" + property.serializer() + " not support," + e.getLocalizedMessage());
            }
        }

        if(serializer == null)
        {
            serializer = this.serializers.get(type);
        }

        if(serializer == null)
        {
            throw new LineException("the field type " + type.getTypeName() + " not support");
        }

        return serializer;
    }
}
