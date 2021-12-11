package io.github.orange.line.serializer;

import io.github.orange.line.annotation.Property;
import io.github.orange.line.parser.LineParser;
import io.github.orange.line.parser.deserializer.ObjectDeserializer;
import io.github.orange.line.util.WriteUtil;

import java.io.StringWriter;
import java.lang.reflect.Type;
import java.text.MessageFormat;

/**
 * @author orange
 * @Description:
 * @date 2021/12/9 17:01
 */
public class StringCodec implements ObjectSerializer, ObjectDeserializer
{
    public static StringCodec instance = new StringCodec();

    @Override
    public <T> T deserialze(LineParser parser, Type type, String fieldName, Property property)
    {
        String value = null;

        MessageFormat messageFormat = null;

        if(property.pattern() != null)
        {
            messageFormat = new MessageFormat(property.pattern().trim());
        }

        if(messageFormat != null)
        {
            value = messageFormat.format(new Object[]{value});
        }

        return (T) value;
    }

    @Override
    public void write(LineSerializer serializer, Object object, Type type, String fieldName, Property property)
    {
        StringWriter out = serializer.out;

        String  value = (String) object;

        if(value == null)
        {
            return;
        }

        MessageFormat messageFormat = null;

        if(property.pattern() != null)
        {
            messageFormat = new MessageFormat(property.pattern().trim());
        }

        if(messageFormat != null)
        {
            value = messageFormat.format(new Object[]{value});
        }

        WriteUtil.write(out, value);
    }
}
