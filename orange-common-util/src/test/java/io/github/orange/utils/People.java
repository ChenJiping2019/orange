package io.github.orange.utils;

import javax.xml.bind.annotation.*;

/**
 * @author: orange
 * 测试
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "People")
@XmlType(name = "", propOrder = {
        "name"
})
public class People
{
    @XmlElement(name = "Name", required = true)
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
