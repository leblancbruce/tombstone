package com.tombstone.server.ui.admin.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.tombstone.server.bean.ApplicationBean;
import com.tombstone.server.domain.ApplicationUserSummary;
import com.tombstone.server.domain.ApplicationUserSummaryRepository;
import com.tombstone.server.domain.exception.LoadException;

@ManagedBean
@RequestScoped
public final class UsersPageBean implements Serializable
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public void setApplicationBean(final ApplicationBean applicationBean)
    {
        _applicationBean = applicationBean;
    }

    public List<ApplicationUserSummary> getApplicationUserSummaries()
        throws LoadException
    {
        final ApplicationUserSummaryRepository repository
             = new ApplicationUserSummaryRepository(
                 _applicationBean.getDataSource());

        return repository.load(1, 1);
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    @ManagedProperty(name="applicationBean", value="#{applicationBean}")
    private ApplicationBean _applicationBean;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;
}
