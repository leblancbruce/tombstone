package com.tombstone.server.domain;

import com.lebcool.common.domain.DomainObject;

public abstract class AuditableDomainObject extends DomainObject
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public long getCreatedByApplicationUserId()
    {
        return _createdByApplicationUserId;
    }

    public void setCreatedByApplicationUserId(
        final long createdByApplicationUserId)
    {
        _createdByApplicationUserId = createdByApplicationUserId;
    }

    public long getUpdatedByApplicationUserId()
    {
        return _updatedByApplicationUserId;
    }

    public void setUpdatedByApplicationUserId(
        final long updatedByApplicationUserId)
    {
        _updatedByApplicationUserId = updatedByApplicationUserId;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private Long _createdByApplicationUserId;

    private Long _updatedByApplicationUserId;
}
