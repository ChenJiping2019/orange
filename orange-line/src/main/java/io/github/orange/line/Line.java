package io.github.orange.line;

import io.github.orange.line.annotation.Property;
import io.github.orange.line.parser.LineParser;
import io.github.orange.line.parser.ParserConfig;
import io.github.orange.line.serializer.LineSerializer;
import io.github.orange.line.serializer.SerializeConfig;

import java.io.StringWriter;

/**
 * @author orange
 * 该类是将OrangeLine的主要类，通常使用{@link #toLineString(Object)}和{@link #parseObject(String, Class)}
 * 例：
 *    Model model = new Model();
 *    String line = Line.toLineString(model);// serializes model to line text
 *    Model model2 = Line.parseObject(line, Model.class); // deserializes line into model2
 *
 */
public class Line
{
    public static String toLineString(Object object)
    {
        return toLineString(object, SerializeConfig.globalInstance);
    }

    public static String toLineString(Object object, String separator)
    {
        SerializeConfig config = SerializeConfig.globalInstance;

        config.setSeparator(separator);

        return toLineString(object, config);
    }

    /**
     *
     * @param object
     * @param separator
     * @param length 分隔长度，如果小于0，默认为{@link Property#order()}最大值 + 1
     * @return
     */
    public static String toLineString(Object object, String separator, int length)
    {
        SerializeConfig config = SerializeConfig.globalInstance;

        config.setSeparator(separator);

        config.setLength(length);

        return toLineString(object, config);
    }

    public static String toLineString(Object object, SerializeConfig config)
    {
        StringWriter out = new StringWriter();

        LineSerializer serializer = new LineSerializer(out, config);

        serializer.write(object);

        String str = out.toString();

        return str;
    }

    public static <T> T parseObject(String text, Class<T> clazz)
    {
        return parseObject(text, clazz, ParserConfig.globalInstance);
    }

    /**
     * 根据指定的分隔符解析字符串
     * @param text
     * @param clazz
     * @param separator 分隔符
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String text, Class<T> clazz, String separator)
    {
        ParserConfig config = ParserConfig.globalInstance;

        config.setSeparator(separator);

        return parseObject(text, clazz, config);
    }

    /**
     * 根据指定的配置文件解析字符串
     * @param text
     * @param clazz
     * @param config 解析配置文件
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String text, Class<T> clazz, ParserConfig config)
    {

        LineParser parser = new LineParser(text, config);

        T object = parser.parseObject(clazz);

        return object;
    }

}
