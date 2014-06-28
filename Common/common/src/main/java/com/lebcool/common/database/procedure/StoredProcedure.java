package com.lebcool.common.database.procedure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lebcool.common.database.procedure.StoredProcedureArguments.StoredProcedureArgument;
import com.lebcool.common.domain.Repository;
import com.lebcool.common.logging.Logger;

/**
 * <p>
 * This class represents a database stored procedure.
 * </p>
 *
 * <p>
 * This class will execute a database stored procedure using the supplied
 * JDBC connection, stored procedure arguments, and result set processor.
 *
 * <p>
 * This class does not perform the jdbc connection and jdbc transaction
 * management.  The {@link Repository} class is responsible for the
 * {@link DataSource} management and the transaction management.
 * </p>
 *
 * @see {@link #execute(Connection, StoredProcedureArguments, ResultSetProcessor)}
 * @see {@link StoredProcedureArguments}
 * @see {@link ResultSetProcessor}
 * @see {@link Repository}
 */
public final class StoredProcedure
{
    //------------------------------------------------------------------------
    // :: Public Construction

    /**
     * Constructs an instance of a {@link StoredProcedure} object using the
     * supplied parameter.
     *
     * @param procedureName
     *        The name of the database stored procedure to execute.
     *        This cannot be null.
     */
    public StoredProcedure(final String procedureName)
    {
        _procedureName = procedureName;
    }

    //------------------------------------------------------------------------
    // :: Public Interface

    /**
     * @return A string representation of this class for logging and
     *         debugging purposes.
     */
    @Override
    public String toString()
    {
        return getClass().getName()
            + "[procedureName=\"" + _procedureName + "\"]";
    }

    /**
     * Executes this stored procedure using the supplied parameters.
     *
     * @param connection
     *        The JDBC {@link Connection} to execute the stored procedure on.
     *        This cannot be null.  This method will simply use this object to
     *        create a {@link PreparedStatement} object.  All other connection
     *        operations are expected to be handled outside this method.
     * @param arguments
     *        The arguments to supply to the stored procedure, as a
     *        {@link StoredProcedureArguments} object.  This can be null IFF
     *        the actual database stored procedure doesn't expect any
     *        arguments.
     * @param resultSetProcessor
     *        The object that will handle any result sets returned by
     *        executing the stored procedure, as a {@link resultSetProcessor}
     *        object.
     *
     * @throws SQLException
     *         Thrown if there's an issue executing this stored procedure.
     */
    public final void execute(
            final Connection connection,
            final StoredProcedureArguments arguments,
            final ResultSetProcessor resultSetProcessor)
                    throws SQLException
    {
        LOGGER.debug(this, "Attempting to execute this stored procedure "
            + "using arguments={} and resultSetProcessor={}.",
            arguments, resultSetProcessor);

        final int numberOfArguments = arguments != null ? arguments.size() : 0;

        // Create the sql string used to create the prepared statement that
        // will be executed.
        final String sql = createPreparedStatementString(numberOfArguments);

        PreparedStatement statement = null;

        try
        {
            // Create the prepared statement.
            statement = connection.prepareStatement(sql);

            // The arguments will be null if the stored procedure being
            // executed does not take any arguments.  If null, then there is
            // no need to add any arguments to the stored procedure.
            if(arguments != null)
            {
                // Iterate over the supplied stored procedure arguments and add
                // each on, in order, to the prepared statement.
                for(final StoredProcedureArgument currentArgument : arguments)
                {
                    statement.setObject(
                        currentArgument.getIndex(),
                        currentArgument.getValue(),
                        currentArgument.getType());
                }
            }

            LOGGER.debug(this, "Executing the jdbc prepared statement using "
                + "sql={}.", sql);

            // Execute the stored procedure
            if(statement.execute())
            {
                // There are result sets to process as the above executed
                // statement returned true.  Proceed to process the result
                // sets.

                // If a result set processor object is provided, then proceed
                // to process the returned result sets, else log a warning
                // to indicate that the stored procedure returned some result
                // sets, but they were essentially ignored.
                if(resultSetProcessor != null)
                {
                    // Start the result id at 1.  This id let's the result set
                    // processor know which result set to process.  This will
                    // increment by 1 for each result set returned.
                    int resultSetId = 1;

                    // To obtain the first result set, you need to call
                    // getResultSet() right off the bat prior to iterating
                    // over the remainder of the result sets returned.  This
                    // action is documented within the jdbc documentation.
                    final ResultSet firstResultSet = statement.getResultSet();

                    // Tell the result set processor to process the first
                    // result set.
                    resultSetProcessor.processResultSet(
                         firstResultSet, resultSetId);

                    // Once the above method returns from processing the first
                    // result set, we attempt to close the first result set.
                    // If the result set processor already closed it, the
                    // following close action becomes a no-op.
                    firstResultSet.close();

                    // Iterate over the remaining result sets and call the
                    // result processor for each one.
                    while(statement.getMoreResults())
                    {
                        // Get the next result set that was returned.
                        final ResultSet currentResultSet
                            = statement.getResultSet();

                        // Tell the result set processor to process it.
                        resultSetProcessor.processResultSet(
                            currentResultSet, resultSetId++);

                        // Once the above method returns from processing the
                        // result set, we attempt to close the result set.
                        // If the result set processor already closed it, the
                        // following close action becomes a no-op.
                        currentResultSet.close();
                    }
                }
                else
                {
                    // Uh-oh, we have a situation where a stored procedure was
                    // called and it returned one or more result sets, but
                    // the calling code did not provide a means to process
                    // the returned result sets.  This may or may not have
                    // been intentional.  Regardless, we're going to log a
                    // warning.
                    LOGGER.warn(this, "This stored procedure returned "
                        + "one or more result sets.  However, the calling "
                        + "code provided a null result set processor "
                        + "object.  The result sets returned will be "
                        + "ignored.");
                }
            }
            else
            {
                LOGGER.debug(this, "This stored procedure only returned an "
                    + "update count when it executed.  Update count={}.",
                    statement.getUpdateCount());
            }

            LOGGER.debug(this, "Successfully executed this stored "
                + "procedure using arguments={} and resultSetProcessor={}",
                arguments, resultSetProcessor);

            // We've successfully executed the stored procedure without
            // error.  Simply return.
            return;
        }
        catch(final SQLException e)
        {
            // This catch block serves only to log the exception and then
            // forward it to the calling code.

            // Log the error.
            LOGGER.error(this, "An error occurred while executing this "
                + "stored procedure using arguments=" + arguments
                + " and resultSetProcessor=" + resultSetProcessor
                + ".", e);

            // Forward the exception to the calling code to handle.
            throw e;
        }
        finally
        {
            // Whether the stored procedure execution succeeded or failed, we
            // want to clean up the JDBC resources used properly.

            if(statement != null)
            {
                // The following closes the prepare statement and any result
                // sets objects that are open.
                statement.close();
            }
        }
    }

    //------------------------------------------------------------------------
    // :: Private Interface

    /**
     * Creates the sql string used by the prepared statement to execute this
     * stored procedure.
     *
     * @param numberOfArguments
     *        The number of arguments the stored procedure expects.  This can
     *        be 0.
     *
     * @return A string containing the SQL needed to execute the actual
     *         database stored procedure.  This cannot be null.
     */
    private String createPreparedStatementString(final int numberOfArguments)
    {
        String sql = "exec " + _procedureName
            + (numberOfArguments > 0 ? " " : "");

        // A prepared statement simply needs to know how may arguments to
        // expect.  For example, if a stored procedure has 3 arguments, then
        // the sql string to execute will look like the following.
        //
        // exec procedureName ?,?,?
        //
        for(int i = 0; i < numberOfArguments; i++)
        {
            sql += "?";

            sql+= ((i < numberOfArguments - 1) ? ", " : "");
        }

        return sql;
    }

    //------------------------------------------------------------------------
    // :: Private Data Members

    // The name of the procedure to execute.  This cannot be null.
    private final String _procedureName;

    //------------------------------------------------------------------------
    // :: Private Constants

    // The logger object used to emit log messages.
    private static final Logger LOGGER = new Logger(StoredProcedure.class);
}
