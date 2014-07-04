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
import com.tombstone.server.domain.exception.SaveException;

public final class ApplicationUserRepository extends Repository
{
    //:: ---------------------------------------------------------------------
    //:: Public Construction

    public ApplicationUserRepository(final DataSource dataSource)
    {
        super(dataSource);
    }

    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public List<ApplicationUser> load() throws LoadException
    {
        final StoredProcedure procedure
            = new StoredProcedure("getAllApplicationUsers");

        final MultipleApplicationUsersResultSetProcessor resultSetProcessor
            = new MultipleApplicationUsersResultSetProcessor();

        final Executable executable
            = new Executable(procedure, null, resultSetProcessor);

        try
        {
            execute(executable);

            return resultSetProcessor.getApplicationUsers();
        }
        catch(final SQLException e)
        {
            throw new LoadException("Unable to load all the application "
                + "users from the database.", e);
        }
    }

    public ApplicationUser loadById(final long id) throws LoadException
    {
        final StoredProcedure procedure
            = new StoredProcedure("getApplicationUserById");

        final StoredProcedureArguments arguments
            = new StoredProcedureArguments();
        arguments.add(id);

        final SingleApplicationUserResultSetProcessor resultSetProcessor
            = new SingleApplicationUserResultSetProcessor();

        final Executable executable
            = new Executable(procedure, arguments, resultSetProcessor);

        try
        {
            execute(executable);

            return resultSetProcessor.getApplicationUser();
        }
        catch(final SQLException e)
        {
            throw new LoadException("Unable to load the application user "
                + "using id=" + id + ".", e);
        }
    }

    public ApplicationUser loadByUsername(final String username)
        throws LoadException
    {
        if(username == null)
        {
            throw new IllegalArgumentException("Cannot load an application "
                + "user using a null username.");
        }

        final StoredProcedure procedure
            = new StoredProcedure("getApplicationUserByUsername");

        final StoredProcedureArguments arguments
            = new StoredProcedureArguments();
        arguments.add(username);

        final SingleApplicationUserResultSetProcessor resultSetProcessor
            = new SingleApplicationUserResultSetProcessor();

        final Executable executable
            = new Executable(procedure, arguments, resultSetProcessor);

        try
        {
            execute(executable);

            return resultSetProcessor.getApplicationUser();
        }
        catch(final SQLException e)
        {
            throw new LoadException("Unable to load the application user "
                + "using username=\"" + username + "\".", e);
        }
    }

    public void save(final ApplicationUser applicationUser)
        throws SaveException
    {
        final StoredProcedure procedure
            = new StoredProcedure("saveApplicationUser");

        final StoredProcedureArguments arguments
            = new StoredProcedureArguments();
        arguments.add(applicationUser.getId());
        arguments.add(applicationUser.getVersion());
        arguments.add(applicationUser.getFirstName());
        arguments.add(applicationUser.getLastName());
        arguments.add(applicationUser.getUserName());
        arguments.add(applicationUser.getPassword(), true);
        arguments.add(applicationUser.isActive());

        final SingleApplicationUserResultSetProcessor resultSetProcessor
            = new SingleApplicationUserResultSetProcessor(applicationUser);

        final Executable executable
            = new Executable(procedure, arguments, resultSetProcessor);

        try
        {
            execute(executable);
        }
        catch(final SQLException e)
        {
            throw new SaveException("Unable to save applicationUser="
                + applicationUser + " to the database.", e);
        }
    }

    public void disable(final ApplicationUser applicationUser)
        throws SaveException
    {
        if(applicationUser == null || applicationUser.getId() == null)
        {
            throw new IllegalArgumentException("The supplied application user "
                + "cannot be disabled because it is either null or has not "
                + "been persisted to the database yet.  applicationUser="
                + applicationUser + ".");
        }

        final StoredProcedure procedure
            = new StoredProcedure("disableApplicationUser");

        final StoredProcedureArguments arguments
            = new StoredProcedureArguments();
        arguments.add(applicationUser.getId());

        final SingleApplicationUserResultSetProcessor resultSetProcessor
            = new SingleApplicationUserResultSetProcessor(applicationUser);

        final Executable executable
            = new Executable(procedure, arguments, resultSetProcessor);

        try
        {
            execute(executable);
        }
        catch(final SQLException e)
        {
            throw new SaveException("Unable to disable the applicationUser="
                + applicationUser + ".", e);
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
            final ApplicationUser applicationUser) throws SQLException
        {
            LOGGER.debug(this, "Unmarshalling the row results into an "
                + "{} object.", ApplicationUser.class.getName());

            final Long id = resultSet.getLong(1);
            final long version = resultSet.getLong(2);
            final LocalDateTime createdOn
                = convertStringToLocalDateTime(resultSet.getString(3));
            final LocalDateTime updatedOn
                = convertStringToLocalDateTime(resultSet.getString(4));

            updateControlledFields(
                applicationUser, id, version, createdOn, updatedOn);

            applicationUser.setFirstName(resultSet.getNString(5));
            applicationUser.setLastName(resultSet.getNString(6));
            applicationUser.setUserName(resultSet.getNString(7));
            applicationUser.setPassword(resultSet.getNString(8));
            applicationUser.setActive(resultSet.getBoolean(9));

            return;
        }

        private static final Logger LOGGER
            = new Logger(BaseResultSetProcessor.class);
    }

    private static final class SingleApplicationUserResultSetProcessor
        extends BaseResultSetProcessor
    {
        public SingleApplicationUserResultSetProcessor()
        {
            this(new ApplicationUser());
        }

        public SingleApplicationUserResultSetProcessor(
            final ApplicationUser applicationUser)
        {
            _applicationUser = applicationUser;
        }

        @Override
        public void processResultSet(
            final ResultSet resultSet,
            final int resultSetId)
                throws SQLException
        {
            if(resultSet.next())
            {
                unmarshal(resultSet, _applicationUser);

                return;
            }

            _applicationUser = null;
        }

        public ApplicationUser getApplicationUser()
        {
            return _applicationUser;
        }

        @Override
        public String toString()
        {
            return getClass().getName();
        }

        private ApplicationUser _applicationUser;
    }

    private static final class MultipleApplicationUsersResultSetProcessor
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
                final ApplicationUser applicationUser
                    = new ApplicationUser();
                unmarshal(resultSet, applicationUser);

                _applicationUsers.add(applicationUser);
            }
        }

        public List<ApplicationUser> getApplicationUsers()
        {
            return Collections.unmodifiableList(_applicationUsers);
        }

        @Override
        public String toString()
        {
            return getClass().getName();
        }

        private final List<ApplicationUser> _applicationUsers
            = new ArrayList<>();
    }
}
