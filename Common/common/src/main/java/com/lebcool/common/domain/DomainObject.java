package com.lebcool.common.domain;

import org.joda.time.LocalDateTime;

/**
 * <p>
 * The base domain object class that all domain objects should inherit from.
 * </p>
 */
public abstract class DomainObject
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    /**
     * @return The unique database identifier assigned to this domain object
     *         when it was persisted or null if this domain object has not been
     *         persisted yet.
     */
    public Long getId()
    {
        return _id;
    }

    public long getVersion()
    {
        return _version;
    }

    public LocalDateTime getCreatedOn()
    {
        return _createdOn;
    }

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

    protected void updateControlledFields(
        final Long id,
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

    private long _version;

    private LocalDateTime _createdOn;

    private LocalDateTime _updatedOn;
}
