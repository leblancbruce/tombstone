package com.lebcool.common.logging;

import org.slf4j.LoggerFactory;

/**
 * <p>
 * This class wraps a {@link org.slf4j.Logger} object to provide a
 * means to log the source object emitting the log message.
 * </p>
 */
public final class Logger
{
    //:: ---------------------------------------------------------------------
    //:: Public Construction

    /**
     * Constructs an instance of a {@link Logger} object using the supplied
     * class.
     *
     * @param clazz
     *        The {@link Class} object for the class that wishes to emit log
     *        statements.  This cannot be null.
     */
    public Logger(final Class<?> clazz)
    {
        _logger = LoggerFactory.getLogger(clazz);
    }

    //:: ---------------------------------------------------------------------
    //:: Public Interface

    /**
     * Emits a log message at the info level prefixed by the
     * source object's {@link Object#toString()} value.
     *
     * @param source
     *        The source object used to prefix the log message.  This cannot
     *        be null.
     * @param message
     *        The log message to emit.  This cannot be null.
     * @param arguments
     *        A variable argument list of objects whose string representations
     *        need to be inserted into the supplied message.  This is optional
     *        as there may be no string insertion necessary.
     */
    public void info(
            final Object source,
            final String message,
            final Object ... arguments)
    {
        _logger.info(prefixLogMessage(source, message), arguments);
    }

    /**
     * Emits a log message at the warn level prefixed by the
     * source object's {@link Object#toString()} value.
     *
     * @param source
     *        The source object used to prefix the log message.  This cannot
     *        be null.
     * @param message
     *        The log message to emit.  This cannot be null.
     * @param arguments
     *        A variable argument list of objects whose string representations
     *        need to be inserted into the supplied message.  This is optional
     *        as there may be no string insertion necessary.
     */
    public void warn(
        final Object source,
        final String message,
        final Object ... arguments)
    {
        _logger.warn(prefixLogMessage(source, message), arguments);
    }

    /**
     * Emits a log message at the warn level prefixed by the
     * source object's {@link Object#toString()} value.  This will also log
     * the stack trace for the supplied {@link Throwable} object.
     *
     * @param source
     *        The source object used to prefix the log message.  This cannot
     *        be null.
     * @param message
     *        The log message to emit.  This cannot be null.
     * @param e
     *        The {@link Throwable} object that caused a fault.  The stack
     *        trace will be logged.  This cannot be null.
     */
    public void warn(
        final Object source,
        final String message,
        final Throwable e)
    {
        _logger.warn(prefixLogMessage(source, message), e);
    }

    /**
     * Emits a log message at the error level prefixed by the
     * source object's {@link Object#toString()} value.
     *
     * @param source
     *        The source object used to prefix the log message.  This cannot
     *        be null.
     * @param message
     *        The log message to emit.  This cannot be null.
     * @param arguments
     *        A variable argument list of objects whose string representations
     *        need to be inserted into the supplied message.  This is optional
     *        as there may be no string insertion necessary.
     */
    public void error(
        final Object source,
        final String message,
        final Object ... arguments)
    {
        _logger.error(prefixLogMessage(source, message), arguments);
    }

    /**
     * Emits a log message at the error level prefixed by the
     * source object's {@link Object#toString()} value.  This will also log
     * the stack trace for the supplied {@link Throwable} object.
     *
     * @param source
     *        The source object used to prefix the log message.  This cannot
     *        be null.
     * @param message
     *        The log message to emit.  This cannot be null.
     * @param e
     *        The {@link Throwable} object that caused a fault.  The stack
     *        trace will be logged.  This cannot be null.
     */
    public void error(
        final Object source,
        final String message,
        final Throwable e)
    {
        _logger.error(prefixLogMessage(source, message), e);
    }

    /**
     * Emits a log message at the trace level prefixed by the
     * source object's {@link Object#toString()} value.
     *
     * @param source
     *        The source object used to prefix the log message.  This cannot
     *        be null.
     * @param message
     *        The log message to emit.  This cannot be null.
     * @param arguments
     *        A variable argument list of objects whose string representations
     *        need to be inserted into the supplied message.  This is optional
     *        as there may be no string insertion necessary.
     */
    public void trace(
        final Object source,
        final String message,
        final Object ... arguments)
    {
        _logger.trace(prefixLogMessage(source, message), arguments);
    }

    /**
     * Emits a log message at the debug level prefixed by the
     * source object's {@link Object#toString()} value.
     *
     * @param source
     *        The source object used to prefix the log message.  This cannot
     *        be null.
     * @param message
     *        The log message to emit.  This cannot be null.
     * @param arguments
     *        A variable argument list of objects whose string representations
     *        need to be inserted into the supplied message.  This is optional
     *        as there may be no string insertion necessary.
     */
    public void debug(
        final Object source,
        final String message,
        final Object ... arguments)
    {
        _logger.debug(prefixLogMessage(source, message), arguments);
    }

    //:: ---------------------------------------------------------------------
    //:: Private Interface

    /**
     * A helper method used to concatenate the source object's
     * {@link Object#toString()} value to the supplied log message string.
     *
     *@param source
     *        The source object used to prefix the overall message.  This
     *        cannot be null.
     * @param message
     *        The log message to finish the overall message with.
     *        This cannot be null.
     *
     * @return A log message string containing the source's toString value
     *        followed by the actual log message separated by a colon.  This
     *        cannot be null.
     */
    private String prefixLogMessage(final Object source, final String message)
    {
        return source + ": " + message;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    // The actual log4j logger object used to emit log messages.  This cannot
    // be null.
    private final org.slf4j.Logger _logger;
}