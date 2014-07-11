package com.tombstone.server.ui.admin.bean;

import static com.tombstone.server.ui.admin.bean.Page.NOT_LOGGED_IN_PAGE;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.lebcool.common.logging.Logger;
import com.tombstone.server.bean.Bean;
import com.tombstone.server.domain.ApplicationUser;

@ManagedBean
@SessionScoped
public final class SessionBean extends Bean
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

    public void reDirectIfNotAuthenticated() throws IOException
    {
        LOGGER.debug(this, "Checking to see if the user is authenticated.");

        try
        {
            if(_loggedInUser != null)
            {
                LOGGER.debug(this, "The user is authenticated.  Proceeding.");

                return;
            }
        }
        catch(final Throwable e)
        {
             LOGGER.error(this, "An exception was encountered while "
                + "verifying whether the user is authenticated.", e);
        }

        LOGGER.warn(this, "The user is NOT authenticated.  "
            + "Re-directing the user to the page=\"" + NOT_LOGGED_IN_PAGE
            + "\".");

        final FacesContext facesContext
            = FacesContext.getCurrentInstance();

        final ExternalContext externalContext
            = facesContext.getExternalContext();

        try
        {
            externalContext.redirect(NOT_LOGGED_IN_PAGE + ".jsf");
        }
        catch(final IOException ioe)
        {
            LOGGER.error(this, "An exception was encountered while "
                + "trying to redirect to the page=\""
                + NOT_LOGGED_IN_PAGE + "\".", ioe);

            throw ioe;
        }
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

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final Logger LOGGER = new Logger(SessionBean.class);

    private static final long serialVersionUID = 1L;
}
