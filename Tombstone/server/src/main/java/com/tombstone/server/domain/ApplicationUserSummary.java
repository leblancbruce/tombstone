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

    public Long getThumbnailImageId()
    {
        return _thumbnailImageId;
    }

    //:: ---------------------------------------------------------------------
    //:: Package-Private Construction

    ApplicationUserSummary(
        final long applicationUserId,
        final String firstName,
        final String lastName,
        final Long thumbnailImageId)
    {
        _applicationUserId = applicationUserId;
        _firstName = firstName;
        _lastName = lastName;
        _thumbnailImageId = thumbnailImageId;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private final long _applicationUserId;

    private final String _firstName;

    private final String _lastName;

    private final Long _thumbnailImageId;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    // The following is needed by the serializable contract.
    private static final long serialVersionUID = 1L;
}
