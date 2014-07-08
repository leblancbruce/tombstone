package com.tombstone.server.domain;

public enum PlotType
{
    UNKNOWN,

    SINGLE,

    MULTIPLE,

    MASS,

    UNMARKED;

    public String getResourceBundleKey()
    {
        return "plot.type." + name();
    }
}
