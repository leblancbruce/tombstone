package com.tombstone.server.ui.admin.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.tombstone.server.bean.ApplicationBean;
import com.tombstone.server.domain.Cemetery;
import com.tombstone.server.domain.CemeteryRepository;
import com.tombstone.server.domain.exception.LoadException;

@ManagedBean
@RequestScoped
public final class CemeteryDetailsPageBean implements Serializable
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public void setApplicationBean(final ApplicationBean applicationBean)
    {
        _applicationBean = applicationBean;
    }

    public long getCemeteryId()
    {
        return _cemeteryId;
    }

    public void setCemeteryId(final long cemeteryId)
    {
        _cemeteryId = cemeteryId;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(final String name)
    {
        _name = name;
    }

    @PostConstruct
    public void loadCemetery() throws LoadException
    {
        _cemeteryRepository = new CemeteryRepository(
            _applicationBean.getDataSource(),
            _sessionBean.getLoggedInUser());

        final Cemetery cemetery = _cemeteryRepository.loadById(_cemeteryId);

        _name = cemetery.getName();
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    @ManagedProperty(name="applicationBean", value="#{applicationBean}")
    private ApplicationBean _applicationBean;

    @ManagedProperty(name="sessionBean", value="#{sessionBean}")
    private SessionBean _sessionBean;

    @ManagedProperty(name="cemeteryId", value="#{param.cemeteryId}")
    private long _cemeteryId;

    private CemeteryRepository _cemeteryRepository;

    private String _name;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;
}
