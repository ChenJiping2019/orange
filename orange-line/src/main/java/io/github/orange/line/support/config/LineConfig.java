package io.github.orange.line.support.config;

import io.github.orange.line.Constants;
import io.github.orange.line.parser.ParserConfig;
import io.github.orange.line.serializer.SerializeConfig;

/**
 * @author orange
 * Line工具的配置
 */
public class LineConfig
{
    private String separator;

    private String dateFormat;

    private SerializeConfig serializeConfig;

    private ParserConfig parserConfig;

    public LineConfig()
    {
        this.separator = Constants.DEFAULT_SEPARATOR;

        this.dateFormat = Constants.DEFAULT_DATE_FORMAT;

        this.serializeConfig = SerializeConfig.globalInstance;

        this.parserConfig = ParserConfig.globalInstance;
    }

    public String getSeparator()
    {
        return separator;
    }

    public void setSeparator(String separator)
    {
        this.separator = separator;
    }

    public String getDateFormat()
    {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    public SerializeConfig getSerializeConfig()
    {
        return serializeConfig;
    }

    public void setSerializeConfig(SerializeConfig serializeConfig)
    {
        this.serializeConfig = serializeConfig;
    }

    public ParserConfig getParserConfig()
    {
        return parserConfig;
    }

    public void setParserConfig(ParserConfig parserConfig)
    {
        this.parserConfig = parserConfig;
    }
}
