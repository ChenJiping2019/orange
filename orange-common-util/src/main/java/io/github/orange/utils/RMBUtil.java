package io.github.orange.utils;

import java.math.BigDecimal;

/**
 * @author: orange
 * 人民币工具类
 */
public class RMBUtil
{
    /**
     * 大写转换
     * @param money 待转换的金额
     * @return 将给出的金额进行大写转换后返回
     */
    public static String toUppercase(String money)
    {
        if(money == null || "".equals(money.replaceAll("\\s", "")))
        {
            return "";
        }

        String fraction[] = new String[]{"角","分"};

        String digit[] = new String[]{"零","壹","贰","叁", "肆", "伍", "陆", "柒", "捌", "玖"};

        String unit[][] = new String[][]{{"元", "万","亿"}, {"", "拾","佰", "仟"}};


        BigDecimal value = new BigDecimal(money.trim().replace(",", ""));

        String head = value.compareTo(BigDecimal.ZERO) < 0 ? "欠" : "";

        value = value.abs();

        StringBuilder str = new StringBuilder();


        for(int i = 0; i < fraction.length; ++ i)
        {
            int index = value.multiply(BigDecimal.TEN).multiply(new BigDecimal(Math.pow(10, i))).intValue() % 10;

            String temp = digit[index] + fraction[i];

            str.append(temp.replaceAll("零.", ""));
        }

        if(str.length() == 0)
        {
            str.append("整");
        }

        double tMoney = Math.floor(value.doubleValue());

        for(int i = 0; i < unit[0].length && tMoney > 0; ++ i)
        {
            String temp = "";

            for(int j = 0; j < unit[1].length && tMoney > 0; ++ j)
            {
                double remainder = tMoney % 10;

                temp = digit[(int)remainder] + unit[1][j] + temp;

                tMoney = Math.floor(tMoney / 10);
            }

            temp = temp.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i];

            str.insert(0, temp);
        }

        String result = head + str.toString().replaceAll("(零.)*零元", "元").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");

        return result;
    }

    /**
     * 大写转换
     * @param money 待转换的金额
     * @return 将给出的金额进行大写转换后返回
     */
    public static String toUppercase(double money)
    {
        return toUppercase(String.valueOf(money));
    }
}
