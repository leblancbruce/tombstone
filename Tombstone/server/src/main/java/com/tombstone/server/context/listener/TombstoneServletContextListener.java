package com.tombstone.server.context.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lebcool.common.logging.Logger;

/**
 * <p>
 * The servlet context listener for the Tombstone application.
 * </p>
 */
public final class TombstoneServletContextListener
    implements ServletContextListener
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextInitialized(final ServletContextEvent event)
    {
        // Simply log the fact that the context was initialized.
        LOGGER.info(this, "The servlet context was initialized.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextDestroyed(final ServletContextEvent event)
    {
        // Simply log the fact that the context was destroyed.
        LOGGER.info(this, "The servlet context was destroyed.");
    }

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

    // The logger instance used to emit log messages to the application log.
    private static final Logger LOGGER
        = new Logger(TombstoneServletContextListener.class);
}
