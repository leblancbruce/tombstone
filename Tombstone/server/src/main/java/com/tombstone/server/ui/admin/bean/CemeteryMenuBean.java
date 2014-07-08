package com.tombstone.server.ui.admin.bean;

import static com.tombstone.server.ui.admin.bean.Page.CEMETERY_DETAILS_PAGE;
import static com.tombstone.server.ui.admin.bean.Page.CEMETERY_PHOTOS_PAGE;
import static com.tombstone.server.ui.admin.bean.Page.PLOT_SUMMARIES_PAGE;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public final class CemeteryMenuBean implements Serializable
{
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

    private static final long serialVersionUID = 1L;
}
