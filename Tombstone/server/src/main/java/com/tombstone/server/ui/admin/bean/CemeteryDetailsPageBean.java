package com.tombstone.server.ui.admin.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.lebcool.common.logging.Logger;
import com.tombstone.server.domain.Cemetery;
import com.tombstone.server.domain.CemeteryRepository;
import com.tombstone.server.domain.exception.LoadException;

@ManagedBean
@RequestScoped
public final class CemeteryDetailsPageBean extends UIAdminBean
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public long getCemeteryId()
    {
        return _cemeteryId;
    }

    public void setCemeteryId(final long cemeteryId)
    {
        LOGGER.debug(this, "Setting the cemetery id to {}.", _cemeteryId);

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
    public void loadCemetery()
    {
        try
        {
            LOGGER.debug(this, "Attempting to initialize this bean using "
                + "cemeteryId={}.", _cemeteryId);

            _cemeteryRepository = new CemeteryRepository(
                getApplicationBean().getDataSource(),
                getSessionBean().getLoggedInUser());

            final Cemetery cemetery
                = _cemeteryRepository.loadById(_cemeteryId);

            _name = cemetery.getName();
        }
        catch(final LoadException e)
        {
            LOGGER.error(this, "An exception occured while initializing this "
                + "bean.", e);
        }
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    @ManagedProperty(name="cemeteryId", value="#{param.cemeteryId}")
    private long _cemeteryId;

    private CemeteryRepository _cemeteryRepository;

    private String _name;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER
        = new Logger(CemeteryDetailsPageBean.class);
}
