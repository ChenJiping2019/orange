package io.github.orange;

import io.github.orange.line.annotation.Property;
import io.github.orange.line.serializer.StringCodec;

import java.util.Date;

/**
 * @author: orange
 * @description:
 * @create: 2021/12/10 13:56
 */
public class People
{
    @Property(order = 0, serializer = StringCodec.class)
    private String name;

    @Property(order = 1)
    private int age;

    @Property(order = 2)
    private boolean married;

    @Property(order = 3)
    private char sex;

    @Property(order = 4)
    private Character graduate;

    @Property(order = 5, pattern = "yyyy-MM-dd")
    private Date birthday;

    @Property(order = 6)
    private Date marriage;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public boolean isMarried()
    {
        return married;
    }

    public void setMarried(boolean married)
    {
        this.married = married;
    }

    public char getSex()
    {
        return sex;
    }

    public void setSex(char sex)
    {
        this.sex = sex;
    }

    public Character getGraduate()
    {
        return graduate;
    }

    public void setGraduate(Character graduate)
    {
        this.graduate = graduate;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public Date getMarriage()
    {
        return marriage;
    }

    public void setMarriage(Date marriage)
    {
        this.marriage = marriage;
    }
}
