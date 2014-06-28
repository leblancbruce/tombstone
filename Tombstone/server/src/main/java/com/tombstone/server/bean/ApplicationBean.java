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

@ManagedBean
@ApplicationScoped
public final class ApplicationBean implements Serializable
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public DataSource getDataSource()
    {
        return _dataSource;
    }

    @PostConstruct
    public void initialize()
    {
        LOGGER.info(this, "Attempting to obtain the database datasource "
           + "object using name=\"{}\"", DATASOURCE_JNDI_NAME);

        try
        {
            final Context initialContext = new InitialContext();
            final Context envContext
                = (Context)initialContext.lookup("java:comp/env");

            _dataSource
                = (DataSource)envContext.lookup(DATASOURCE_JNDI_NAME);

            LOGGER.info(this, "Successfully obtained the datasource object.");
        }
        catch(final NamingException e)
        {
            LOGGER.error(this, "Unable to load the database data source using "
                + "name=\"{}\"", DATASOURCE_JNDI_NAME);
        }
    }

    @Override
    public String toString()
    {
        return getClass().getName();
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private DataSource _dataSource;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final String DATASOURCE_JNDI_NAME = "jdbc/tombstone";

    private static final Logger LOGGER = new Logger(ApplicationBean.class);

    private static final long serialVersionUID = 1L;
}
