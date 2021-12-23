package io.github.orange.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class RMBUtilTest
{

    @Test
    public void toUppercase()
    {
        System.out.println(RMBUtil.toUppercase(10));
        System.out.println(RMBUtil.toUppercase(10000));
        System.out.println(RMBUtil.toUppercase(10000.98));
        System.out.println(RMBUtil.toUppercase(10000.8));
        System.out.println(RMBUtil.toUppercase(12351.8));
        System.out.println(RMBUtil.toUppercase(0.67));

        System.out.println(RMBUtil.toUppercase(-0.67));
    }

    @Test
    public void toUppercase2()
    {
        System.out.println(RMBUtil.toUppercase(10000.8));
        System.out.println(RMBUtil.toUppercase(10400.8));
        System.out.println(RMBUtil.toUppercase(10400.843));
        System.out.println(RMBUtil.toUppercase("10,400.843"));
    }
}