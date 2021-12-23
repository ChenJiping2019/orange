package io.github.orange.utils;

import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class JdomUtilTest
{
    private Document document;

    @Before
    public void before()
    {
        document = new Document();

        Element root = new Element("Config");
        document.setRootElement(root);

        Element name = new Element("name");
        name.setText("Orange");
        root.addContent(name);
    }

    @Test
    public void testToString()
    {

        String xml = JdomUtil.toString(document, null, true);
        System.out.println(xml);

        xml = JdomUtil.toString(document, "", true);
        System.out.println(xml);

        xml = JdomUtil.toString(document, "GBK", true);
        System.out.println(xml);
    }

    @Test
    public void toFmtBytes() throws IOException
    {
        byte[] bytes = JdomUtil.toFmtBytes(document, "utf-8");

        System.out.println(bytes.length);

        System.out.println(new String(bytes, "utf-8"));
    }
}