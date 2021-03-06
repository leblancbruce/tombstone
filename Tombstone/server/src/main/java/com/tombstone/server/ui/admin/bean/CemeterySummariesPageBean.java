package com.tombstone.server.ui.admin.bean;

import static com.tombstone.server.ui.admin.bean.Page.PLOT_SUMMARIES_PAGE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.lebcool.common.logging.Logger;
import com.tombstone.server.domain.CemeteryStatus;
import com.tombstone.server.domain.CemeterySummary;
import com.tombstone.server.domain.CemeterySummaryRepository;
import com.tombstone.server.domain.County;
import com.tombstone.server.domain.exception.LoadException;

@ManagedBean
@RequestScoped
public final class CemeterySummariesPageBean extends UIAdminBean
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public County[] getCounties()
    {
        return County.values();
    }

    public List<CemeterySummaryWrapper> getCemeterySummaries() throws LoadException
    {
        final List<CemeterySummaryWrapper> wrappers = new ArrayList<>();

        final CemeterySummaryRepository repository
             = new CemeterySummaryRepository(
                 getApplicationBean().getDataSource());

        for(final CemeterySummary summary : repository.load(1, 1))
        {
            wrappers.add(new CemeterySummaryWrapper(summary));
        }

        LOGGER.debug(this, "Returning {} cemetery summaries.",
            wrappers.size());

        return Collections.unmodifiableList(wrappers);
    }

    public String edit()
    {
        LOGGER.debug(this, "Editing the cemetery");

        return PLOT_SUMMARIES_PAGE;
    }

    //:: ---------------------------------------------------------------------
    //:: Public Nested Classes

    public static final class CemeterySummaryWrapper implements Serializable
    {
        public CemeterySummaryWrapper(final CemeterySummary cemeterySummary)
        {
            _cemeterySummary = cemeterySummary;
        }

        public long getCemeteryId()
        {
            return _cemeterySummary.getCemeteryId();
        }

        public String getName()
        {
            return _cemeterySummary.getName();
        }

        public Short getEstablishedYear()
        {
            return _cemeterySummary.getEstablishedYear();
        }

        public CemeteryStatus getCemeteryStatus()
        {
            return _cemeterySummary.getCemeteryStatus();
        }

        public long getNumberOfPlots()
        {
            return _cemeterySummary.getNumberOfPlots();
        }

        public long getNumberOfDeceased()
        {
            return _cemeterySummary.getNumberOfDeceased();
        }

        public String getLastUpdatedOnDateTime()
        {
            return _cemeterySummary.getLastUpdatedDateTime()
                .toString(DATE_FORMAT, new Locale("en", "CA"));
        }

        public String getLastUpdatedByUserName()
        {
            return _cemeterySummary.getLastUpdatedByUserName();
        }

        public String getThumbnailImageUrl()
        {
            final Long thumbnailImageId
                = _cemeterySummary.getThumbnailImageId();

            if(thumbnailImageId == null)
            {
                return NO_THUMBNAIL_AVAILABLE_URL;
            }

            return THUMBNAIL_IMAGE_URL_PREFIX + thumbnailImageId;
        }

        private final CemeterySummary _cemeterySummary;

        private static final long serialVersionUID = 1L;

        private static final String DATE_FORMAT = "MMM dd, yyyy";

        private static final String THUMBNAIL_IMAGE_URL_PREFIX
            = "/rest/getImage/thumbnail/";

        private static final String NO_THUMBNAIL_AVAILABLE_URL
            = "/resources/images/no-thumbnail-available.png";
    }

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER
        = new Logger(CemeterySummariesPageBean.class);
}
