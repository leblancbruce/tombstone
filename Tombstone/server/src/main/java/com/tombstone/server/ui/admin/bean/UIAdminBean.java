package com.tombstone.server.ui.admin.bean;

import javax.faces.bean.ManagedProperty;

import com.tombstone.server.bean.ApplicationBean;
import com.tombstone.server.bean.Bean;

public abstract class UIAdminBean extends Bean
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public final void setApplicationBean(final ApplicationBean applicationBean)
    {
        _applicationBean = applicationBean;
    }

    public final void setSessionBean(final SessionBean sessionBean)
    {
        _sessionBean = sessionBean;
    }

    //:: ---------------------------------------------------------------------
    //:: Package-Private Interface

    final ApplicationBean getApplicationBean()
    {
        return _applicationBean;
    }

    final SessionBean getSessionBean()
    {
        return _sessionBean;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    @ManagedProperty(name="applicationBean", value="#{applicationBean}")
    private ApplicationBean _applicationBean;

    @ManagedProperty(name="sessionBean", value="#{sessionBean}")
    private SessionBean _sessionBean;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;
}