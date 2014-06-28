package com.tombstone.server.domain;

public final class Cemetery extends AuditableDomainObject
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public String getName()
    {
        return _name;
    }

    public void setName(final String name)
    {
        _name = name;
    }

    public String getAddress()
    {
        return _address;
    }

    public void setAddress(final String address)
    {
        _address = address;
    }

    public String getCity()
    {
        return _city;
    }

    public void setCity(final String city)
    {
        _city = city;
    }

    public Short getEstablishedDay()
    {
        return _establishedDay;
    }

    public void setEstablishedDay(final Short establishedDay)
    {
        _establishedDay = establishedDay;
    }

    public Short getEstablishedMonth()
    {
        return _establishedMonth;
    }

    public void setEstablishedMonth(final Short establishedMonth)
    {
        _establishedMonth = establishedMonth;
    }

    public Short getEstablishedYear()
    {
        return _establishedYear;
    }

    public void setEstablishedYear(final Short establishedYear)
    {
        _establishedYear = establishedYear;
    }

    public CemeteryStatus getCemeteryStatus()
    {
        return _cemeteryStatus;
    }

    public void setCemeteryStatus(final CemeteryStatus cemeteryStatus)
    {
        _cemeteryStatus = cemeteryStatus;
    }

    public County getCounty()
    {
        return _county;
    }

    public void setCounty(final County county)
    {
        _county = county;
    }

    //:: ---------------------------------------------------------------------
    //:: Protected Interface

    @Override
    protected String getMembersAsKeyValueString()
    {
        return " name=\"" + _name + "\""
            + " address=\"" + _address + "\""
            + " city=\"" + _city + "\""
            + " establishedDay=" + _establishedDay
            + " establishedMonth=" + _establishedMonth
            + " establishedYear=" + _establishedYear
            + " cemeteryStatus=" + _cemeteryStatus
            + " county=" + _county;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private String _name;

    private String _address;

    private String _city;

    private Short _establishedDay;

    private Short _establishedMonth;

    private Short _establishedYear;

    private CemeteryStatus _cemeteryStatus;

    private County _county;
}
