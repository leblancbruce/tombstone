package com.tombstone.server.ui.admin.bean;

import static com.tombstone.server.ui.admin.bean.Page.CEMETERY_DETAILS_PAGE;
import static com.tombstone.server.ui.admin.bean.Page.CEMETERY_PHOTOS_PAGE;
import static com.tombstone.server.ui.admin.bean.Page.PLOT_SUMMARIES_PAGE;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.lebcool.common.logging.Logger;

@ManagedBean
@RequestScoped
public final class CemeteryTabsBean extends UIAdminBean
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

    public String plotSummaries()
    {
        return PLOT_SUMMARIES_PAGE;
    }

    public String details()
    {
        return CEMETERY_DETAILS_PAGE;
    }

    public String photos()
    {
        return CEMETERY_PHOTOS_PAGE;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    @ManagedProperty(name="cemeteryId", value="#{param.cemeteryId}")
    private long _cemeteryId;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = new Logger(CemeteryTabsBean.class);
}
