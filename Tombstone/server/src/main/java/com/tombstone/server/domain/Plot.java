package com.tombstone.server.domain;

public final class Plot extends AuditableDomainObject
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

    public long getCemeteryId()
    {
        return _cemeteryId;
    }

    public void setCemeteryId(final long cemeteryId)
    {
        _cemeteryId = cemeteryId;
    }

    public PlotType getPlotType()
    {
        return _plotType;
    }

    public void setPlotType(final PlotType plotType)
    {
        _plotType = plotType;
    }

    //:: ---------------------------------------------------------------------
    //:: Protected Interface

    @Override
    protected String getMembersAsKeyValueString()
    {
        return " name=\"" + _name + "\""
            + " cemeteryId=" + _cemeteryId
            + " plotType=" + _plotType;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private String _name;

    private long _cemeteryId;

    private PlotType _plotType;
}
