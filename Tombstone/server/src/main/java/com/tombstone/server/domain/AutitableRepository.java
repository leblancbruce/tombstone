package com.tombstone.server.domain;

import javax.sql.DataSource;

import org.joda.time.LocalDateTime;

import com.lebcool.common.domain.Repository;

abstract class AutitableRepository extends Repository
{
    //------------------------------------------------------------------------
    // :: Package-Private Construction

    AutitableRepository(
        final DataSource dataSource,
        final ApplicationUser loggedInUser)
    {
        super(dataSource);

        if(loggedInUser == null || loggedInUser.getId() == null)
        {
            throw new IllegalArgumentException("Cannot instantiate an "
                + "auditable repository using a null user or a user that "
                + "has not been persisted yet.");
        }

        _loggedInUser = loggedInUser;
    }

    //------------------------------------------------------------------------
    // :: Package-Private Interface

    final ApplicationUser getLoggedInUser()
    {
        return _loggedInUser;
    }


    static void updateControlledFields(
        final AuditableDomainObject auditableDomainObject,
        final Long id,
        final long version,
        final LocalDateTime createdOn,
        final LocalDateTime updatedOn,
        final long createdByApplicationUserId,
        final long updatedByApplicationUserId)
    {
        auditableDomainObject.updateControlledFields(
             auditableDomainObject,
             id,
             version,
             createdOn,
             updatedOn,
             createdByApplicationUserId,
             updatedByApplicationUserId);
    }

    //------------------------------------------------------------------------
    // :: Private Data Members

    private final ApplicationUser _loggedInUser;
}
