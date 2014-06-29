package com.tombstone.server.domain;

import org.joda.time.LocalDateTime;

import com.lebcool.common.domain.DomainObject;


public abstract class AuditableDomainObject extends DomainObject
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public long getCreatedByApplicationUserId()
    {
        return _createdByApplicationUserId;
    }

    public long getUpdatedByApplicationUserId()
    {
        return _updatedByApplicationUserId;
    }

    //:: ---------------------------------------------------------------------
    //:: Package-Private Interface

    protected void updateControlledFields(
        final AuditableDomainObject auditableDomainObject,
        final Long id,
        final long version,
        final LocalDateTime createdOn,
        final LocalDateTime updatedOn,
        final long createdByApplicationUserId,
        final long updatedByApplicationUserId)
    {
        super.updateControlledFields(id, version, createdOn, updatedOn);

        _createdByApplicationUserId = createdByApplicationUserId;
        _updatedByApplicationUserId = updatedByApplicationUserId;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private Long _createdByApplicationUserId;

    private Long _updatedByApplicationUserId;

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    // The following is needed by the serializable contract.
    private static final long serialVersionUID = 1L;
}
