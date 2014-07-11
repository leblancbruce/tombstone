package com.tombstone.server.bean;

import java.io.Serializable;

public abstract class Bean implements Serializable
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    /**
     * @return A string representation of this class used for logging
     *         purposes.
     */
    @Override
    public String toString()
    {
        return getClass().getName();
    }

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;
}
