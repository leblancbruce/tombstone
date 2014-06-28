package com.tombstone.server.domain.exception;

public final class LoadException extends Exception
{
    //:: ---------------------------------------------------------------------
    //:: Public Construction

    public LoadException(final String message, final Throwable e)
    {
        super(message, e);
    }

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;
}
