package com.tombstone.server.domain;

import java.io.Serializable;

public final class ApplicationUserSummary implements Serializable
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public long getApplicationUserId()
    {
        return _applicationUserId;
    }

    public String getFirstName()
    {
        return _firstName;
    }

    public String getLastName()
    {
        return _lastName;
    }

    //:: ---------------------------------------------------------------------
    //:: Package-Private Construction

    ApplicationUserSummary(
        final long applicationUserId,
        final String firstName,
        final String lastName)
    {
        _applicationUserId = applicationUserId;
        _firstName = firstName;
        _lastName = lastName;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private final long _applicationUserId;

    private final String _firstName;

    private final String _lastName;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    // The following is needed by the serializable contract.
    private static final long serialVersionUID = 1L;

}
