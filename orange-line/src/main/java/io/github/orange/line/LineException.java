package io.github.orange.line;

/**
 * @author orange
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
