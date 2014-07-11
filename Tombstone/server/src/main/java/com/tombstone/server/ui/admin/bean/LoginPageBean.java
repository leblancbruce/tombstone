package com.tombstone.server.ui.admin.bean;

import static com.tombstone.server.ui.admin.bean.Page.CEMETERY_SUMMARIES_PAGE;
import static com.tombstone.server.ui.admin.bean.Page.ERROR_PAGE;
import static com.tombstone.server.ui.admin.bean.Page.INCORRECT_LOGIN_PAGE;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.lebcool.common.logging.Logger;
import com.tombstone.server.domain.ApplicationUser;
import com.tombstone.server.domain.ApplicationUserRepository;
import com.tombstone.server.domain.exception.LoadException;
import com.tombstone.server.domain.exception.RepositoryInstantiationException;

@ManagedBean
@RequestScoped
public final class LoginPageBean extends UIAdminBean
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public String getUserName()
    {
        return _userName;
    }

    public void setUserName(final String userName)
    {
        _userName = userName;
    }

    public String getPassword()
    {
        return _password;
    }

    public void setPassword(final String password)
    {
        _password = password;
    }

    public String login()
    {
        LOGGER.info(this, "Attempting to authenticate the user with "
            + "username=\"{}\"", _userName);

        try
        {
            final ApplicationUserRepository repository
                = new ApplicationUserRepository(
                    getApplicationBean().getDataSource());

            final ApplicationUser applicationUser
                = repository.loadByUsername(_userName);

            if(applicationUser != null
                && applicationUser.isActive()
                && applicationUser.getPassword().equals(_password))
            {
                getSessionBean().setLoggedInUser(applicationUser);

                LOGGER.info(this, "Successfully authenticated user={}.  "
                    + "Re-direting to the cemeteries page.", applicationUser);

                return CEMETERY_SUMMARIES_PAGE;
            }

            LOGGER.warn(this, "Unable to authenticate the user with "
                + "username=\"{}\".  Re-directing the user to the incorrect "
                + "login page.", _userName);

            return INCORRECT_LOGIN_PAGE;
        }
        catch(final RepositoryInstantiationException rie)
        {
            LOGGER.error(this, "Unable to instantiate the application user "
                + "repository needed to authenticate the user.", rie);
        }
        catch(final LoadException e)
        {
            LOGGER.error(this, "An exception occurred while querying the "
                + "database for the supplied username.", e);
        }

        return ERROR_PAGE;
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private String _userName;

    private String _password;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final Logger LOGGER = new Logger(LoginPageBean.class);

    private static final long serialVersionUID = 1L;
}
