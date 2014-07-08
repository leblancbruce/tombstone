package com.tombstone.server.domain;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

public final class CemeterySummary implements Serializable
{
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

    public Long getThumbnailImageId()
    {
        return _thumbnailImageId;
    }

    @Override
    public String toString()
    {
        return getClass().getName() + "["
            + "cemeteryId=" + _cemeteryId
            + " name=\"" + _name + "\""
            + " establishedYear=" + _establishedYear
            + " cemeteryStatus=" + _cemeteryStatus
            + " numberOfPlots=" + _numberOfPlots
            + " numberOfDeceased=" + _numberOfDeceased
            + " lastUpdatedDateTime=" + _lastUpdatedDateTime
            + " lastUpdatedByUserName=" + _lastUpdatedByUserName
            + " thumbnailImageId=" + _thumbnailImageId
            + "]";
    }

    //:: ---------------------------------------------------------------------
    //:: Package-Private Construction

    CemeterySummary(
        final long cemeteryId,
        final String name,
        final Short establishedYear,
        final CemeteryStatus cemeteryStatus,
        final long numberOfPlots,
        final long numberOfDeceased,
        final LocalDateTime lastUpdatedDateTime,
        final String lastUpdatedByUserName,
        final Long thumbnailImageId)
    {
        _cemeteryId = cemeteryId;
        _name = name;
        _establishedYear = establishedYear;
        _cemeteryStatus = cemeteryStatus;
        _numberOfPlots = numberOfPlots;
        _numberOfDeceased = numberOfDeceased;
        _lastUpdatedDateTime = lastUpdatedDateTime;
        _lastUpdatedByUserName = lastUpdatedByUserName;
        _thumbnailImageId = thumbnailImageId;
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

    private final Long _thumbnailImageId;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    // The following is needed by the serializable contract.
    private static final long serialVersionUID = 1L;
}
