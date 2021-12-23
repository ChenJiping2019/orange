package io.github.orange.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import static org.junit.Assert.*;

public class HttpServletFileUtilTest
{

    @Test
    public void testUTF8_KERNEL()
    {
        System.out.println(Arrays.binarySearch(HttpServletFileUtil.UTF8_KERNEL, "MSIE"));

        Assert.assertEquals(Arrays.binarySearch(HttpServletFileUtil.UTF8_KERNEL, "MSIE"), 0);


    }

    @Test
    public void ouput()
    {



    }
}