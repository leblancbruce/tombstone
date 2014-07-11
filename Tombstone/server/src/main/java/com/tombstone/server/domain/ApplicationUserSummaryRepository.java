package com.tombstone.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import com.lebcool.common.database.procedure.ResultSetProcessor;
import com.lebcool.common.database.procedure.StoredProcedure;
import com.lebcool.common.database.procedure.StoredProcedureArguments;
import com.lebcool.common.domain.Repository;
import com.lebcool.common.logging.Logger;
import com.tombstone.server.domain.exception.LoadException;

public final class ApplicationUserSummaryRepository extends Repository
{
    public ApplicationUserSummaryRepository(final DataSource dataSource)
    {
        super(dataSource);
    }

    public List<ApplicationUserSummary> load(final int page, final int count)
        throws LoadException
    {
        final StoredProcedure procedure
            = new StoredProcedure("getApplicationUserSummaries");

        final StoredProcedureArguments arguments
            = new StoredProcedureArguments();
        arguments.add(page);
        arguments.add(count);

        final ApplicationUserSummaryResultSetProcessor resultSetProcessor
            = new ApplicationUserSummaryResultSetProcessor();

        final Executable executable
            = new Executable(procedure, arguments, resultSetProcessor);

        try
        {
            execute(executable);

            return resultSetProcessor.getApplicationUserSummaries();
        }
        catch(final SQLException e)
        {
            throw new LoadException("Unable to load the application user "
                + " summaries.", e);
        }
    }

    private static final class ApplicationUserSummaryResultSetProcessor
        implements ResultSetProcessor
    {
        @Override
        public void processResultSet(
            final ResultSet resultSet,
            final int resultSetId)
                throws SQLException
        {
            LOGGER.debug(this, "Unmarshalling the row results into an "
                + "{} object.", ApplicationUserSummary.class.getName());

            while(resultSet.next())
            {
                final long applicationUserId = resultSet.getLong(1);
                final String firstName = resultSet.getString(2);
                final String lastName = resultSet.getString(3);

                Long thumbnailImageId = resultSet.getLong(4);

                thumbnailImageId = resultSet.wasNull()
                    ? null : thumbnailImageId;

                final ApplicationUserSummary applicationUserSummary
                    = new ApplicationUserSummary(
                        applicationUserId,
                        firstName,
                        lastName,
                        thumbnailImageId);

                _applicationUserSummaries.add(applicationUserSummary);
            }
        }

        public List<ApplicationUserSummary> getApplicationUserSummaries()
        {
            return Collections.unmodifiableList(_applicationUserSummaries);
        }

        private final List<ApplicationUserSummary> _applicationUserSummaries
            = new ArrayList<>();

        private static final Logger LOGGER
            = new Logger(ApplicationUserSummaryResultSetProcessor.class);
    }
}
