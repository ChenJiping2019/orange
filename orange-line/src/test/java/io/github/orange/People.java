package io.github.orange;

import io.github.orange.line.annotation.Property;
import io.github.orange.line.serializer.StringCodec;

/**
 * @author: orange
 * @description:
 * @create: 2021/12/10 13:56
 */
public class People
{
    @Property(order = 0, serializer = StringCodec.class)
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
