package com.tombstone.server.ui.admin.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.tombstone.server.bean.ApplicationBean;
import com.tombstone.server.domain.CemeterySummary;
import com.tombstone.server.domain.CemeterySummaryRepository;
import com.tombstone.server.domain.County;
import com.tombstone.server.domain.exception.LoadException;

@ManagedBean
@RequestScoped
public final class CemeteryBean implements Serializable
{
    public void setApplicationBean(final ApplicationBean applicationBean)
    {
        _applicationBean = applicationBean;
    }

    public County[] getCounties()
    {
        return County.values();
    }

    public List<CemeterySummary> getCemeterySummaries() throws LoadException
    {
        final CemeterySummaryRepository repository
             = new CemeterySummaryRepository(_applicationBean.getDataSource());

        return new ArrayList<>(repository.load(1, 1));
    }

    @ManagedProperty(name="applicationBean", value="#{applicationBean}")
    private ApplicationBean _applicationBean;

    private static final long serialVersionUID = 1L;
}
