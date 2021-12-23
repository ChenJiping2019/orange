package io.github.orange.utils;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class StreamUtilTest
{

    @Test
    public void ouputWithoutClose() throws IOException
    {
        String test = "orange1233橙子";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        StreamUtil.ouputWithoutClose(test.getBytes("utf-8"), outputStream);

        System.out.println(new String(outputStream.toByteArray(), "utf-8"));
    }
}