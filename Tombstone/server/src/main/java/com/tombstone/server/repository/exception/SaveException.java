package com.tombstone.server.repository.exception;

public final class SaveException extends Exception
{
    //:: ---------------------------------------------------------------------
    //:: Public Construction

    public SaveException(final String message, final Throwable e)
    {
        super(message, e);
    }

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;
}
