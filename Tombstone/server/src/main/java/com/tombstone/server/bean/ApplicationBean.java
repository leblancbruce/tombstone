package com.tombstone.server.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.lebcool.common.logging.Logger;
import com.tombstone.server.database.DataSourceLoader;

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
        LOGGER.info(this, "Attempting to initialize this bean.");

        try
        {
            _dataSource = DataSourceLoader.load();

            LOGGER.info(this, "Successfully initialized this bean.");
        }
        catch(final NamingException e)
        {
            // Uh-oh. We can't load the data source object.  Throw an
            // exception.
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
    // The logger instance used to emit log statements to the application log.
    private static final Logger LOGGER = new Logger(ApplicationBean.class);

    // The following is needed by the serializable contract.
    private static final long serialVersionUID = 1L;
}
