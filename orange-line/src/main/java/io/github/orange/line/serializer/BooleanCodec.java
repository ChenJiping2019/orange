package io.github.orange.line.serializer;

import io.github.orange.line.annotation.Property;
import io.github.orange.line.parser.LineParser;
import io.github.orange.line.parser.deserializer.ObjectDeserializer;
import io.github.orange.line.util.WriteUtil;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class BooleanCodec implements ObjectSerializer, ObjectDeserializer
{
    public static BooleanCodec instance = new BooleanCodec();

    @Override
    public <T> T deserialze(LineParser parser, String input, Field field)
    {
        Boolean value = null;

        if(input == null)
        {
            if(field.getType() == boolean.class)
            {
                value = new Boolean(false);
            }

            return (T) value;
        }

        value = new Boolean(input);

        return (T) value;
    }

    @Override
    public void write(LineSerializer serializer, Object object, Type type, String fieldName, Property property)
    {
        StringWriter out = serializer.out;

        if(object == null)
        {
            return;
        }

        Boolean value;

        if(type == boolean.class)
        {
            value = new Boolean((boolean)object);
        }
        else
        {
            value = (Boolean) object;
        }

        WriteUtil.write(out, value.toString());
    }
}
