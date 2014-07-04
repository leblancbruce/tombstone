package com.tombstone.server.ui.admin.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import com.lebcool.common.logging.Logger;
import com.tombstone.server.database.DataSourceLoader;
import com.tombstone.server.domain.Image;
import com.tombstone.server.domain.ImageRepository;

@ManagedBean
@ViewScoped
public final class ImageBean implements Serializable
{
    public ImageBean()
    {
        LOGGER.info(this, "Created this bean.");
    }

    public String upload()
    {
        LOGGER.info(this, "Saving the image.");

        try
        {
            final byte[] imageBytes
                = IOUtils.toByteArray(_file.getInputStream());

            final Image image = new Image();
            image.setThumbnail(imageBytes);
            image.setImage(imageBytes);
            image.setApplicationUserId(1L);

            final ImageRepository repository
                = new ImageRepository(DataSourceLoader.load());
            repository.save(image);
        }
        catch(final Throwable e)
        {
            LOGGER.error(this, "Cannot save the image.", e);
        }

        return "";
    }

    public Part getFile()
    {
        return _file;
    }

    public void setFile(final Part file)
    {
        LOGGER.info(this, "Setting the file.");
         _file = file;
    }

    private Part _file;

    private static final Logger LOGGER = new Logger(ImageBean.class);

    private static final long serialVersionUID = 1L;
}
