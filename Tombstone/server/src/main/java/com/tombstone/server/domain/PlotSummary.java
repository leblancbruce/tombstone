package com.tombstone.server.domain;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

public final class PlotSummary implements Serializable
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public long getPlotId()
    {
        return _plotId;
    }

    public String getName()
    {
        return _name;
    }

    public PlotType getPlotType()
    {
        return _plotType;
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
            + "plotId=" + _plotId
            + " name=\"" + _name + "\""
            + " plotType=" + _plotType
            + " numberOfDeceased=" + _numberOfDeceased
            + " lastUpdatedDateTime=" + _lastUpdatedDateTime
            + " lastUpdatedByUserName=" + _lastUpdatedByUserName
            + " thumbnailImageId=" + _thumbnailImageId
            + "]";
    }

    //:: ---------------------------------------------------------------------
    //:: Package-Private Construction

    PlotSummary(
        final long plotId,
        final String name,
        final PlotType plotType,
        final long numberOfDeceased,
        final LocalDateTime lastUpdatedDateTime,
        final String lastUpdatedByUserName,
        final Long thumbnailImageId)
    {
        _plotId = plotId;
        _name = name;
        _plotType = plotType;
        _numberOfDeceased = numberOfDeceased;
        _lastUpdatedDateTime = lastUpdatedDateTime;
        _lastUpdatedByUserName = lastUpdatedByUserName;
        _thumbnailImageId = thumbnailImageId;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private final long _plotId;

    private final String _name;

    private final PlotType _plotType;

    private final long _numberOfDeceased;

    private final LocalDateTime _lastUpdatedDateTime;

    private final String _lastUpdatedByUserName;

    private final Long _thumbnailImageId;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    // The following is needed by the serializable contract.
    private static final long serialVersionUID = 1L;
}
