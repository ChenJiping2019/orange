package io.github.orange.utils;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author: orange
 * xml与javaBean转换工具，通过{@link Marshaller}编组指定的内容，{@link Unmarshaller}解组给定的内容，如果不给定xml的编码格式，则在解组或者编组过程中使用
 * 默认的编码格式{@value DEFAULT_ENCODING}
 */
public class JAXBUtil
{
    /**默认的XML输出编码格式*/
    public static final String DEFAULT_ENCODING = "UTF-8";

    /**JAXBContext实例集合：key-Class Type*/
    private static Map<Class<?>, JAXBContext> JAXBContextMap = new WeakHashMap<>();


    /**
     *
     * @param obj 待转换的对象
     * @param encoding xml输出编码格式
     * @return 返回obj对应的xml字符串，如果obj为null,则返回null
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public static String objectToXmlStr(Object obj, String encoding) throws JAXBException, UnsupportedEncodingException
    {
        if(obj == null)
        {
            return null;
        }

        // 获取marshaller对象
        Marshaller marshaller = getMarshaller(obj.getClass());

        // 设置编码字符集
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

        // 格式化XML输出，有分行和缩进
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        marshaller.marshal(obj, baos);

        String xmlStr = new String(baos.toByteArray(), encoding);

        return xmlStr;
    }

    /**
     * 将obj按默认的编码格式{@value DEFAULT_ENCODING}转成字符串
     * @param obj 待转换的对象
     * @return 返回obj对应的xml字符串，如果obj为null,则返回null
     * @throws Exception
     */
    public static String objectToXmlStr(Object obj) throws Exception
    {
        return objectToXmlStr(obj, DEFAULT_ENCODING);
    }


    /**
     *
     * @param obj 待转换的对象
     * @param file 要写入的文件。 如果这个文件已经存在，它将被覆盖
     * @param encoding xml输出编码格式
     * @throws Exception
     */
    public static void objectToXmlStr(Object obj, File file, String encoding) throws Exception
    {
        if(obj == null)
        {
            return;
        }

        // 获取marshaller对象
        Marshaller marshaller = getMarshaller(obj.getClass());

        // 设置编码字符集
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

        // 格式化XML输出，有分行和缩进
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(obj, file);
    }

    /**
     * 将obj按照默认的编码格式{@value DEFAULT_ENCODING}写入文件
     * @param obj 待转换的对象
     * @param file 要写入的文件。 如果这个文件已经存在，它将被覆盖
     * @throws Exception
     */
    public static void objectToXmlStr(Object obj, File file) throws Exception
    {
        objectToXmlStr(obj, file, DEFAULT_ENCODING);
    }


    /**
     *
     * @param obj 待转换的对象
     * @param encoding 编码格式
     * @return 根据obj内容，生成{@link Document}对象，如果obj为null,则返回null
     * @throws JAXBException
     * @throws JDOMException
     * @throws IOException
     */
    public static Document objectToXmlDoc(Object obj, String encoding) throws JAXBException, JDOMException, IOException
    {

        if(obj == null)
        {
            return null;
        }

        // 获取marshaller对象
        Marshaller marshaller = getMarshaller(obj.getClass());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(obj, baos);

        ByteArrayInputStream baIs = new ByteArrayInputStream(baos.toByteArray());

        InputSource mInputSource = new InputSource(baIs);
        mInputSource.setEncoding(encoding);

        SAXBuilder mSAXBuilder = new SAXBuilder();

        return mSAXBuilder.build(mInputSource);
    }

    /**
     * 从指定的文件解组XML数据并返回指定的type对象，如果给定的文件不存在或者不是文件，则返回null
     * @param file xml文件
     * @param type 实例对象{@link Class}类型
     * @return 返回type实例对象
     * @throws Exception
     */
    public static <T> T xmlFileToObject(File file, Class<T> type) throws Exception
    {
        if(file == null || !file.exists() || !file.isFile() )
        {
            return null;
        }

        Unmarshaller unmarshaller = getUnmarshaller(type);


        T bean = (T) unmarshaller.unmarshal(file);

        return bean;
    }

    /**
     * 从指定的输入流解组XML数据并返回指定的type对象，如果给定的输入流为null，则返回null
     * @param is xml输入流
     * @param type 实例对象{@link Class}类型
     * @return 返回type实例对象
     * @throws Exception
     */
    public static <T> T xmlIsStrToObject(InputStream is, Class<T> type) throws Exception
    {
        if(is == null)
        {
            return null;
        }

        Unmarshaller unmarshaller = getUnmarshaller(type);

        T bean = (T) unmarshaller.unmarshal(is);

        return bean;
    }


    /**
     *
     * @param doc 解组的{@link Document}对象
     * @param type  实例对象{@link Class}类型
     * @param encoding {@link Document}对象的编码格式
     * @return 返回type实例对象
     * @throws Exception
     */
    public static <T> T xmlDocToObject(Document doc, Class<T> type, String encoding) throws Exception
    {
        if(doc == null)
        {
            return null;
        }

        Format mFormat = Format.getRawFormat().setEncoding(encoding);

        XMLOutputter outputter = new XMLOutputter(mFormat);

        ByteArrayOutputStream baOs = new ByteArrayOutputStream();

        //读入到byte字节
        outputter.output(doc, baOs);

        ByteArrayInputStream baIs = new ByteArrayInputStream(baOs.toByteArray());

        return xmlIsStrToObject(baIs, type);
    }

    /**
     * 按默认的编码格式{@value DEFAULT_ENCODING}解组给定的{@link Document}对象
     * @param doc 解组的{@link Document}对象
     * @param type 实例对象{@link Class}类型
     * @return 返回type实例对象
     * @throws Exception
     */
    public static <T> T xmlDocToObject(Document doc, Class<T> type) throws Exception
    {
        return xmlDocToObject(doc, type, DEFAULT_ENCODING);
    }

    /**
     *
     * @param xml xml字符串内容
     * @param type 实例对象{@link Class}类型
     * @param encoding xml内容编码格式
     * @return 返回type实例对象
     * @throws Exception
     */
    public static <T> T xmlStrToObject(String xml, Class<T> type, String encoding) throws Exception
    {
        ByteArrayInputStream baIs = new ByteArrayInputStream(xml.getBytes(encoding));

        return xmlIsStrToObject(baIs, type);
    }

    /**
     * 按默认的编码格式{@value DEFAULT_ENCODING}解组给定的xml字符串内容
     * @param xml xml字符串内容
     * @param type 实例对象{@link Class}类型
     * @return 返回type实例对象
     * @throws Exception
     */
    public static <T> T xmlStrToObject(String xml, Class<T> type) throws Exception
    {
        return xmlStrToObject(xml, type, DEFAULT_ENCODING);
    }

    /**
     *
     * @param type 指定对象的{@link Class}类型
     * @return 返回type对应的 {@link Unmarshaller}实例
     * @throws JAXBException
     */
    private static <T> Unmarshaller getUnmarshaller(Class<T> type) throws JAXBException
    {

        JAXBContext context = getStoredJAXBContext(type);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        return unmarshaller;
    }

    /**
     *
     * @param type 指定对象的{@link Class}类型
     * @return 返回type对应的 {@link Marshaller}实例
     * @throws JAXBException
     */
    private static <T> Marshaller getMarshaller(Class<T> type) throws JAXBException
    {

        JAXBContext context = getStoredJAXBContext(type);

        Marshaller marshaller = context.createMarshaller();

        return marshaller;
    }

    /**
     * 获取指定type的{@link JAXBContext}对象
     * @param type 指定对象的{@link Class}类型
     * @return 返回type对应的 {@link JAXBContext}实例
     * @throws JAXBException
     */
    public static <T> JAXBContext getStoredJAXBContext(Class<T> type) throws JAXBException
    {
        synchronized (JAXBContextMap)
        {
            JAXBContext context = JAXBContextMap.get(type);

            if(context == null)
            {
                context = JAXBContext.newInstance(type);

                JAXBContextMap.put(type, context);
            }

            return context;
        }

    }
}
