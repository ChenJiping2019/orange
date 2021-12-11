package io.github.orange.line;

/**
 * @author orange
 * @Description:
 * @date 2021/12/9 14:37
 */
public class LineException extends RuntimeException
{
    public LineException()
    {
        super();
    }

    public LineException(String message)
    {
        super(message);
    }

    public LineException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
