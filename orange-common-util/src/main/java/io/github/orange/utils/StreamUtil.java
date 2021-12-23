package io.github.orange.utils;

import java.io.*;

/**
 * @author: orange
 * 输入输出流工具类
 */
public class StreamUtil
{

    /**
     * 将输入流转换成字节数组
     * @param is 输入流
     * @param isClose 读取完后，是否关闭输入流
     * @return 返回读取内容，如果异常，则返回null
     */
    public static byte[] toBytes(InputStream is, boolean isClose)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try
        {
            byte[] bytes = new byte[1024 * 8];

            for(int length; (length = is.read(bytes)) != -1;)
            {
                byteArrayOutputStream.write(bytes, 0, length);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if(isClose && is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }

        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 将输入流转换成字节数组，不关闭输入流
     * @param is 输入流
     * @return 返回读取内容，如果异常，则返回null
     */
    public static byte[] toBytesWithoutClose(InputStream is)
    {
        return toBytes(is, false);
    }

    /**
     * 将输入流读入到指定的字节数组中，如果读取内容不足，将抛出{@link IOException}异常
     * @param bytes 指定的字节数组
     * @param is 输入流
     * @throws IOException
     */
    public static void readFull(byte[] bytes, InputStream is) throws IOException
    {
        if(bytes == null)
        {
            return;
        }

        for(int length = 0; length < bytes.length;)
        {
            int readSize = is.read(bytes, length, bytes.length - length);

            if(readSize == -1)
            {
                throw new IOException("read error, actual read length:" + length);
            }

            length += readSize;
        }
    }

    /**
     * 将{@link Reader}输入流转成字符串，并关闭输入流
     * @param reader 输入流
     * @return 如果读取异常，则返回null
     */
    public static String toString(Reader reader)
    {
        StringWriter writer = new StringWriter();

        try
        {
            char[] buffer = new char[1024 * 4];

            for(int length; -1 != (length = reader.read());)
            {
                writer.write(buffer, 0, length);
            }

            writer.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if(reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }


        return writer.toString();
    }

    /**
     * 将给定字节数组写入到输出流，不关闭输出流
     * @param bytes 字节数组内容
     * @param out 输出流
     * @throws IOException
     */
    public static void ouputWithoutClose(byte[] bytes, OutputStream out) throws IOException
    {
        ouput(bytes, out, false);
    }

    /**
     * 将给定字节数组写入到输出流
     * @param bytes 字节数组内容
     * @param out 输出流
     * @param isClose 是否关闭输出流：true-关闭，false-不关闭
     * @throws IOException
     */
    public static void ouput(byte[] bytes, OutputStream out, boolean isClose) throws IOException
    {
        int buff = 1024 * 8;

        try
        {

            for (int offset = 0; offset < bytes.length; )
            {
                int len = buff;

                if (offset + buff > bytes.length)
                {
                    len = bytes.length - offset;
                }

                out.write(bytes, offset, len);

                out.flush();

                offset += len;
            }

            out.flush();
        }
        finally
        {
            if(out != null && isClose)
            {
                out.close();
            }
        }
    }
}
