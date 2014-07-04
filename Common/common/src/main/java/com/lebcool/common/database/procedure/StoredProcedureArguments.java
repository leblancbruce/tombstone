package com.lebcool.common.database.procedure;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * This class represents the arguments supplied to a stored procedure.
 * </p>
 *
 * <p>
 * This class maintains a FIFO order for the arguments added.  This class
 * is iterable.
 * </p>
 */
public final class StoredProcedureArguments
    implements Iterable<StoredProcedureArguments.StoredProcedureArgument>
{
    //------------------------------------------------------------------------
    // :: Public Interface

    /**
     * @return An iterator object to iterate over the stored procedure
     *         arguments.  This cannot be null.
     */
    @Override
    public Iterator<StoredProcedureArgument> iterator()
    {
        return _arguments.iterator();
    }

    /**
     * Adds a boolean argument.
     *
     * @param argument
     *        The {@link Boolean} value to add.  This can be null.
     */
    public void add(final Boolean argument)
    {
        add(argument, false);
    }

    /**
     * Adds a boolean argument.
     *
     * @param argument
     *        The {@link Boolean} value to add.  This can be null.
     * @param sensitive
     *        A flag used to mask this value when it used within
     *        application logging event.  True, if the value should be
     *        masked in the logs.
     */
    public void add(final Boolean argument, final boolean sensitive)
    {
        _arguments.add(new StoredProcedureArgument(
            nextIndex(), Types.BOOLEAN, argument, sensitive));
    }

    /**
     * Adds a char argument.
     *
     * @param argument
     *        The {@link Character} value to add.  This can be null.
     */
    public void add(final Character argument)
    {
        add(argument, false);
    }

    /**
     * Adds a char argument.
     *
     * @param argument
     *        The {@link Character} value to add.  This can be null.
     * @param sensitive
     *        A flag used to mask this value when it used within
     *        application logging event.  True, if the value should be
     *        masked in the logs.
     */
    public void add(final Character argument, final boolean sensitive)
    {
        _arguments.add(new StoredProcedureArgument(
            nextIndex(), Types.CHAR, argument, sensitive));
    }

    /**
     * Adds a short argument.
     *
     * @param argument
     *        The {@link Short} value to add.  This can be null.
     */
    public void add(final Short argument)
    {
        add(argument, false);
    }

    /**
     * Adds a short argument.
     *
     * @param argument
     *        The {@link Short} value to add.  This can be null.
     * @param sensitive
     *        A flag used to mask this value when it used within
     *        application logging event.  True, if the value should be
     *        masked in the logs.
     */
    public void add(final Short argument, final boolean sensitive)
    {
        _arguments.add(new StoredProcedureArgument(
            nextIndex(), Types.SMALLINT, argument, sensitive));
    }

    /**
     * Adds a long argument.
     *
     * @param argument
     *        The {@link Long} value to add.  This can be null.
     */
    public void add(final Long argument)
    {
        add(argument, false);
    }

    /**
     * Adds a long argument.
     *
     * @param argument
     *        The {@link Long} value to add.  This can be null.
     * @param sensitive
     *        A flag used to mask this value when it used within
     *        application logging event.  True, if the value should be
     *        masked in the logs.
     */
    public void add(final Long argument, final boolean sensitive)
    {
        _arguments.add(new StoredProcedureArgument(
            nextIndex(), Types.BIGINT, argument, sensitive));
    }

    /**
     * Adds a int argument.
     *
     * @param argument
     *        The {@link Integer} value to add.  This can be null.
     */
    public void add(final Integer argument)
    {
        add(argument, false);
    }

    /**
     * Adds a long argument.
     *
     * @param argument
     *        The {@link Integer} value to add.  This can be null.
     * @param sensitive
     *        A flag used to mask this value when it used within
     *        application logging event.  True, if the value should be
     *        masked in the logs.
     */
    public void add(final Integer argument, final boolean sensitive)
    {
        _arguments.add(new StoredProcedureArgument(
            nextIndex(), Types.INTEGER, argument, sensitive));
    }

    /**
     * Adds a string argument.
     *
     * @param argument
     *        The {@link String} value to add.  This can be null.
     */
    public void add(final String argument)
    {
        add(argument, false);
    }

    /**
     * Adds a string argument.
     *
     * @param argument
     *        The {@link String} value to add.  This can be null.
     * @param sensitive
     *        A flag used to mask this value when it used within
     *        application logging event.  True, if the value should be
     *        masked in the logs.
     */
    public void add(final String argument, final boolean sensitive)
    {
        _arguments.add(new StoredProcedureArgument(
            nextIndex(), Types.NVARCHAR, argument, sensitive));
    }

    /**
     * Adds a byte array argument.
     *
     * @param argument
     *        The byte array value to add.  This can be null.
     */
    public void add(final byte[] argument)
    {
        _arguments.add(new StoredProcedureArgument(
            nextIndex(), Types.VARBINARY, argument, true));
    }

    /**
     * @return A string representation of this class for logging and
     *         debugging purposes.
     */
    @Override
    public String toString()
    {
        return getClass().getName() + "[arguments=" + _arguments + "]";
    }

    //------------------------------------------------------------------------
    // :: Package Private Interface

    /**
     * @return The number of arguments.
     */
    int size()
    {
        return _arguments.size();
    }

    /**
     * @return True, if there are no arguments.  False, otherwise.
     */
    boolean isEmpty()
    {
        return _arguments.isEmpty();
    }

    //------------------------------------------------------------------------
    // :: Package Private Nested Classes

    /**
     * <p>
     * This class represents a single stored procedure argument.
     * </p>
     *
     * <p>
     * Instances of this class are immutable and are not visible outside this
     * package.
     * </p>
     */
    static final class StoredProcedureArgument
    {
        /**
         * @return The index of where this argument appears in the argument
         *         list.
         */
        public int getIndex()
        {
            return _index;
        }

        /**
         * @return The JDBC type of the argument (as represented within
         *         {@link Types}).
         */
        public int getType()
        {
            return _type;
        }

        /**
         * @return The value of the argument.  This can be null.
         */
        public Object getValue()
        {
            return _value;
        }

        /**
         * @return A string representation of this class for logging and
         *         debugging purposes.
         */
        @Override
        public String toString()
        {
            final boolean valueNeedsEnclosingQuotes
                = (_value != null ? (_value.getClass() == String.class
                    || _value.getClass() == Character.class) : false);

            return getClass().getName()
                + "[index=" + _index
                + " type=" + _type
                + " value="
                + (valueNeedsEnclosingQuotes ? "\"" : "")
                + (_sensitive ? MASK : _value)
                + (valueNeedsEnclosingQuotes ? "\"" : "")
                + "]";
        }

        /**
         * Constructs an instance of a {@link StoredProcedureArgument} object.
         *
         * @param index
         *        The index of the argument within the argument list.
         * @param type
         *        The JDBC type of this argument(as represented within
         *         {@link Types}).
         * @param value
         *        The argument value.  This can be null.
         * @param sensitive
         *        True, if the value should NOT appear in the clear within
         *        application logs.  False, otherwise.
         */
        private StoredProcedureArgument(
            final int index,
            final int type,
            final Object value,
            final boolean sensitive)
        {
            _index = index;
            _type = type;
            _value = value;
            _sensitive = sensitive;
        }

        // The index representing what position in the stored procedure
        // argument list this argument maintains.
        private final int _index;

        // The JDBC type integer representing what JDBC type this argument
        // will be converted to at the stored procedure layer.
        private final int _type;

        // The java object representation of the argument.
        private final Object _value;

        // A flag used to determine whether this data should appear in the
        // clear within application logs or not.  True, if it should NOT be
        // in the clear.
        private final boolean _sensitive;

        // The string mask to use in the application logs in place of a value
        // that is flagged as sensitive.
        private static final String MASK = "******";
    }

    //------------------------------------------------------------------------
    // :: Private Interface

    /**
     * @return The next argument index.
     */
    private int nextIndex()
    {
        return _arguments.size() + 1;
    }

    //------------------------------------------------------------------------
    // :: Private Data Members

    // A list of stored procedure arguments.  A list is used to maintain a
    // FIFO order.  This cannot be null, but it could be empty.
    private final List<StoredProcedureArgument> _arguments = new ArrayList<>();
}
