package com.tombstone.server.rest;

import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.lebcool.common.logging.Logger;
import com.tombstone.server.database.DataSourceLoader;
import com.tombstone.server.domain.ImageRepository;
import com.tombstone.server.domain.exception.LoadException;
import com.tombstone.server.rest.exception.EndPointInstantiationException;

@Path("/getImage")
public final class GetImageEndPoint
{
    //:: ---------------------------------------------------------------------
    //:: Public Construction

    public GetImageEndPoint()
    {
        LOGGER.info(this, "Attempting to instantiate this end point.");

        try
        {
            _dataSource = DataSourceLoader.load();

            LOGGER.info(this, "Successfully instantiated this end point.");
        }
        catch(final NamingException e)
        {
            throw new EndPointInstantiationException(
                "Unable to instantiate the get image end point because the "
                    + "data source cannot be loaded.", e);
        }
    }

    //:: ---------------------------------------------------------------------
    //:: Public Interface

    @GET
    @Path("/image/{id}")
    @Produces(JPG_MEDIA_TYPE)
    public Response getImage(@PathParam("id") final long id)
    {
        LOGGER.debug(this, "Attempting to get the image using id={}.", id);

        final ImageRepository repository = new ImageRepository(_dataSource);

        try
        {
            final byte[] imageBytes = repository.loadImageBytesById(id);

            if(imageBytes != null)
            {
                LOGGER.debug(this, "Found the image using id={}.  "
                    + "Returning it.", id);

                return Response
                    .status(Status.OK)
                    .entity(imageBytes)
                    .build();
            }

            LOGGER.warn(this, "No image found using id={}.", id);
        }
        catch(final LoadException e)
        {
            LOGGER.error(this, "Unable to return the image requested.  "
                + "Returning a http status 404.", e);
        }

        return Response
            .status(Status.NOT_FOUND)
            .build();
    }

    @GET
    @Path("/thumbnail/{id}")
    @Produces(JPG_MEDIA_TYPE)
    public Response getThumbnailImage(@PathParam("id") final long id)
    {
        LOGGER.debug(this, "Attempting to get the thumbnail image using id={}.",
           id);

        final ImageRepository repository = new ImageRepository(_dataSource);

        try
        {
            final byte[] imageBytes = repository.loadThumbnailBytesById(id);

            if(imageBytes != null)
            {
                LOGGER.debug(this, "Found the thumbnail image "
                   + "using id={}.  Returning it.", id);

                return Response
                    .status(Status.OK)
                    .entity(imageBytes)
                    .build();
            }

            LOGGER.warn(this, "No thumbnail image found using id={}.", id);
        }
        catch(final LoadException e)
        {
            LOGGER.error(this, "Unable to return the thumbnail image "
                + "requested.  Returning a http status 404.", e);
        }

        return Response
            .status(Status.NOT_FOUND)
            .build();
    }

    @Override
    public String toString()
    {
        return getClass().getName();
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private DataSource _dataSource;

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final Logger LOGGER = new Logger(GetImageEndPoint.class);

    private static final String JPG_MEDIA_TYPE = "image/jpeg";
}
