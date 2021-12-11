package io.github.orange.line;

import io.github.orange.People;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class LineTest
{

    @Test
    public void toLineString()
    {
        People people = new People();
        System.out.println(people.getAge());
        people.setName("orange");
        people.setAge(18);
        people.setMarried(false);
        people.setSex('1');
        people.setGraduate(new Character('1'));
        people.setBirthday(new Date());
        people.setMarriage(new Date());

        String line = Line.toLineString(people);
        System.out.println(line);
    }

    @Test
    public void testToLineString()
    {
    }

    @Test
    public void testToLineString1()
    {
    }

    @Test
    public void parseObject()
    {
        String input = "orange|18|false|1|1|2021-12-11|2021-12-11 20:56:40";
        People people = Line.parseObject(input, People.class);

        System.out.println(people.getAge());
        System.out.println(people.isMarried());
        System.out.println(people.getSex());
        System.out.println(people.getBirthday());
        System.out.println(people.getMarriage());
    }

    @Test
    public void testParseObject()
    {
    }
}