package com.tombstone.server.ui.admin.bean;

import static com.tombstone.server.ui.admin.bean.Page.CEMETERY_SUMMARIES_PAGE;
import static com.tombstone.server.ui.admin.bean.Page.USER_SUMMARIES_PAGE;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.tombstone.server.bean.Bean;

@ManagedBean
@SessionScoped
public final class MenuBean extends Bean
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public String cemeteries()
    {
        return CEMETERY_SUMMARIES_PAGE;
    }

    public String users()
    {
        return USER_SUMMARIES_PAGE;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;
}
