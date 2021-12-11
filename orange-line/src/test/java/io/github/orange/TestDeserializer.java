package io.github.orange;

import io.github.orange.line.annotation.Property;
import io.github.orange.line.parser.deserializer.ObjectDeserializer;
import io.github.orange.line.serializer.NumberCodec;
import io.github.orange.line.serializer.ObjectSerializer;
import io.github.orange.line.util.FieldUtil;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author: orange
 * @description:
 * @create: 2021/12/10 13:51
 */
public class TestDeserializer
{
    @Test
    public void test()
    {

        People people = new People();

        Field[] fields = FieldUtil.getFields(people.getClass(), Property.class);

        for(Field field : fields)
        {
            Property property = field.getAnnotation(Property.class);

            if(ObjectSerializer.class.isAssignableFrom(property.serializer()))
            {
                System.out.println(111);
            }
        }
    }
}
