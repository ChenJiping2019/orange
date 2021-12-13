package io.github.orange.line.serializer;

import io.github.orange.line.Constants;
import io.github.orange.line.LineException;
import io.github.orange.line.annotation.Property;
import io.github.orange.line.util.FieldUtil;
import io.github.orange.line.util.WriteUtil;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author orange
 */
public class LineSerializer
{
    protected final SerializeConfig config;

    private String dateFormatPattern;

    private DateFormat dateFormat;

    private static TimeZone timeZone = TimeZone.getDefault();

    private static Locale locale = Locale.getDefault();

    public final StringWriter out;

    public LineSerializer(StringWriter out)
    {
        this(out, SerializeConfig.globalInstance);
    }

    public LineSerializer(SerializeConfig config)
    {
        this(new StringWriter(), config);
    }

    public LineSerializer(StringWriter out, SerializeConfig config)
    {
        this.out = out;

        this.config = config;

        this.setDateFormat(this.config.getDateFormat());
    }

    public String getDateFormatPattern()
    {
        if (dateFormat instanceof SimpleDateFormat)
        {
            return ((SimpleDateFormat) dateFormat).toPattern();
        }

        return dateFormatPattern;
    }

    public DateFormat getDateFormat()
    {
        if (dateFormat == null)
        {
            if (dateFormatPattern != null)
            {
                dateFormat = new SimpleDateFormat(dateFormatPattern, locale);
                dateFormat.setTimeZone(timeZone);
            }
        }

        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat)
    {
        this.dateFormat = dateFormat;
        if (dateFormatPattern != null)
        {
            dateFormatPattern = null;
        }
    }

    public void setDateFormat(String dateFormat)
    {
        this.dateFormatPattern = dateFormat;
        if (this.dateFormat != null)
        {
            this.dateFormat = null;
        }
    }

    public final void write(Object object)
    {
        if(object == null)
        {
            WriteUtil.writeNull(out);
            return;
        }

        if(object.getClass().isPrimitive())
        {
            WriteUtil.write(out, object.toString());
            return;
        }

        Field[] fields = FieldUtil.getFields(object.getClass(), Property.class);

        Arrays.sort(fields, (field1, field2) -> {
            Property p1 = field1.getAnnotation(Property.class);

            Property p2 = field2.getAnnotation(Property.class);

            return p1.order() - p2.order();
        });

        int length = this.config.getLength();

        if(length <= Constants.MAX_LENGTH)
        {
            length = fields[fields.length - 1].getAnnotation(Property.class).order() + 1;
        }

        for(int i = 0; i < length; ++ i)
        {
            Field field = getFieldByOrder(fields, i);

            if(field != null)
            {
                ObjectSerializer serializer = config.getSerializer(field.getType(), field);

                Class<?> type = field.getType();

                String prefix = "get";

                if(type == boolean.class)
                {
                    prefix = "is";
                }

                Object value = FieldUtil.getValue(object, field.getName(), prefix);

                serializer.write(this, value, field.getType(), field.getName(), field.getAnnotation(Property.class));
            }

            if(i != length - 1)
            {
                WriteUtil.write(out, config.getSeparator());
            }
        }
    }

    private Field getFieldByOrder(Field[] fields, int order)
    {
        Field field = null;

        for(Field temp : fields)
        {
            Property property = temp.getAnnotation(Property.class);

            if(property.order() == order)
            {
                field = temp;
                break;
            }
        }

        return field;
    }
}
