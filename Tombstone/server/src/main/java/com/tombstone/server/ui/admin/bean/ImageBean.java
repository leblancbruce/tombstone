package com.tombstone.server.ui.admin.bean;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import net.coobird.thumbnailator.Thumbnailator;

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
            image.setDefaultImage(true);
            image.setCemeteryId(1L);

            final ImageRepository repository
                = new ImageRepository(DataSourceLoader.load());
            repository.save(image);
        }
        catch(final Throwable e)
        {
            LOGGER.error(this, "Cannot save the image.", e);
        }
        finally
        {
            if(thumbnailBytesStream != null)
            {
                try
                {
                    thumbnailBytesStream.close();
                }
                catch(final Throwable e)
                {
                    LOGGER.error(this, "Cannot save the image.", e);
                }
            }
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
