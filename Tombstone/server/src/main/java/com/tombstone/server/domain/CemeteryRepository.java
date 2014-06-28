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
import com.lebcool.common.logging.Logger;
import com.tombstone.server.domain.exception.LoadException;
import com.tombstone.server.domain.exception.SaveException;

public final class CemeteryRepository extends AutitableRepository
{
    //:: ---------------------------------------------------------------------
    //:: Public Construction

    public CemeteryRepository(
        final DataSource dataSource,
        final ApplicationUser loggedInUser)
    {
        super(dataSource, loggedInUser);
    }

    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public Set<Cemetery> load() throws LoadException
    {
        final StoredProcedure procedure
            = new StoredProcedure("getAllCemeteries");

        final MultipleCemeteriesResultSetProcessor resultSetProcessor
            = new MultipleCemeteriesResultSetProcessor();

        final Executable executable
            = new Executable(procedure, null, resultSetProcessor);

        try
        {
            execute(executable);

            return resultSetProcessor.getCemeteries();
        }
        catch(final SQLException e)
        {
            throw new LoadException("Unable to load all the cemeteries "
                + "from the database.", e);
        }
    }

    public Cemetery loadById(final long id) throws LoadException
    {
        final StoredProcedure procedure
            = new StoredProcedure("getCemeteryById");

        final StoredProcedureArguments arguments
            = new StoredProcedureArguments();
        arguments.add(id);

        final SingleCemeteryResultSetProcessor resultSetProcessor
            = new SingleCemeteryResultSetProcessor();

        final Executable executable
            = new Executable(procedure, arguments, resultSetProcessor);

        try
        {
            execute(executable);

            return resultSetProcessor.getCemetery();
        }
        catch(final SQLException e)
        {
            throw new LoadException("Unable to load the cemetery "
                + "using id=" + id + ".", e);
        }
    }

    public Cemetery loadByName(final String name)
        throws LoadException
    {
        if(name == null)
        {
            throw new IllegalArgumentException("Cannot load a cemetery "
                + "status using a null name.");
        }

        final StoredProcedure procedure
            = new StoredProcedure("getCemeteryByName");

        final StoredProcedureArguments arguments
            = new StoredProcedureArguments();
        arguments.add(name);

        final SingleCemeteryResultSetProcessor resultSetProcessor
            = new SingleCemeteryResultSetProcessor();

        final Executable executable
            = new Executable(procedure, arguments, resultSetProcessor);

        try
        {
            execute(executable);

            return resultSetProcessor.getCemetery();
        }
        catch(final SQLException e)
        {
            throw new LoadException("Unable to load the cemetery "
                + "using name\"=" + name + "\".", e);
        }
    }

    public void save(final Cemetery cemetery) throws SaveException
    {
        final StoredProcedure procedure
            = new StoredProcedure("saveCemetery");

        final StoredProcedureArguments arguments
            = new StoredProcedureArguments();
        arguments.add(getLoggedInUser().getId());
        arguments.add(cemetery.getId());
        arguments.add(cemetery.getVersion());
        arguments.add(cemetery.getName());
        arguments.add(cemetery.getAddress());
        arguments.add(cemetery.getCity());
        arguments.add(cemetery.getEstablishedDay());
        arguments.add(cemetery.getEstablishedMonth());
        arguments.add(cemetery.getEstablishedYear());
        arguments.add(cemetery.getCemeteryStatus().toString());
        arguments.add(cemetery.getCounty().toString());

        final SingleCemeteryResultSetProcessor resultSetProcessor
            = new SingleCemeteryResultSetProcessor(cemetery);

        final Executable executable
            = new Executable(procedure, arguments, resultSetProcessor);

        try
        {
            execute(executable);
        }
        catch(final SQLException e)
        {
            throw new SaveException("Unable to save cemetery="
                + cemetery + " to the database.", e);
        }
    }

    //:: ---------------------------------------------------------------------
    //:: Private Inner Classes

    private static abstract class BaseResultSetProcessor
        implements ResultSetProcessor
    {
        @Override
        public abstract void processResultSet(
                final ResultSet resultSet,
                final int resultSetId)
                    throws SQLException;

        protected void unmarshal(final ResultSet resultSet,
            final Cemetery cemetery) throws SQLException
        {
            LOGGER.debug(this, "Unmarshalling the row results into an "
                + "{} object.", Cemetery.class.getName());

            final Long id = resultSet.getLong(1);
            final long version = resultSet.getLong(2);
            final long createdByApplicationUserId = resultSet.getLong(3);
            final LocalDateTime createdOn
                = convertStringToLocalDateTime(resultSet.getString(4));
            final long updatedByApplicationUserId = resultSet.getLong(5);
            final LocalDateTime updatedOn
                = convertStringToLocalDateTime(resultSet.getString(6));

            updateControlledFields(
                cemetery,
                id,
                version,
                createdOn,
                updatedOn,
                createdByApplicationUserId,
                updatedByApplicationUserId);

            cemetery.setName(resultSet.getNString(7));
            cemetery.setAddress(resultSet.getNString(8));
            cemetery.setCity(resultSet.getNString(9));
            cemetery.setEstablishedDay(resultSet.getShort(10));
            cemetery.setEstablishedMonth(resultSet.getShort(11));
            cemetery.setEstablishedYear(resultSet.getShort(12));
            cemetery.setCemeteryStatus(
                CemeteryStatus.valueOf(resultSet.getString(13)));
            cemetery.setCounty(
                County.valueOf(resultSet.getString(14)));

            return;
        }

        private static final Logger LOGGER
            = new Logger(BaseResultSetProcessor.class);
    }

    private static final class SingleCemeteryResultSetProcessor
        extends BaseResultSetProcessor
    {
        public SingleCemeteryResultSetProcessor()
        {
            this(new Cemetery());
        }

        public SingleCemeteryResultSetProcessor(final Cemetery cemetery)
        {
            _cemetery = cemetery;
        }

        @Override
        public void processResultSet(
            final ResultSet resultSet,
            final int resultSetId)
                throws SQLException
        {
            if(resultSet.next())
            {
                unmarshal(resultSet, _cemetery);

                return;
            }

            _cemetery = null;
        }

        public Cemetery getCemetery()
        {
            return _cemetery;
        }

        @Override
        public String toString()
        {
            return getClass().getName();
        }

        private Cemetery _cemetery;
    }

    private static final class MultipleCemeteriesResultSetProcessor
        extends BaseResultSetProcessor
    {
        @Override
        public void processResultSet(
            final ResultSet resultSet,
            final int resultSetId)
                throws SQLException
        {
            while(resultSet.next())
            {
                final Cemetery cemetery = new Cemetery();

                unmarshal(resultSet, cemetery);

                _cemeteries.add(cemetery);
            }
        }

        public Set<Cemetery> getCemeteries()
        {
            return Collections.unmodifiableSet(_cemeteries);
        }

        @Override
        public String toString()
        {
            return getClass().getName();
        }

        private final Set<Cemetery> _cemeteries = new HashSet<>();
    }
}
