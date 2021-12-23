package io.github.orange.utils;

/**
 * @author: orange
 * 中国居民身份证号工具类
 */
public class CHNIDNoUtil
{
    /**
     * 加权因子
     */
    public static final int[] WEIGHTING_FACTOR = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1};

    /**
     * 身份证号长度
     */
    public static final int LENGTH = 18;

    /***
     * 性别枚举类型
     */
    enum Sex{
        /**女*/
        F,

        /**男*/
        M,

        /**未知*/
        N,
    }

    /**
     * 判断给定的字符串是否是有效的身份证号
     * @param input 输入字符串
     * @return true-有效，false-无效
     */
    public static boolean isValid(String input)
    {
        if(input == null || input.trim().length() != LENGTH)
        {
            return false;
        }

        // 身份证校验位校验
        int sum = 0;

        for(int i = 0; i < LENGTH; ++ i)
        {
            String temp = String.valueOf(input.charAt(i));

            int num = 0;

            if ("X".equalsIgnoreCase(temp))
            {
                num = 10;
            }
            else
            {
                num = Integer.parseInt(temp);
            }

            // 校验位乘以该位的加权因子
            sum += num * WEIGHTING_FACTOR[i];
        }

        if ((sum % 11) != 1)
        {
            return false;
        }

        return true;
    }

    /**
     * 从给定的证件号码中取出生日
     * @param idNo 证件号码
     * @return 8位的生日
     */
    public static String getBirthday(String idNo)
    {
        String birthday = null;

        if(!isValid(idNo))
        {
            return birthday;
        }

        birthday = idNo.substring(6, 13);

        return birthday;
    }

    /**
     * 从给定的证件号码中获取性别{@link Sex}
     * @param idNo 证件号
     * @return 如果证件号异常，则返回{@link Sex#N}
     */
    public static Sex getSex(String idNo)
    {
        Sex sex = Sex.N;

        if(!isValid(idNo))
        {
            return sex;
        }

        String sexFlag = idNo.substring(16, 17);

        int result = Integer.valueOf(sexFlag) % 2;

        if(result == 0)
        {
            sex = Sex.F;
        }
        else
        {
            sex = Sex.M;
        }

        return sex;
    }
}
