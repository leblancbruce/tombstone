package com.tombstone.server.ui.admin.bean;

import static com.tombstone.server.ui.admin.bean.Page.DEFAULT_ERROR_PAGE;
import static com.tombstone.server.ui.admin.bean.Page.HOME_PAGE;
import static com.tombstone.server.ui.admin.bean.Page.INCORRECT_LOGIN_PAGE;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.lebcool.common.logging.Logger;
import com.tombstone.server.domain.ApplicationUser;
import com.tombstone.server.repository.ApplicationUserRepository;
import com.tombstone.server.repository.exception.LoadException;
import com.tombstone.server.repository.exception.RepositoryInstantiationException;

@ManagedBean
@RequestScoped
public final class LoginBean implements Serializable
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
        LOGGER.info(this, "Attempting to authenticate the user.");

        try
        {
            final ApplicationUserRepository repository
                = new ApplicationUserRepository();

            final ApplicationUser applicationUser
                = repository.loadByUsername(_userName);

            if(applicationUser != null
                && applicationUser.isActive()
                && applicationUser.getPassword().equals(_password))
            {
                return HOME_PAGE;
            }

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

        return DEFAULT_ERROR_PAGE;
    }

    @Override
    public String toString()
    {
        return getClass().getName() + "["
            + "userName=\"" + _userName + "\""
            + " password=\"******\"]";
    }
    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private String _userName;

    private String _password;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final Logger LOGGER = new Logger(LoginBean.class);

    private static final long serialVersionUID = 1L;
}
