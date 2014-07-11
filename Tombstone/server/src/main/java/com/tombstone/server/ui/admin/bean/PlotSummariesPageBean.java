package com.tombstone.server.ui.admin.bean;

import static com.tombstone.server.ui.admin.bean.Page.PERSON_SUMMARIES_PAGE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.lebcool.common.logging.Logger;
import com.tombstone.server.domain.PlotSummary;
import com.tombstone.server.domain.PlotSummaryRepository;
import com.tombstone.server.domain.PlotType;
import com.tombstone.server.domain.exception.LoadException;

@ManagedBean
@RequestScoped
public final class PlotSummariesPageBean extends UIAdminBean
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public long getCemeteryId()
    {
        return _cemeteryId;
    }

    public void setCemeteryId(final long cemeteryId)
    {
        _cemeteryId = cemeteryId;
    }

    public List<PlotSummaryWrapper> getPlotSummaries() throws LoadException
    {
        final List<PlotSummaryWrapper> wrappers = new ArrayList<>();

        final PlotSummaryRepository repository
             = new PlotSummaryRepository(getApplicationBean().getDataSource());

        for(final PlotSummary summary : repository.load(_cemeteryId, 1, 1))
        {
            wrappers.add(new PlotSummaryWrapper(summary));
        }

        LOGGER.debug(this, "Returning {} plot summaries.", wrappers.size());

        return Collections.unmodifiableList(wrappers);
    }

    public String edit()
    {
        LOGGER.debug(this, "Editing the plot");

        return PERSON_SUMMARIES_PAGE;
    }

    //:: ---------------------------------------------------------------------
    //:: Public Nested Classes

    public static final class PlotSummaryWrapper implements Serializable
    {
        public PlotSummaryWrapper(final PlotSummary plotSummary)
        {
            _plotSummary = plotSummary;
        }

        public long getPlotId()
        {
            return _plotSummary.getPlotId();
        }

        public String getName()
        {
            return _plotSummary.getName();
        }

        public PlotType getPlotType()
        {
            return _plotSummary.getPlotType();
        }

        public long getNumberOfDeceased()
        {
            return _plotSummary.getNumberOfDeceased();
        }

        public String getLastUpdatedOnDateTime()
        {
            return _plotSummary.getLastUpdatedDateTime()
                .toString(DATE_FORMAT, new Locale("en", "CA"));
        }

        public String getLastUpdatedByUserName()
        {
            return _plotSummary.getLastUpdatedByUserName();
        }

        public String getThumbnailImageUrl()
        {
            final Long thumbnailImageId
                = _plotSummary.getThumbnailImageId();

            if(thumbnailImageId == null)
            {
                return NO_THUMBNAIL_AVAILABLE_URL;
            }

            return THUMBNAIL_IMAGE_URL_PREFIX + thumbnailImageId;
        }

        private final PlotSummary _plotSummary;

        private static final long serialVersionUID = 1L;

        private static final String DATE_FORMAT = "MMM dd, yyyy";

        private static final String THUMBNAIL_IMAGE_URL_PREFIX
            = "/rest/getImage/thumbnail/";

        private static final String NO_THUMBNAIL_AVAILABLE_URL
            = "/resources/images/no-thumbnail-available.png";
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    @ManagedProperty(name="cemeteryId", value="#{param.cemeteryId}")
    private long _cemeteryId;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER
        = new Logger(PlotSummariesPageBean.class);
}
