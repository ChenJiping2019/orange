package io.github.orange.line.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Property
{
    /**
     * 字段顺序：不能重重复，从0开始，在进行parse操作时，按照该值进行索引解析；将class对象进行toString操作时，
     * 按照该顺序进行输出
     * @return
     */
    int order();

    /**
     * 值格式化模式，使用工具类{@link java.text.Format}进行格式化处理。例：字段类开是{@link java.util.Date}, {@link Property#pattern()}= "yyyy-MM-dd"，则
     * 按照此格式将日期类开转换成字符串或按此格式将字符串解析成日期对象。
     * @return
     */
    String pattern() default "";

    /**
     * {@link io.github.orange.line.serializer.ObjectSerializer}子类；如果配置，则优先使用该子类实例进行序列化
     * @return
     */
    Class<?> serializer() default Void.class;

    /***
     * {@link io.github.orange.line.parser.deserializer.ObjectDeserializer}子类；如果配置，则优先使用该子类实例进行反序列化
     * @return
     */
    Class<?> deserializer() default Void.class;
}
