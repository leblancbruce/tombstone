package com.tombstone.server.ui.admin.bean;

import static com.tombstone.server.ui.admin.bean.Page.CEMETERY_IMAGES_PAGE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.naming.NamingException;
import javax.servlet.http.Part;

import net.coobird.thumbnailator.Thumbnailator;

import org.apache.commons.io.IOUtils;

import com.tombstone.server.domain.Image;
import com.tombstone.server.domain.ImageRepository;
import com.tombstone.server.domain.exception.LoadException;
import com.tombstone.server.domain.exception.SaveException;

@ManagedBean
@RequestScoped
public final class CemeteryImagesPageBean extends UIAdminBean
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public Part getFile()
    {
        return _file;
    }

    public void setFile(final Part file)
    {
         _file = file;
    }

    public long getCemeteryId()
    {
        return _cemeteryId;
    }

    public void setCemeteryId(final long cemeteryId)
    {
        _cemeteryId = cemeteryId;
    }

    public String upload() throws IOException, SaveException, NamingException
    {
        if(_file == null)
        {
            return CEMETERY_IMAGES_PAGE;
        }

        ByteArrayOutputStream thumbnailBytesStream = null;

        try
        {
            final InputStream is = _file.getInputStream();

            thumbnailBytesStream = new ByteArrayOutputStream(1024);

            Thumbnailator.createThumbnail(is, thumbnailBytesStream, 100, 100);
            final byte[] imageBytes
                = IOUtils.toByteArray(_file.getInputStream());

            final Image image = new Image();
            image.setThumbnail(thumbnailBytesStream.toByteArray());
            image.setImage(imageBytes);
            image.setDefaultImage(false);
            image.setCemeteryId(_cemeteryId);

            final ImageRepository repository
                = new ImageRepository(getApplicationBean().getDataSource());
            repository.save(image);
        }
        finally
        {
            if(thumbnailBytesStream != null)
            {
                thumbnailBytesStream.close();
            }
        }

        return CEMETERY_IMAGES_PAGE;
    }

    public List<ThumbnailWrapper> getThumbnails() throws LoadException
    {
        final List<ThumbnailWrapper> wrappers = new ArrayList<>();

        final ImageRepository repository
            = new ImageRepository(getApplicationBean().getDataSource());

        for(final long id : repository.loadImageIdsByCemeteryId(_cemeteryId))
        {
            wrappers.add(new ThumbnailWrapper(id));
        }

        return Collections.unmodifiableList(wrappers);
    }

    public String show()
    {
        return CEMETERY_IMAGES_PAGE;
    }

    public String makeDefault()
    {
        return CEMETERY_IMAGES_PAGE;
    }

    //:: ---------------------------------------------------------------------
    //:: Public Nested Classes

    public static final class ThumbnailWrapper implements Serializable
    {
        public ThumbnailWrapper(final long id)
        {
            _id = id;
        }

        public long getId()
        {
            return _id;
        }

        public String getThumbnailImageUrl()
        {
            return THUMBNAIL_IMAGE_URL_PREFIX + _id;
        }

        private final long _id;

        private static final long serialVersionUID = 1L;

        private static final String THUMBNAIL_IMAGE_URL_PREFIX
            = "/rest/getImage/thumbnail/";
   }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private Part _file;

    @ManagedProperty(name="cemeteryId", value="#{param.cemeteryId}")
    private long _cemeteryId;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;
}
