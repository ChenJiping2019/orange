package io.github.orange.utils;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilTest
{

    @Test
    public void formatDate()
    {
        String curDate = DateUtil.formatDate(new Date(), DateUtil.PATTERN_10DATE_8TIME);
        System.out.println(curDate);
    }

    @Test
    public void getCur8Date()
    {
    }

    @Test
    public void getCur6Time()
    {
    }

    @Test
    public void getCur8Date6Time()
    {
    }

    @Test
    public void getCur10Date()
    {
    }

    @Test
    public void getCur8Time()
    {
    }

    @Test
    public void getCur10Date8Time()
    {
    }

    @Test
    public void add()
    {
    }

    @Test
    public void testAdd()
    {
    }
}