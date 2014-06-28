package com.tombstone.server.repository;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.lebcool.common.repository.Repository;
import com.tombstone.server.repository.exception.RepositoryInstantiationException;

abstract class ServerRepository extends Repository
{
    //:: ---------------------------------------------------------------------
    //:: Package-Private Construction

    ServerRepository()
    {
        super(ServerRepository.getDataSource());
    }

    //:: ---------------------------------------------------------------------
    //:: Package-Private Interface

    static LocalDateTime convertStringToLocalDateTime(final String s)
    {
        return LocalDateTime.parse(s, FORMATTER);
    }

    //:: ---------------------------------------------------------------------
    //:: Private Interface

    private static final DataSource getDataSource()
    {
        try
        {
            final Context initialContext = new InitialContext();
            final Context envContext
                = (Context)initialContext.lookup("java:comp/env");

            return (DataSource)envContext.lookup(DATASOURCE_JNDI_NAME);
        }
        catch(final NamingException e)
        {
            throw new RepositoryInstantiationException("", e);
        }
    }

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final String DATASOURCE_JNDI_NAME = "jdbc/tombstone";

    private static final DateTimeFormatter FORMATTER
        = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS Z");
}
