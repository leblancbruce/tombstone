package com.tombstone.server.rest.exception;

public final class EndPointInstantiationException extends RuntimeException
{
    //:: ---------------------------------------------------------------------
    //:: Public Construction

    public EndPointInstantiationException(
        final String message,
        final Throwable e)
    {
        super(message, e);
    }

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;
}
