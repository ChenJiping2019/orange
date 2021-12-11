package io.github.orange.line.parser;


import io.github.orange.line.LineException;
import io.github.orange.line.annotation.Property;
import io.github.orange.line.parser.deserializer.ObjectDeserializer;
import io.github.orange.line.util.FieldUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author orange
 * @Description:
 * @date 2021/12/7 15:55
 */
public class LineParser
{
    private String input;

    private ParserConfig config;

    private Object object;

    public LineParser(String input)
    {
        this(input, ParserConfig.globalInstance);
    }

    public LineParser(final String input, final ParserConfig config)
    {
        this.input = input;

        this.config = config;
    }

    public <T> T parseObject(Class<T> clazz)
    {
        if(clazz == null)
        {
            return null;
        }

        try
        {
            object = clazz.newInstance();
        }
        catch (InstantiationException |IllegalAccessException e)
        {
            throw new LineException("new instance of " + clazz.getName() + " fail:" + e.getLocalizedMessage());
        }

        if(input == null)
        {
            return (T) object;
        }

        String contentes[] = input.split(config.getSeparator(), -1);

        Field[] fields = FieldUtil.getFields(clazz, Property.class);

        for(int i = 0; i < contentes.length; ++ i)
        {
            Field field = getFieldByOrder(fields, i);

            if(field == null)
            {
                continue;
            }

            ObjectDeserializer derializer = config.getDeserializer(field.getType(), field);

            Object value = derializer.deserialze(this, field.getType(), field.getName(), field.getAnnotation(Property.class));

            FieldUtil.setValue(object, field, value);

        }

        return (T) object;
    }

    private Field getFieldByOrder(Field[] fields, int order)
    {
        Field field = null;

        for(Field temp : fields)
        {
            Property property = temp.getAnnotation(Property.class);

            if(property.order() == order)
            {
                field = temp;
                break;
            }
        }

        return field;
    }
}
