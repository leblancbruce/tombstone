package com.lebcool.common.domain;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

/**
 * <p>
 * The base domain object class that all domain objects should inherit from.
 * </p>
 */
public abstract class DomainObject implements Serializable
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    /**
     * @return The unique database identifier assigned to this domain object
     *         when it was persisted to the database or null if this domain
     *         object has not been persisted yet.  The underlying database is
     *         responsible for setting this value.
     */
    public Long getId()
    {
        return _id;
    }

    /**
     * @return A version number used for concurrency control.  This value is
     *         maintained by the underlying database.
     */
    public long getVersion()
    {
        return _version;
    }

    /**
     * @return The time, as a {@link LocalDateTime} object, of when this
     *         domain object was persisted to the database.  This cannot be
     *         null and it is maintained by the underlying database.
     */
    public LocalDateTime getCreatedOn()
    {
        return _createdOn;
    }

    /**
     * @return The time, as a {@link LocalDateTime} object, of when this
     *         domain object was last updated within the database.  This
     *         cannot be null and it is maintained by the underlying database.
     */
    public LocalDateTime getUpdatedOn()
    {
        return _updatedOn;
    }

    /**
     * @return A string representation of this object used for logging
     *         purposes.
     */
    @Override
    public String toString()
    {
        return getClass().getName() + "["
            + "id=" + _id
            + getMembersAsKeyValueString()
            + "]";
    }

    //:: ---------------------------------------------------------------------
    //:: Protected Interface

    /**
     * This method is implemented by the children of {@link DomainObject}.  It
     * allows child objects to return a comma separated string of key-value
     * pairs that will appear in the {@link #toString()} method.
     *
     * @return A comma separated string of key-value pairs for the members
     *         that this domain object wants to expose within its
     *         {@link #toString()} method.
     */
    protected abstract String getMembersAsKeyValueString();

    /**
     * The following method exposes a restricted means for the children of this
     * class to update the id, version, createdOn, and updatedOn fields.  Since
     * these fields are controlled by the database itself, we do not want to
     * expose public setters in the API.  We expect load and refresh
     * operations to call this method.
     *
     * @param id
     *        The unique database identifier.
     * @param version
     *        The current version of the domain object within the database.
     * @param createdOn
     *        The time that this domain object was saved to the database.  This
     *        cannot be null.
     * @param updatedOn
     *        The last time this domain object was updated within the database.
     *        This cannot be null.
     */
    protected void updateControlledFields(
        final long id,
        final long version,
        final LocalDateTime createdOn,
        final LocalDateTime updatedOn)
    {
        _id = id;
        _version = version;
        _createdOn = createdOn;
        _updatedOn = updatedOn;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    // The unique database identifier that is assigned to this domain object
    // when it is persisted and associated with it thereafter.  This can be
    // null IFF it was not persisted yet.
    private Long _id;

    // A version number used by the database to control concurrent writes
    // to the record representing this domain object.  This value is controlled
    // and maintained by the database.
    private long _version;

    // The time that this domain object was originally saved to the database.
    // This cannot be null.
    private LocalDateTime _createdOn;

    // The time of the last update made to this domain object within the
    // database.  This cannot be null.
    private LocalDateTime _updatedOn;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    // The following is needed by the serializable contract.
    private static final long serialVersionUID = 1L;
}
