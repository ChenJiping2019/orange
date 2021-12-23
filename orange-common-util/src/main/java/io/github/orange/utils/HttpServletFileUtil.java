package io.github.orange.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * @author: orange
 * HttpServlet文件处理工具类，例：
 * 下载文件：
 *   File file = new File("test.txt");
 *   HttpServletFileUtil.output(file, request, response);
 */
public class HttpServletFileUtil
{
    /**文件名以utf-8编码的浏览器内核*/
    public static final String[]  UTF8_KERNEL = {"MSIE", "TRIDENT", "EDGE"};

    /**
     * 将给定文件输出到{@link HttpServletResponse}响应流中，如果文件不存在或不是文件，则不做任何操作
     * @param file 给定的文件
     * @param request HttpServlet请求
     * @param response HttpServlet响应
     * @throws IOException
     */
    public static void output(File file, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        if(file == null || !file.exists() || file.isDirectory())
        {
            return;
        }

        //文件名
        String filename = encodingFilename(file.getName(), request);

        OutputStream out = null;

        FileInputStream fileInputStream = null;

        try
        {
            out = response.getOutputStream();

            response.reset();

            response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");


            fileInputStream = new FileInputStream(file);

            //定义缓存
            byte temp[] = new byte[1024 * 8];

            int readLen = 0;

            while((readLen = fileInputStream.read(temp)) != -1)
            {
                out.write(temp, 0, readLen);

                out.flush();
            }

            out.flush();
        }
        finally
        {
            if(out != null)
            {
                out.close();
            }

            if(fileInputStream != null)
            {
                fileInputStream.close();
            }
        }
    }

    /**
     * 将给定的文件字节数组输出到{@link HttpServletResponse}响应流中，如果内容为null，则不做任何操作
     * @param fileBytes 文件字节数组
     * @param filename 文件名
     * @param request HttpServlet请求
     * @param response HttpServlet响应
     * @throws IOException
     */
    public static void output(byte fileBytes[], String filename, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        if(fileBytes == null)
        {
            return;
        }

        //文件名
        String newFilename = encodingFilename(filename, request);

        OutputStream out = null;


        try
        {
            out = response.getOutputStream();

            response.reset();

            response.addHeader("Content-Disposition", "attachment; filename=\"" + newFilename + "\"");

            StreamUtil.ouputWithoutClose(fileBytes, out);

            out.flush();
        }
        finally
        {
            if(out != null)
            {
                out.close();
            }
        }
    }


    /**
     * 编码文件名，避免中文乱码
     * @param filename 文件名
     * @param request HttpServlet请求
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String encodingFilename(String filename, HttpServletRequest request) throws UnsupportedEncodingException
    {
        //获取请求头
        String userAgent = request.getHeader("User-Agent").toUpperCase();

        String encodedFilename = null;

        if(Arrays.binarySearch(UTF8_KERNEL, userAgent) > -1)
        {
            encodedFilename = URLEncoder.encode(filename, "UTF-8");
        }
        else
        {
            encodedFilename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        }

        return encodedFilename;
    }
}
