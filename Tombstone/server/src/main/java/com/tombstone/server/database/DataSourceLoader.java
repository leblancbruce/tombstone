package com.tombstone.server.database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.lebcool.common.logging.Logger;

public final class DataSourceLoader
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public static DataSource load() throws NamingException
    {
        // Log what we're about to attempt.
        LOGGER.info(DataSourceLoader.class.getName(),
            "Attempting to obtain the database datasource "
                + "object using name=\"{}\"", DATASOURCE_JNDI_NAME);

        try
        {
            // Request the data source object from the container via a jndi
            // lookup.
            final Context initialContext = new InitialContext();
            final Context envContext
                = (Context)initialContext.lookup("java:comp/env");

            final DataSource dataSource
                = (DataSource)envContext.lookup(DATASOURCE_JNDI_NAME);

            LOGGER.info(DataSourceLoader.class.getName(),
                "Successfully obtained the datasource object.  Returning it.");

            return dataSource;
        }
        catch(final NamingException e)
        {
            // Uh-oh.  There was an issue looking up the data source object
            // within the container via jndi.  Log the error.  A method
            // marked with the post construct annotation is not permitted
            // to throw a checked exception.  Bundle the naming exception into
            // a new runtime exception and throw it.
            LOGGER.error(DataSourceLoader.class.getName(),
                "Unable to load the database data source using "
                    + "name=\"{}\"", DATASOURCE_JNDI_NAME);

            throw e;
        }

    }

    //:: ---------------------------------------------------------------------
    //:: Private Construction

    private DataSourceLoader()
    {

    }

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    // The jndi resource name associated with the datasource object managed
    // by the container.
    private static final String DATASOURCE_JNDI_NAME = "jdbc/tombstone";

    private static final Logger LOGGER = new Logger(DataSourceLoader.class);
}
