package io.github.orange.utils;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;

import java.io.*;

/**
 * @author: orange
 * xml构建工具类，将{@link org.jdom2.Document}转换成xml字符串，或者将xml数据字节（流）生成{@link org.jdom2.Document}对象
 */
public class JdomUtil
{
    public static String DEFAULT_ENCODING = "utf-8";

    /**doc解析类*/
    private final static ThreadLocal<SAXBuilder> threadSAXBuilder = ThreadLocal.withInitial(() -> {
        SAXBuilder builder =  new SAXBuilder();
        builder.setExpandEntities(false);
        return builder;
    });


    /**
     *
     * @param inputStream xml输入流
     * @param charset 编码格式，如果为null,则取{@value DEFAULT_ENCODING}
     * @param ignoringBoundaryWhitespace 是否应消除边界空白
     * @return 根据xml数据输入流构建 {@link Document}
     * @throws JDOMException
     * @throws IOException
     */
    public static Document build(InputStream inputStream, String charset, boolean ignoringBoundaryWhitespace) throws JDOMException, IOException
    {
        InputSource inputSource = new InputSource(inputStream);

        inputSource.setEncoding(charset == null ? DEFAULT_ENCODING : charset);

        SAXBuilder builder = threadSAXBuilder.get();

        builder.setIgnoringBoundaryWhitespace(ignoringBoundaryWhitespace);

        Document document = builder.build(inputSource);

        return document;
    }

    /**
     *
     * @param bytes xml字节数组
     * @param charset 编码格式，如果为null,则取{@value DEFAULT_ENCODING}
     * @param ignoringBoundaryWhitespace 是否应消除边界空白
     * @return 根据xml数据输入流构建 {@link Document}
     * @throws JDOMException
     * @throws IOException
     */
    public static Document build(byte[] bytes, String charset, boolean ignoringBoundaryWhitespace) throws JDOMException, IOException
    {
        return build(new ByteArrayInputStream(bytes), charset, ignoringBoundaryWhitespace);
    }

    /**
     * 默认消除边界空白进行xml字节数组解析
     * @param bytes xml字节数组
     * @param charset 编码格式，如果为null,则取{@value DEFAULT_ENCODING}
     * @return 根据xml数据输入流构建 {@link Document}
     * @throws JDOMException
     * @throws IOException
     */
    public static Document build(byte[] bytes, String charset) throws JDOMException, IOException
    {
        return build(bytes, charset, true);
    }

    /**
     * 默认采用{@value DEFAULT_ENCODING}编码并消除边界空白进行xml字节数组解析
     * @param bytes xml字节数组
     * @return 根据xml数据输入流构建 {@link Document}
     * @throws JDOMException
     * @throws IOException
     */
    public static Document build(byte[] bytes) throws JDOMException, IOException
    {
        return build(bytes, DEFAULT_ENCODING);
    }

    /**
     *
     * @param xmlStr xml字符串
     * @param ignoringBoundaryWhitespace 是否应消除边界空白
     * @return 根据xml字符串构建 {@link Document}
     * @throws JDOMException
     * @throws IOException
     */
    public static Document build(String xmlStr, boolean ignoringBoundaryWhitespace) throws JDOMException, IOException
    {
        SAXBuilder builder = threadSAXBuilder.get();

        builder.setIgnoringBoundaryWhitespace(ignoringBoundaryWhitespace);

        Document document = builder.build(new StringReader(xmlStr));

        return document;
    }

    /**
     * 默认消除边界空白进行xml字符串解析
     * @param xmlStr xml字符串
     * @return 根据xml字符串构建 {@link Document}
     * @throws JDOMException
     * @throws IOException
     */
    public static Document build(String xmlStr) throws JDOMException, IOException
    {
        return build(xmlStr, true);
    }

    /**
     *
     * @param document 待转换的{@link Document}对象
     * @param encodingDecl 声明中的编码，如：(<code>&lt;&#063;xml version="1&#046;0" encoding="UTF-8"&#063;&gt;</code>)
     *                     如果null，则忽略整个声明；
     *                     如果为空字符串，则忽略编码，如：(<code>&lt;&#063;xml version="1&#046;0"&#063;&gt;</code>)
     * @param isPretty 是否格式化
     * @return 如果document为null,则返回null
     */
    public static String toString(Document document, String encodingDecl, boolean isPretty)
    {
        if(document == null)
        {
            return null;
        }

        Format format = null;

        if(isPretty)
        {
            format = Format.getPrettyFormat();
        }
        else
        {
            format = Format.getRawFormat();
        }

        if(null == encodingDecl)
        {
            format.setOmitDeclaration(true);
        }
        else if("".equals(encodingDecl.trim()))
        {
            format.setOmitEncoding(true);
        }
        else
        {
            format.setEncoding(encodingDecl);
        }

        XMLOutputter outputter = new XMLOutputter(format);

        String xml = outputter.outputString(document);

        return xml;
    }

    /**
     * 格式化转换{@link Document}对象
     * @param document 待转换的{@link Document}对象
     * @param encodingDecl 声明中的编码，如：(<code>&lt;&#063;xml version="1&#046;0" encoding="UTF-8"&#063;&gt;</code>)
     *                     如果null，则忽略整个声明；
     *                     如果为空字符串，则忽略编码，如：(<code>&lt;&#063;xml version="1&#046;0"&#063;&gt;</code>)
     * @return 如果document为null,则返回null
     */
    public static String toFmtString(Document document, String encodingDecl)
    {
        return toString(document, encodingDecl, true);
    }

    /**
     * 格式化转换{@link Document}对象，忽略编码格式声明
     * @param document 待转换的{@link Document}对象
     * @return 如果document为null,则返回null
     */
    public static String toFmtString(Document document)
    {
        return toString(document, "", true);
    }

    /**
     * 转换{@link Document}对象
     * @param document 待转换的{@link Document}对象
     * @param encodingDecl 声明中的编码，如：(<code>&lt;&#063;xml version="1&#046;0" encoding="UTF-8"&#063;&gt;</code>)
     *                     如果null，则忽略整个声明；
     *                     如果为空字符串，则忽略编码，如：(<code>&lt;&#063;xml version="1&#046;0"&#063;&gt;</code>)
     * @return
     */
    public static String toString(Document document, String encodingDecl)
    {
        return toString(document, encodingDecl, false);
    }

    /**
     * 转换{@link Document}对象，忽略编码格式声明
     * @param document 待转换的{@link Document}对象
     * @return 如果document为null,则返回null
     */
    public static String toString(Document document)
    {
        return toString(document, "");
    }

    /**
     *
     * @param document 待转换的{@link Document}对象
     * @param encoding 编码格式，如果为null，则取{@value DEFAULT_ENCODING}
     * @param isPretty isPretty 是否格式化
     * @return 如果document为null,则返回null
     * @throws IOException
     */
    public static byte[] toBytes(Document document, String encoding, boolean isPretty) throws IOException
    {
        if(document == null)
        {
            return null;
        }

        Format format = null;

        if(isPretty)
        {
            format = Format.getPrettyFormat();
        }
        else
        {
            format = Format.getRawFormat();
        }

        format.setEncoding(encoding == null ? DEFAULT_ENCODING : encoding);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        XMLOutputter outputter = new XMLOutputter(format);

        outputter.output(document, outputStream);

        return outputStream.toByteArray();
    }

    /**
     * 格式化{@link Document}对象
     * @param document 待转换的{@link Document}对象
     * @param encoding 编码格式
     * @return 如果document为null,则返回null
     * @throws IOException
     */
    public static byte[] toFmtBytes(Document document, String encoding) throws IOException
    {
        return toBytes(document, encoding, true);
    }

    /**
     * 按默认编码格式{@value DEFAULT_ENCODING}格式化{@link Document}对象
     * @param document 待转换的{@link Document}对象
     * @return 如果document为null,则返回null
     * @throws IOException
     */
    public static byte[] toFmtBytes(Document document) throws IOException
    {
        return toBytes(document, DEFAULT_ENCODING, true);
    }

    /**
     *
     * @param document 待转换的{@link Document}对象
     * @param encoding 编码格式
     * @return 如果document为null,则返回null
     * @throws IOException
     */
    public static byte[] toBytes(Document document, String encoding) throws IOException
    {
        return toBytes(document, encoding, false);
    }

    /**
     * 按默认编码格式{@value DEFAULT_ENCODING}转换{@link Document}对象
     * @param document 待转换的{@link Document}对象
     * @return 如果document为null,则返回null
     * @throws IOException
     */
    public static byte[] toBytes(Document document) throws IOException
    {
        return toBytes(document, DEFAULT_ENCODING, false);
    }

    /**
     *
     * @param element 待转换的{@link Element}对象
     * @param encoding 编码格式，如果为null，则取{@value DEFAULT_ENCODING}
     * @param isPretty 是否格式化
     * @return 如果element为空，则返回null
     * @throws IOException
     */
    public static byte[] toBytes(Element element, String encoding, boolean isPretty) throws IOException
    {
        if(element == null)
        {
            return null;
        }

        Format format = null;

        if(isPretty)
        {
            format = Format.getPrettyFormat();
        }
        else
        {
            format = Format.getRawFormat();
        }

        format.setEncoding(encoding == null ? DEFAULT_ENCODING : encoding);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        XMLOutputter outputter = new XMLOutputter(format);

        outputter.output(element, outputStream);

        return outputStream.toByteArray();
    }

    /**
     *
     * @param element 待转换的{@link Element}对象
     * @param encoding 编码格式，如果为null，则取{@value DEFAULT_ENCODING}
     * @return 如果element为空，则返回null
     * @throws IOException
     */
    public static byte[] toBytes(Element element, String encoding) throws IOException
    {
        return  toBytes(element, encoding, false);
    }

    /**
     * 采用默认编码格式{@value DEFAULT_ENCODING}进行处理
     * @param element 待转换的{@link Element}对象
     * @return 如果element为空，则返回null
     * @throws IOException
     */
    public static byte[] toBytes(Element element) throws IOException
    {
        return  toBytes(element, DEFAULT_ENCODING);
    }

    /**
     *
     * @param element 待转换的{@link Element}对象
     * @param encoding 编码格式，如果为null，则取{@value DEFAULT_ENCODING}
     * @return 如果element为空，则返回null
     * @throws IOException
     */
    public static byte[] toFmtBytes(Element element, String encoding) throws IOException
    {
        return  toBytes(element, encoding, true);
    }

    /**
     * 采用默认编码格式{@value DEFAULT_ENCODING}进行处理
     * @param element 待转换的{@link Element}对象
     * @return 如果element为空，则返回null
     * @throws IOException
     */
    public static byte[] toFmtBytes(Element element) throws IOException
    {
        return  toFmtBytes(element, DEFAULT_ENCODING);
    }

    /**
     * 将文档内容输出到指定流，此方法不关闭输出流
     * @param document 文档对象
     * @param os 输出流
     * @param encoding 编码格式
     * @throws IOException
     */
    public static void output(Document document, OutputStream os, String encoding) throws IOException
    {
        Format format = Format.getPrettyFormat();

        format.setEncoding(encoding == null ? DEFAULT_ENCODING : encoding);

        XMLOutputter outputter = new XMLOutputter(format);

        outputter.output(document, os);
    }

    /**
     * 将文档节点内容输出到指定流，此方法不关闭输出流
     * @param element 文档节点对象{@link Element}
     * @param os 输出流
     * @param encoding 编码格式
     * @throws IOException
     */
    public static void output(Element element, OutputStream os, String encoding) throws IOException
    {
        Format format = Format.getPrettyFormat();

        format.setEncoding(encoding == null ? DEFAULT_ENCODING : encoding);

        XMLOutputter outputter = new XMLOutputter(format);

        outputter.output(element, os);
    }
}
