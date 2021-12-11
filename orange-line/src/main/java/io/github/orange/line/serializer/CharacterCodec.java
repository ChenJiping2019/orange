package io.github.orange.line.serializer;

import io.github.orange.line.Constants;
import io.github.orange.line.LineException;
import io.github.orange.line.annotation.Property;
import io.github.orange.line.parser.LineParser;
import io.github.orange.line.parser.deserializer.ObjectDeserializer;
import io.github.orange.line.util.WriteUtil;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class CharacterCodec implements ObjectSerializer, ObjectDeserializer
{
    public static CharacterCodec instance = new CharacterCodec();

    @Override
    public <T> T deserialze(LineParser parser, String input, Field field)
    {
        Character value = null;

        if(input == null)
        {
            value = new Character(Constants.DEFAULT_VALUE);
        }
        else
        {
            char[] chars = input.toCharArray();

            if(chars.length > 1)
            {
                throw new LineException("parse " + field.getType().getTypeName() + " fail: the input '" + input + "' length exceeds 1");
            }

            if(chars.length == 0)
            {
                value = new Character(Constants.DEFAULT_VALUE);
            }
            else
            {
                value = new Character(input.charAt(0));
            }

        }

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

        Character value = null;

        if(type == char.class)
        {
            value = new Character((char)object);
        }
        else
        {
            value = (Character) object;
        }

        WriteUtil.write(out, value.toString());
    }
}
