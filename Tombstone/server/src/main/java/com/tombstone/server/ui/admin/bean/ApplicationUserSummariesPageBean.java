package com.tombstone.server.ui.admin.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.lebcool.common.logging.Logger;
import com.tombstone.server.domain.ApplicationUserSummary;
import com.tombstone.server.domain.ApplicationUserSummaryRepository;
import com.tombstone.server.domain.exception.LoadException;

@ManagedBean
@RequestScoped
public final class ApplicationUserSummariesPageBean extends UIAdminBean
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public List<ApplicationUserSummaryWrapper> getApplicationUserSummaries()
        throws LoadException
    {
        final List<ApplicationUserSummaryWrapper> wrappers = new ArrayList<>();

        final ApplicationUserSummaryRepository repository
             = new ApplicationUserSummaryRepository(
                 getApplicationBean().getDataSource());

        for(final ApplicationUserSummary summary : repository.load(1, 1))
        {
            wrappers.add(new ApplicationUserSummaryWrapper(summary));
        }

        LOGGER.debug(this, "Returning {} application user summaries.",
            wrappers.size());

        return Collections.unmodifiableList(wrappers);
    }

    public String edit()
    {
        return "";
    }

    //:: ---------------------------------------------------------------------
    //:: Public Nested Classes

    public static final class ApplicationUserSummaryWrapper
        implements Serializable
    {
        public ApplicationUserSummaryWrapper(
            final ApplicationUserSummary applicationUserSummary)
        {
            _applicationUserSummary = applicationUserSummary;
        }

        public long getApplicationUserId()
        {
            return _applicationUserSummary.getApplicationUserId();
        }

        public String getFirstName()
        {
            return _applicationUserSummary.getFirstName();
        }

        public String getThumbnailImageUrl()
        {
            final Long thumbnailImageId
                = _applicationUserSummary.getThumbnailImageId();

            if(thumbnailImageId == null)
            {
                return NO_THUMBNAIL_AVAILABLE_URL;
            }

            return THUMBNAIL_IMAGE_URL_PREFIX + thumbnailImageId;
        }

        private final ApplicationUserSummary _applicationUserSummary;

        private static final long serialVersionUID = 1L;

        private static final String THUMBNAIL_IMAGE_URL_PREFIX
            = "/rest/getImage/thumbnail/";

        private static final String NO_THUMBNAIL_AVAILABLE_URL
            = "/resources/images/no-thumbnail-available.png";
    }

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER
        = new Logger(ApplicationUserSummariesPageBean.class);

}
