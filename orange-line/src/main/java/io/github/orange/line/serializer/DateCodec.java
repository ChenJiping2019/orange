package io.github.orange.line.serializer;

import io.github.orange.line.annotation.Property;
import io.github.orange.line.parser.LineParser;
import io.github.orange.line.parser.deserializer.ObjectDeserializer;
import io.github.orange.line.util.WriteUtil;

import java.io.StringWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author orange
 * @Description:
 * @date 2021/12/9 17:54
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

        if(property.pattern() != null)
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
    public <T> T deserialze(LineParser parser, Type type, String fieldName, Property property)
    {
        return null;
    }
}
