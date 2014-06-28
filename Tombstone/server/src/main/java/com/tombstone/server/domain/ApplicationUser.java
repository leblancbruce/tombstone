package com.tombstone.server.domain;

import com.lebcool.common.domain.DomainObject;

public final class ApplicationUser extends DomainObject
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public String getFirstName()
    {
        return _firstName;
    }

    public void setFirstName(final String firstName)
    {
        _firstName = firstName;
    }

    public String getLastName()
    {
        return _lastName;
    }

    public void setLastName(final String lastName)
    {
        _lastName = lastName;
    }

    public String getUserName()
    {
        return _userName;
    }

    public void setUserName(final String userName)
    {
        _userName = userName;
    }

    public String getPassword()
    {
        return _password;
    }

    public void setPassword(final String password)
    {
        _password = password;
    }

    public boolean isActive()
    {
        return _active;
    }

    public void setActive(final boolean active)
    {
        _active = active;
    }

    //:: ---------------------------------------------------------------------
    //:: Protected Interface

    @Override
    protected String getMembersAsKeyValueString()
    {
        return " firstName=\"" + _firstName
            + "\" lastName=\"" + _lastName
            + "\" userName=\"" + _userName
            + "\" password=\"******\""
            + " active=" + _active;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private String _firstName;

    private String _lastName;

    private String _userName;

    private String _password;

    private boolean _active;
}
