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
import com.tombstone.server.domain.Image;
import com.tombstone.server.domain.ImageRepository;
import com.tombstone.server.domain.exception.LoadException;
import com.tombstone.server.rest.exception.EndPointInstantiationException;

@Path("/image")
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

            LOGGER.info(this, "Successfully constructed this end point.");
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
    @Path("/user/{id}")
    @Produces("image/jpeg")
    public Response getApplicationUserImage(@PathParam("id") final long id)
    {
        LOGGER.debug(this, "Attempting to get the application user image "
            + "using id=" + id);

        final ImageRepository repository = new ImageRepository(_dataSource);

        try
        {
            final Image image = repository.loadById(id);

            if(image != null)
            {
                LOGGER.debug(this, "Found the application user image "
                   + "using id=" + id + ".  Returning it.");

                return Response
                    .status(Status.OK)
                    .entity(image.getImage())
                    .build();
            }

            LOGGER.debug(this, "No application user image found "
                + "using id=" + id + ".");
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
    @Path("/cemetery/{id}")
    @Produces("image/jpeg")
    public Response getCemeteryImage(@PathParam("id") final long id)
    {
        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/plot/{id}")
    @Produces("image/jpeg")
    public Response getPlotImage(@PathParam("id") final long id)
    {
        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/person/{id}")
    @Produces("image/jpeg")
    public Response getPersonImage()
    {
        return Response.status(Status.NOT_FOUND).build();
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
}
