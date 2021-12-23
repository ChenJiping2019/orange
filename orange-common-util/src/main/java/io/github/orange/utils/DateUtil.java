package io.github.orange.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: orange
 * 日期工具类
 */
public class DateUtil
{
    public static final String PATTERN_8DATE = "yyyyMMdd";

    public static final String PATTERN_6TIME = "HHmmss";

    public static final String PATTERN_8DATE_6TIME = "yyyyMMddHHmmss";

    public static final String PATTERN_10DATE = "yyyy-MM-dd";

    public static final String PATTERN_8TIME = "HH:mm:ss";

    public static final String PATTERN_10DATE_8TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将给定日期按给定的格式进行格式化
     * @param date 日期
     * @param pattern 新日期和时间格式模式的字符串
     * @return 给定的日期字符串
     */
    public static String formatDate(Date date, String pattern)
    {
        Format format = new SimpleDateFormat(pattern);

        String str = format.format(date);

        return str;
    }

    /**
     * 按照{@value PATTERN_8DATE}模式返回当前日期的字符串形式
     * @return 当前日期的字符串
     */
    public static String getCur8Date()
    {
        return formatDate(new Date(), PATTERN_8DATE);
    }

    /**
     * 按照{@value PATTERN_6TIME}模式返回当前时间的字符串形式
     * @return 当前时间的字符串
     */
    public static String getCur6Time()
    {
        return formatDate(new Date(), PATTERN_6TIME);
    }

    /**
     * 按照{@value PATTERN_8DATE_6TIME}模式返回当前日期和时间的字符串形式
     * @return 当前日期和时间的字符串
     */
    public static String getCur8Date6Time()
    {
        return formatDate(new Date(), PATTERN_8DATE_6TIME);
    }

    /**
     * 按照{@value PATTERN_10DATE}模式返回当前日期的字符串形式
     * @return 当前日期的字符串
     */
    public static String getCur10Date()
    {
        return formatDate(new Date(), PATTERN_10DATE);
    }

    /**
     * 按照{@value PATTERN_8TIME}模式返回当前时间的字符串形式
     * @return 当前时间的字符串
     */
    public static String getCur8Time()
    {
        return formatDate(new Date(), PATTERN_8TIME);
    }

    /**
     * 按照{@value PATTERN_10DATE_8TIME}模式返回当前日期和时间的字符串形式
     * @return 当前日期和时间的字符串
     */
    public static String getCur10Date8Time()
    {
        return formatDate(new Date(), PATTERN_10DATE_8TIME);
    }

    /**
     * 将日期字符串按照指定的日期和时间模式进行解析
     * @param dateStr 日期字符串
     * @param pattern 日期和时间格式模式的字符串
     * @return 解析失败，返回 null
     */
    public static Date parseDate(String dateStr, String pattern)
    {
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        Date date = null;
        try
        {
            date = format.parse(dateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }

        return date;
    }


    /**
     * 根据日历的规则，将指定的时间量添加或减去给定的日历字段。 例如，要从当前日历的时间减去5天，您可以通过调用以下方法来实现：
     *   Date date = new Date();
     *   DateUtil.add(date, Calendar.DAY_OF_MONTH, -5)
     * @param oldDate 给定的日期时间
     * @param field 日历字段，{@link Calendar}日历字段
     * @param amount 要添加到该字段的日期或时间的数量
     * @return 新的日期
     */
    public static Date add(Date oldDate, int field, int amount)
    {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(oldDate);

        calendar.add(field, amount);

        return calendar.getTime();
    }

    /**
     * 根据日历的规则，将指定的时间量添加或减去给定的日历字段。 例如，要从给定的时间减去5天，您可以通过调用以下方法来实现：
     *  DateUtil.add("2021-01-01", "yyyy-MM-dd", Calendar.DAY_OF_MONTH, -5)
     *  如果给定的pattern不能解析字符串的日期，则返回 null
     * @param dateStr 给定的字符串日期
     * @param pattern 日期和时间格式模式的字符串
     * @param field 日历字段，{@link Calendar}日历字段
     * @param amount 要添加到该字段的日期或时间的数量
     * @return 新的日期，并按给定的pattern返回
     */
    public static String add(String dateStr, String pattern, int field, int amount)
    {
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        Date newDate = null;

        try
        {
            Date date = format.parse(dateStr);

            newDate = add(date, field, amount);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }

        return format.format(newDate);
    }

}
