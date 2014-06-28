package com.lebcool.common.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lebcool.common.database.procedure.ResultSetProcessor;
import com.lebcool.common.database.procedure.StoredProcedure;
import com.lebcool.common.database.procedure.StoredProcedureArguments;
import com.lebcool.common.logging.Logger;

public abstract class Repository
{
    //:: ---------------------------------------------------------------------
    //:: Public Construction

    /**
     * Instantiates an instance of a {@link Repository} object using the
     * supplied arguments.
     *
     * @param dataSource
     *        The {@link DataSource} object used to obtain {@link Connection}
     *        objects from.  This cannot be null.
     */
    public Repository(final DataSource dataSource)
    {
        if(dataSource == null)
        {
            throw new IllegalArgumentException("The supplied datasource "
                 + "cannot be null.");
        }

        _dataSource = dataSource;
    }

    //:: ---------------------------------------------------------------------
    //:: Public Interface

    /**
     * @return A string representation of this class used for logging purposes.
     */
    @Override
    public String toString()
    {
        return getClass().getName();
    }

    //:: ---------------------------------------------------------------------
    //:: Protected Interface

    /**
     * Executes a single stored procedure.
     *
     * @param executable
     *        The {@link Executable} wrapper object containing the stored
     *        procedure to execute along with its arguments and result set
     *        processor.  This cannot be null.
     *
     * @throws SQLException
     *         Thrown if there's an issue executing the stored procedure.  A
     *         database rollback will automatically occur if this exception is
     *         thrown.
     */
    protected void execute(final Executable executable) throws SQLException
    {
        if(executable == null)
        {
            throw new IllegalArgumentException("The supplied executable "
                 + "cannot be null.");
        }

        final List<Executable> executables = new ArrayList<>();
        executables.add(executable);

        execute(executables);
    }

    /**
     * Executes the supplied stored procedures in FIFO order within the same
     * database transaction.
     *
     * @param executables
     *        The list of {@link Executable} wrapper objects containing the
     *        stored procedure objects to execute along with their arguments
     *        and result set processors.  This cannot be null nor empty.
     *
     * @throws SQLException
     *         Thrown if there's an issue executing a stored procedure within
     *         the list.  A database rollback will automatically occur if this
     *         exception is thrown.
     */
    protected void execute(final List<Executable> executables)
        throws SQLException
    {
        if(executables == null)
        {
            throw new IllegalArgumentException("The supplied executable list "
                 + "cannot be null.");
        }

        // If there aren't any executables to execute, then simply return.
        // This prevents us from obtaining a jdbc connection and performing
        // some no-ops using that connection.
        if(executables.isEmpty())
        {
            // The calling code is responsible for calling this execute method.
            // If there are no stored executables, then we can infer a logic
            // mishap.  Log a warning and return.
            LOGGER.warn(this, "There are no stored procedures to execute.  "
                + "There is nothing to do.  Returning.");

            return;
        }

        // Obtain a database connection from the data source.  All explicit
        // connection operations will be confined to this method and class.
        final Connection connection = _dataSource.getConnection();

        try
        {
            // Ensure that the auto commit feature used by some databases is
            // turned off.  This object is responsible for committing to the
            // database.
            connection.setAutoCommit(false);

            // Iterate over each procedure wrapper.  Since we use a list,
            // the execution order will be maintained (i.e. FIFO).
            for(final Executable currentExecutable : executables)
            {
                final StoredProcedure procedure
                    = currentExecutable.getProcedure();

                final StoredProcedureArguments arguments
                    = currentExecutable.getArguments();

                final ResultSetProcessor resultSetProcessor
                    = currentExecutable.getResultSetProcessor();

                try
                {
                    procedure.execute(
                        connection, arguments, resultSetProcessor);
                }
                catch(final SQLException ee)
                {
                    LOGGER.error(this, "The stored procedure failed to execute "
                        + "properly.  storedProcedure={}, "
                        + "storedProcedureArguments={},"
                        + " and resultSetProcessore={}.",
                        procedure, arguments, resultSetProcessor);

                    throw ee;
                }
            }

            // Commit any outstanding dml (data manipulation language) changes
            // to the database.
            connection.commit();
        }
        catch(final SQLException e)
        {
            // On exception, we want to perform a rollback operation to revert
            // any dml (data manipulation language) statements executed.  If
            // the stored procedure contains any ddl (data definition
            // language) statements, then a rollback will likely have no
            // effect if the statement was successfully executed.  If the
            // stored procedure performs any commits, then this rollback will
            // not affect those commit operations.
            if(connection != null)
            {
                LOGGER.info(this, "Attempting a database rollback operation "
                    + "due to the sql exception encountered.");

                connection.rollback();

                LOGGER.info(this, "Successfully rolled back any outstanding "
                    + "data modification operations.");
            }

            // We've taken care of the rollback, but let the calling code deal
            // with the exception as well.
            throw e;
        }
        finally
        {
            // Whether or not the stored procedure execution run was successful
            // or not, we need to ensure we clean up the JDBC resources used
            // (if any).

            if(connection != null)
            {
                // If the connection is obtained from a data source that pools
                // connections, closing it will likely return it to the pool.
                connection.close();
            }
        }
    }

    //------------------------------------------------------------------------
    // :: Protected Nested Classes

    /**
     * <p>
     * A wrapper class used to group together a {@link StoredProcedure}
     * object and its {@link StoredProcedureArguments} object and its
     * {@link ResultSetProcessor} object.
     * </p>
     *
     * <p>
     * This class is immutable.
     * </p>
     */
    protected final class Executable
    {
        /**
         * Constructs an instance of an {@link Executable} object using the
         * supplied arguments.
         *
         * @param procedure
         *        The {@link StoredProcedure} object to execute.  This cannot
         *        be null.
         * @param arguments
         *        The {@link StoredProcedureArguments} object to supply to the
         *        stored procedure.  This can be null.
         * @param resultSetProcessor
         *        The {@link ResultSetProcessor} object used to process the
         *        result sets returned by the stored procedure.  This can
         *        be null.
         */
        public Executable(
            final StoredProcedure procedure,
            final StoredProcedureArguments arguments,
            final ResultSetProcessor resultSetProcessor)
        {
            if(procedure == null)
            {
                throw new IllegalArgumentException("The procedure cannot be "
                    + "null.");
            }

            _procedure = procedure;
            _arguments = arguments;
            _resultSetProcessor = resultSetProcessor;
        }

        /**
         * @return A string representation of this object used for logging
         *         purposes.
         */
        @Override
        public String toString()
        {
            return getClass().getName()
                + "[procedure=" + _procedure
                + ", arguments=" + _arguments
                + ", resultSetProcessore=" + _resultSetProcessor
                + "]";
        }

        /**
         * @return The {@link StoredProcedure} object to execute.  This cannot
         *         be null.
         */
        StoredProcedure getProcedure()
        {
            return _procedure;
        }

        /**
         * @return The {@link StoredProcedureArguments} object to supply to
         *         the stored procedure.  This can be null.
         */
        StoredProcedureArguments getArguments()
        {
            return _arguments;
        }

        /**
         * @return The {@link ResultSetProcessor} object used to process the
         *         result sets returned by the stored procedure.  This can
         *         be null.
         */
        ResultSetProcessor getResultSetProcessor()
        {
            return _resultSetProcessor;
        }

        // Keeps a reference to the stored procedure.  This cannot be null.
        private final StoredProcedure _procedure;

        // Keeps a reference to the arguments for the above stored procedure.
        // This can be null.
        private final StoredProcedureArguments _arguments;

        // Keeps a reference to the results handler that will process the
        // stored procedure results.  This can be null.
        private final ResultSetProcessor _resultSetProcessor;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    // The data source object that manages the database connections.  This is
    // needed to drive the underlying stored procedures.  This cannot be
    // null.
    private final DataSource _dataSource;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    // The logger instance used by this class.  This cannot be null.
    private static final Logger LOGGER = new Logger(Repository.class);
}