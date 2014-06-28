package com.tombstone.server.domain.exception;


public final class RepositoryInstantiationException extends RuntimeException
{
    //------------------------------------------------------------------------
    // :: Public Construction

    public RepositoryInstantiationException(
        final String message, final Throwable e)
    {
        super(message, e);
    }

    //------------------------------------------------------------------------
    // :: Private Constants

    private static final long serialVersionUID = 1L;
}
