package com.tombstone.server.ui.admin.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.lebcool.common.logging.Logger;
import com.tombstone.server.bean.ApplicationBean;
import com.tombstone.server.domain.ApplicationUser;

@ManagedBean
@SessionScoped
public final class SessionBean implements Serializable
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public ApplicationUser getLoggedInUser()
    {
        return _loggedInUser;
    }

    public void setLoggedInUser(final ApplicationUser loggedInUser)
    {
        _loggedInUser = loggedInUser;
    }

    public void setApplicationBean(final ApplicationBean applicationBean)
    {
        _applicationBean = applicationBean;
    }

    @Override
    public String toString()
    {
        return getClass().getName() + "["
            + "loggedInUser=" + _loggedInUser + "\"]";
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private ApplicationUser _loggedInUser;

    @ManagedProperty(name="applicationBean", value="#{applicationBean}")
    private ApplicationBean _applicationBean;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final Logger LOGGER = new Logger(SessionBean.class);

    private static final long serialVersionUID = 1L;
}
