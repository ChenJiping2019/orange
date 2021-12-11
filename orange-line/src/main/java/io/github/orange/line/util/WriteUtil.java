package io.github.orange.line.util;

import io.github.orange.line.LineException;

import java.io.IOException;
import java.io.Writer;

/**
 * @author orange
 * @Description:
 * @date 2021/12/9 14:31
 */
public class WriteUtil
{
    /**
     * 写入空字符串
     * @param writer
     */
    public final static void writeNull(Writer writer)
    {
        write(writer, "null");
    }

    /**
     * 将字符串写入writer流中
     * @param writer
     * @param text
     */
    public final static void write(Writer writer, String text)
    {
        if(text == null)
        {
            writeNull(writer);
            return;
        }

        try
        {
            writer.write(text, 0, text.length());

            writer.flush();
        }
        catch(IOException e)
        {
            throw new LineException("Serialize fail:" + e.getMessage());
        }

    }
}
