package com.tombstone.server.domain;

public enum CemeteryStatus
{
    UNKNOWN,

    IN_USE,

    DISUSED;

    public String getResourceBundleKey()
    {
        return "cemetery.status." + name();
    }
}
