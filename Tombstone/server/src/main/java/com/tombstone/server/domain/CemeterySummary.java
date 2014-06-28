package com.tombstone.server.domain;

import org.joda.time.LocalDateTime;

public final class CemeterySummary
{
    //:: ---------------------------------------------------------------------
    //:: Public Construction

    public CemeterySummary(
        final long cemeteryId,
        final String name,
        final short establishedYear,
        final CemeteryStatus cemeteryStatus,
        final long numberOfPlots,
        final long numberOfDeceased,
        final LocalDateTime lastUpdatedDateTime,
        final String lastUpdatedByUserName)
    {
        _cemeteryId = cemeteryId;
        _name = name;
        _establishedYear = establishedYear;
        _cemeteryStatus = cemeteryStatus;
        _numberOfPlots = numberOfPlots;
        _numberOfDeceased = numberOfDeceased;
        _lastUpdatedDateTime = lastUpdatedDateTime;
        _lastUpdatedByUserName = lastUpdatedByUserName;
    }

    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public long getCemeteryId()
    {
        return _cemeteryId;
    }

    public String getName()
    {
        return _name;
    }

    public Short getEstablishedYear()
    {
        return _establishedYear;
    }

    public CemeteryStatus getCemeteryStatus()
    {
        return _cemeteryStatus;
    }

    public long getNumberOfPlots()
    {
        return _numberOfPlots;
    }

    public long getNumberOfDeceased()
    {
        return _numberOfDeceased;
    }

    public LocalDateTime getLastUpdatedDateTime()
    {
        return _lastUpdatedDateTime;
    }

    public String getLastUpdatedByUserName()
    {
        return _lastUpdatedByUserName;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private final long _cemeteryId;

    private final String _name;

    private final Short _establishedYear;

    private final CemeteryStatus _cemeteryStatus;

    private final long _numberOfPlots;

    private final long _numberOfDeceased;

    private final LocalDateTime _lastUpdatedDateTime;

    private final String _lastUpdatedByUserName;
}
