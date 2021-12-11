package io.github.orange.line.parser;


import io.github.orange.line.LineException;
import io.github.orange.line.annotation.Property;
import io.github.orange.line.parser.deserializer.ObjectDeserializer;
import io.github.orange.line.util.FieldUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

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

    private String dateFormatPattern;

    private DateFormat dateFormat;

    private static TimeZone timeZone = TimeZone.getDefault();

    private static Locale locale = Locale.getDefault();

    public LineParser(String input)
    {
        this(input, ParserConfig.globalInstance);
    }

    public LineParser(final String input, final ParserConfig config)
    {
        this.input = input;

        this.config = config;

        this.setDateFormat(config.getDateFormat());
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

            Object value = derializer.deserialze(this, contentes[i], field);

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

    public String getDateFormatPattern()
    {
        if (dateFormat instanceof SimpleDateFormat)
        {
            return ((SimpleDateFormat) dateFormat).toPattern();
        }

        return dateFormatPattern;
    }

    public DateFormat getDateFormat()
    {
        if (dateFormat == null)
        {
            if (dateFormatPattern != null)
            {
                dateFormat = new SimpleDateFormat(dateFormatPattern, locale);
                dateFormat.setTimeZone(timeZone);
            }
        }

        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat)
    {
        this.dateFormat = dateFormat;
        if (dateFormatPattern != null)
        {
            dateFormatPattern = null;
        }
    }

    public void setDateFormat(String dateFormat)
    {
        this.dateFormatPattern = dateFormat;
        if (this.dateFormat != null)
        {
            this.dateFormat = null;
        }
    }
}
