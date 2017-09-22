package com.snapchat.service;

import com.snapchat.image.Image;
import com.snapchat.image.ImageHolder;
import org.quartz.SchedulerException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by apolonxviii on 22.09.17.
 */
@Path("image")
public class ImageService {

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public String getIt(byte[] file, @QueryParam("time") long time) throws SchedulerException {
        Image img = new Image(file, System.currentTimeMillis() + time);

        String path = ImageHolder.putImages(img);
        System.out.println("Saved img with path:" + path + " for ms:" + time + ", size: " + file.length);
        return path;
    }

    @GET
    @Path("/{path}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response removeMessage(@PathParam("path") String path) {
        try {
            Image img = ImageHolder.getImage(path);
            if (img != null) {
                return Response.status(Response.Status.OK).entity(img.getFile()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (IndexOutOfBoundsException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error").build();
        }
    }

}
