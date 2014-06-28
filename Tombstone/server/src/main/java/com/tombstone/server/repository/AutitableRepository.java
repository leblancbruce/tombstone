package com.tombstone.server.repository;

import com.tombstone.server.domain.ApplicationUser;

abstract class AutitableRepository extends ServerRepository
{
    //------------------------------------------------------------------------
    // :: Package-Private Construction

    AutitableRepository(final ApplicationUser loggedInUser)
    {
        super();

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

    //------------------------------------------------------------------------
    // :: Private Data Members

    private final ApplicationUser _loggedInUser;
}
