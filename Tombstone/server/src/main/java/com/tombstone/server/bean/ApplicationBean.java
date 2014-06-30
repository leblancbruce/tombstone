package com.tombstone.server.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.lebcool.common.logging.Logger;

/**
 * <p>
 * This application-scoped, managed bean is used to house resources for the
 * application that are expensive to create and that must be globally
 * accessible.
 * </p>
 */
@ManagedBean
@ApplicationScoped
public final class ApplicationBean implements Serializable
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    /**
     * @return The {@link DataSource} object that manages the connections to
     *         the database.  This cannot be null.
     */
    public DataSource getDataSource()
    {
        return _dataSource;
    }

    /**
     * The initialization method that runs immediately after this bean is
     * constructed.  This method will lookup the data source object managed
     * by the container and make it globally accessible.
     */
    @PostConstruct
    public void initialize()
    {
        // Log what we're about to attempt.
        LOGGER.info(this, "Attempting to obtain the database datasource "
           + "object using name=\"{}\"", DATASOURCE_JNDI_NAME);

        try
        {
            // Request the data source object from the container via a jndi
            // lookup.
            final Context initialContext = new InitialContext();
            final Context envContext
                = (Context)initialContext.lookup("java:comp/env");

            _dataSource
                = (DataSource)envContext.lookup(DATASOURCE_JNDI_NAME);

            LOGGER.info(this, "Successfully obtained the datasource object.");
        }
        catch(final NamingException e)
        {
            // Uh-oh.  There was an issue looking up the data source object
            // within the container via jndi.  Log the error.  A method
            // marked with the post construct annotation is not permitted
            // to throw a checked exception.  Bundle the naming exception into
            // a new runtime exception and throw it.
            LOGGER.error(this, "Unable to load the database data source using "
                + "name=\"{}\"", DATASOURCE_JNDI_NAME);

            throw new RuntimeException("The data source object could not "
                + "be retrived from the container.", e);
        }
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
    //:: Private Data Members

    // The database data source object that manages the database connections
    // used by this application.  This cannot be null.
    private DataSource _dataSource;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    // The jndi resource name associated with the datasource object managed
    // by the container.
    private static final String DATASOURCE_JNDI_NAME = "jdbc/tombstone";

    // The logger instance used to emit log statements to the application log.
    private static final Logger LOGGER = new Logger(ApplicationBean.class);

    // The following is needed by the serializable contract.
    private static final long serialVersionUID = 1L;
}
