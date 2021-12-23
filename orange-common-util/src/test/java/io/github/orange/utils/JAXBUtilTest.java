package io.github.orange.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class JAXBUtilTest
{

    @Test
    public void xmlIsStrToObject() throws Exception
    {
        People people = JAXBUtil.xmlIsStrToObject(null, People.class);
    }

    @Test
    public void objectToXmlStr() throws Exception
    {
        People people = new People();

        people.setName("orange");

        String xml = JAXBUtil.objectToXmlStr(people, "gbk");

        System.out.println(xml);
    }
}