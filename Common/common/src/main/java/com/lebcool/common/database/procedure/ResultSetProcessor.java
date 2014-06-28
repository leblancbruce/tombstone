package com.lebcool.common.database.procedure;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * The interface used by the {@link StoredProcedure} object to allow the
 * custom processing of {@link ResultSet} objects returned by an executed
 * stored procedure.
 * </p>
 *
 * <p>
 * A stored procedure can conceivably return no result set, one result set, or
 * multiple result set objects upon execution.
 * The {@link #processResultSet(ResultSet, int)} method that the implementing
 * class must define can be called multiple times by the
 * {@link StoredProcedure} object IFF there are multiple
 * result sets to process.  Each time the method is called, the resultSetId
 * can be used to determine which result from the stored procedure is being
 * handed over for processing.  The identifier will begin at 1 and increment
 * by 1 for each result set returned by the stored procedure.  It is up to
 * the implementor of this interface to properly handle multiple result sets
 * by knowing how many result sets are returned and in what order they are
 * returned by the stored procedure.
 * </p>
 *
 * <p>
 * The implementors of this interface do not need to worry about closing the
 * result sets that are passed on to it for processing.  The
 * {@link StoredProcedure} object will handle the closing of each result set
 * once the {@link #processResultSet(ResultSet, int)} returns.
 * </p>
 *
 * @see StoredProcedure
 */
public interface ResultSetProcessor
{
    /**
     * Processes each result set object returned by executing a
     * {@link StoredProcedure} object.  This method is called once per
     * result set object returned.
     *
     * @param resultSet
     *        The {@link ResultSet} object to process.  This cannot be null.
     *        The closing of the result set will be handled by the
     *        {@link StoredProcedure} object.
     * @param resultSetId
     *        An identifier to denote what result set object is being
     *        processed.  This identifier will begin at 1 for the first result
     *        set object returned by the stored procedure and increment by 1
     *        for each subsequent result set object returned.
     *
     * @throws SQLException
     *         Thrown if there's an error processing the result set.  If thrown
     *         the {@link StoredProcedure} object will take action to
     *         automatically roll back the changes involved within the current
     *         database transaction.
     */
    void processResultSet(final ResultSet resultSet, final int resultSetId)
        throws SQLException;
}
