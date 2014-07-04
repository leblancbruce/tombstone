package com.tombstone.server.domain;

import com.lebcool.common.domain.DomainObject;

public final class Image extends DomainObject
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public byte[] getThumbnail()
    {
        return _thumbnail;
    }

    public void setThumbnail(final byte[] thumbnail)
    {
        _thumbnail = thumbnail;
    }
    public byte[] getImage()
    {
        return _image;
    }

    public void setImage(final byte[] image)
    {
        _image = image;
    }

    public boolean isDefaultImage()
    {
        return _defaultImage;
    }

    public void setDefaultImage(final boolean defaultImage)
    {
        _defaultImage = defaultImage;
    }

    public Long getApplicationUserId()
    {
        return _applicationUserId;
    }

    public void setApplicationUserId(final Long applicationUserId)
    {
        _applicationUserId = applicationUserId;
    }

    public Long getCemeteryId()
    {
        return _cemeteryId;
    }

    public void setCemeteryId(final Long cemeteryId)
    {
        _cemeteryId = cemeteryId;
    }

    public Long getPlotId()
    {
        return _plotId;
    }

    public void setPlotId(final Long plotId)
    {
        _plotId = plotId;
    }

    public Long getPersonId()
    {
        return _personId;
    }

    public void setPersonId(final Long personId)
    {
        _personId = personId;
    }

    //:: ---------------------------------------------------------------------
    //:: Protected Interface

    @Override
    protected String getMembersAsKeyValueString()
    {
        return " thumbnail="
            + (_thumbnail != null ? "<size=" + _thumbnail.length: "null")
            + " image="
            + (_image != null ? "<size=" + _image.length: "null")
            + " defaultImage=" + _defaultImage
            + " applicationUserId=" + _applicationUserId
            + " cemeteryId=" + _cemeteryId
            + " plotId=" + _plotId
            + " personId=" + _personId;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private byte[] _thumbnail;

    private byte[] _image;

    private boolean _defaultImage;

    private Long _applicationUserId;

    private Long _cemeteryId;

    private Long _plotId;

    private Long _personId;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;
}
