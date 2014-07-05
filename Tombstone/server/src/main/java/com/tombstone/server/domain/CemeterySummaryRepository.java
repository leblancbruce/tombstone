package com.tombstone.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public List<CemeterySummary> load(final int page, final int count)
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

            while(resultSet.next())
            {
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

                Long thumbnailImageId = resultSet.getLong(9);

                thumbnailImageId = resultSet.wasNull()
                    ? null : thumbnailImageId;

                final CemeterySummary cemeterySummary = new CemeterySummary(
                    cemeteryId,
                    name,
                    establishedYear,
                    cemeteryStatus,
                    numberOfPlots,
                    numberOfDeceased,
                    lastUpdatedByDateTime,
                    lastUpdatedByUserName,
                    thumbnailImageId);

                _cemeterySummaries.add(cemeterySummary);
            }
        }

        public List<CemeterySummary> getCemeterySummaries()
        {
            return Collections.unmodifiableList(_cemeterySummaries);
        }

        @Override
        public String toString()
        {
            return getClass().getName();
        }

        private final List<CemeterySummary> _cemeterySummaries
            = new ArrayList<>();

        private static final Logger LOGGER
            = new Logger(CemeterySummaryResultSetProcessor.class);
    }
}
