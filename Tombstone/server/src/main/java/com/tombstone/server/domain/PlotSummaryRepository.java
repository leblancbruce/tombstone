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

public final class PlotSummaryRepository extends Repository
{
    public PlotSummaryRepository(final DataSource dataSource)
    {
        super(dataSource);
    }

    public List<PlotSummary> load(
        final long cemeteryId,
        final int page,
        final int count)
            throws LoadException
    {
        final StoredProcedure procedure
            = new StoredProcedure("getPlotSummaries");

        final StoredProcedureArguments arguments
            = new StoredProcedureArguments();
        arguments.add(cemeteryId);
        arguments.add(page);
        arguments.add(count);

        final PlotSummaryResultSetProcessor resultSetProcessor
            = new PlotSummaryResultSetProcessor();

        final Executable executable
            = new Executable(procedure, arguments, resultSetProcessor);

        try
        {
            execute(executable);

            return resultSetProcessor.getPlotSummaries();
        }
        catch(final SQLException e)
        {
            throw new LoadException("Unable to load the plot summaries for "
                + " the cemetery with id=" + cemeteryId + ".",
                e);
        }
    }

    private static final class PlotSummaryResultSetProcessor
        implements ResultSetProcessor
    {
        @Override
        public void processResultSet(
            final ResultSet resultSet,
            final int resultSetId)
                throws SQLException
        {
            LOGGER.debug(this, "Unmarshalling the row results into an "
                    + "{} object.", PlotSummary.class.getName());

            while(resultSet.next())
            {
                final long plotId = resultSet.getLong(1);
                final String name = resultSet.getString(2);
                final PlotType plotType
                    = PlotType.valueOf(resultSet.getString(3));
                final long numberOfDeceased = resultSet.getLong(4);
                final LocalDateTime lastUpdatedByDateTime
                    = convertStringToLocalDateTime(resultSet.getString(5));
                final String lastUpdatedByUserName = resultSet.getString(6);

                Long thumbnailImageId = resultSet.getLong(7);

                thumbnailImageId = resultSet.wasNull()
                    ? null : thumbnailImageId;

                final PlotSummary plotSummary = new PlotSummary(
                    plotId,
                    name,
                    plotType,
                    numberOfDeceased,
                    lastUpdatedByDateTime,
                    lastUpdatedByUserName,
                    thumbnailImageId);

                _plotSummaries.add(plotSummary);
            }
        }

        public List<PlotSummary> getPlotSummaries()
        {
            return Collections.unmodifiableList(_plotSummaries);
        }

        @Override
        public String toString()
        {
            return getClass().getName();
        }

        private final List<PlotSummary> _plotSummaries = new ArrayList<>();

        private static final Logger LOGGER
            = new Logger(PlotSummaryResultSetProcessor.class);
    }
}
