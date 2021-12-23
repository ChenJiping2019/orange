package io.github.orange.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CHNIDNoUtilTest
{

    @Test
    public void isValid()
    {
        Assert.assertEquals(CHNIDNoUtil.isValid("135"), false);


    }

    @Test
    public void getSex()
    {
        Assert.assertEquals(CHNIDNoUtil.getSex("135"), CHNIDNoUtil.Sex.N);
    }
}