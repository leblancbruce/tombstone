package com.tombstone.server.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.lebcool.common.logging.Logger;

@Path("/thumbnail")
public final class GetThumbnailEndPoint
{
    //:: ---------------------------------------------------------------------
    //:: Public Construction

    public GetThumbnailEndPoint()
    {
        LOGGER.info(this, "Successfully constructed this end point.");
    }

    //:: ---------------------------------------------------------------------
    //:: Public Interface

    @GET
    @Path("/user/{id}")
    @Produces("image/jpeg")
    public Response getApplicationUserThumbnail(@PathParam("id") final long id)
    {
        LOGGER.debug(this, "Attempting to get the application user thumbnail "
            + "image using id=" + id);

        return Response.status(Status.OK).build();
    }

    @GET
    @Path("/cemetery/{id}")
    @Produces("image/jpeg")
    public Response getCemeteryThumbnail(@PathParam("id") final long id)
    {
        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/plot/{id}")
    @Produces("image/jpeg")
    public Response getPlotThumbnail(@PathParam("id") final long id)
    {
        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/person/{id}")
    @Produces("image/jpeg")
    public Response getPersonThumbnail()
    {
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public String toString()
    {
        return getClass().getName();
    }

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final Logger LOGGER = new Logger(GetThumbnailEndPoint.class);
}
