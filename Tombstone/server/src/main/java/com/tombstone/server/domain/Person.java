package com.tombstone.server.domain;

public final class Person extends AuditableDomainObject
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

    public String getMiddleNames()
    {
        return _middleNames;
    }

    public void setMiddleNames(final String middleNames)
    {
        _middleNames = middleNames;
    }

    public String getLastName()
    {
        return _lastName;
    }

    public void setLastName(final String lastName)
    {
        _lastName = lastName;
    }

    public String getMaidenName()
    {
        return _maidenName;
    }

    public void setLastname(final String maidenName)
    {
        _maidenName = maidenName;
    }

    //:: ---------------------------------------------------------------------
    //:: Protected Interface

    @Override
    protected String getMembersAsKeyValueString()
    {
        return " firstName=\"" + _firstName + "\""
            + " middleNames=\"" + _middleNames + "\""
            + " lastName=\"" + _lastName + "\""
            + " maidenName=\"" + _maidenName + "\""
            + " ageDays=" + _ageDays
            + " ageMonths=" + _ageMonths
            + " ageYears=" + _ageYears
            + " birthDay=" + _birthDay
            + " birthMonth=" + _birthMonth
            + " birthYear=" + _birthYear
            + " deathDay=" + _deathDay
            + " deathMonth=" + _deathMonth
            + " deathYear=" + _deathYear
            + " gender=" + _gender
            + " cemeteryId=" + _cemeteryId
            + " plotId=" + _plotId;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private String _firstName;

    private String _middleNames;

    private String _lastName;

    private String _maidenName;

    private Short _ageDays;

    private Short _ageMonths;

    private Short _ageYears;

    private Short _birthDay;

    private Short _birthMonth;

    private Short _birthYear;

    private Short _deathDay;

    private Short _deathMonth;

    private Short _deathYear;

    private Gender _gender;

    private long _cemeteryId;

    private long _plotId;
}
