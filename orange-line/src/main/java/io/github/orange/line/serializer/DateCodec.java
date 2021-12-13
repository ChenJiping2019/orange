package io.github.orange.line.serializer;

import io.github.orange.line.LineException;
import io.github.orange.line.annotation.Property;
import io.github.orange.line.parser.LineParser;
import io.github.orange.line.parser.deserializer.ObjectDeserializer;
import io.github.orange.line.util.WriteUtil;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author orange
 */
public class DateCodec implements ObjectSerializer, ObjectDeserializer
{
    public static DateCodec instance = new DateCodec();

    @Override
    public void write(LineSerializer serializer, Object object, Type type, String fieldName, Property property)
    {
        StringWriter out = serializer.out;

        Date value = (Date) object;

        DateFormat format = null;

        if(property.pattern() != null && !"".equals(property.pattern().trim()))
        {
            format = new SimpleDateFormat(property.pattern());
        }

        if(format == null)
        {
            format = serializer.getDateFormat();
        }

        WriteUtil.write(out, format.format(value));
    }

    @Override
    public <T> T deserialze(LineParser parser, String input, Field field)
    {
        Date date = null;

        if(input == null)
        {
            return (T) date;
        }

        Property property = field.getAnnotation(Property.class);

        DateFormat format = null;

        if(property.pattern() != null && !"".equals(property.pattern().trim()))
        {
            format = new SimpleDateFormat(property.pattern());
        }

        if(format == null)
        {
            format = parser.getDateFormat();
        }

        try
        {
            date = format.parse(input.trim());
        }
        catch (ParseException e)
        {
            throw new LineException("date parse fail:", e);
        }

        return (T) date;
    }
}
