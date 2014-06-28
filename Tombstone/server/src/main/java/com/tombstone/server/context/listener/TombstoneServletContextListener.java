package com.tombstone.server.context.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lebcool.common.logging.Logger;

public final class TombstoneServletContextListener
    implements ServletContextListener
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    @Override
    public void contextInitialized(final ServletContextEvent event)
    {
        LOGGER.info(this, "The servlet context was initialized.");
    }

    @Override
    public void contextDestroyed(final ServletContextEvent event)
    {
        LOGGER.info(this, "The servlet context was destroyed.");
    }

    @Override
    public String toString()
    {
        return getClass().getName();
    }

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final Logger LOGGER
        = new Logger(TombstoneServletContextListener.class);
}
