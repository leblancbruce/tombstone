package com.tombstone.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.joda.time.LocalDateTime;

import com.lebcool.common.database.procedure.ResultSetProcessor;
import com.lebcool.common.database.procedure.StoredProcedure;
import com.lebcool.common.database.procedure.StoredProcedureArguments;
import com.lebcool.common.domain.Repository;
import com.lebcool.common.logging.Logger;
import com.tombstone.server.domain.exception.LoadException;

public final class CemeterySummaryRepository extends Repository
{
    public CemeterySummaryRepository(final DataSource dataSource)
    {
        super(dataSource);
    }

    public Set<CemeterySummary> load(final int page, final int count)
        throws LoadException
    {
        final StoredProcedure procedure
            = new StoredProcedure("getCemeterySummaries");

        final StoredProcedureArguments arguments
            = new StoredProcedureArguments();
        arguments.add(page);
        arguments.add(count);

        final CemeterySummaryResultSetProcessor resultSetProcessor
            = new CemeterySummaryResultSetProcessor();

        final Executable executable
            = new Executable(procedure, arguments, resultSetProcessor);

        try
        {
            execute(executable);

            return resultSetProcessor.getCemeterySummaries();
        }
        catch(final SQLException e)
        {
            throw new LoadException("Unable to load the cemetery summaries.",
                e);
        }
    }

    private static final class CemeterySummaryResultSetProcessor
        implements ResultSetProcessor
    {
        @Override
        public void processResultSet(
            final ResultSet resultSet,
            final int resultSetId)
                throws SQLException
        {
            LOGGER.debug(this, "Unmarshalling the row results into an "
                    + "{} object.", CemeterySummary.class.getName());

            final long cemeteryId = resultSet.getLong(1);
            final String name = resultSet.getString(2);
            final Short establishedYear = resultSet.getShort(3);
            final CemeteryStatus cemeteryStatus
                = CemeteryStatus.valueOf(resultSet.getString(4));
            final long numberOfPlots = resultSet.getLong(5);
            final long numberOfDeceased = resultSet.getLong(6);
            final LocalDateTime lastUpdatedByDateTime
                = convertStringToLocalDateTime(resultSet.getString(7));
            final String lastUpdatedByUserName = resultSet.getString(8);

            final CemeterySummary cemeterySummary = new CemeterySummary(
                cemeteryId,
                name,
                establishedYear,
                cemeteryStatus,
                numberOfPlots,
                numberOfDeceased,
                lastUpdatedByDateTime,
                lastUpdatedByUserName);

            _cemeterySummaries.add(cemeterySummary);
        }

        public Set<CemeterySummary> getCemeterySummaries()
        {
            return Collections.unmodifiableSet(_cemeterySummaries);
        }

        private final Set<CemeterySummary> _cemeterySummaries
            = new HashSet<>();

        private static final Logger LOGGER
            = new Logger(CemeterySummaryResultSetProcessor.class);
    }
}
