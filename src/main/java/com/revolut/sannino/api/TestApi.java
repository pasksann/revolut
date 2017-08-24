package com.revolut.sannino.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("TestApi")
public class TestApi {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Running!";
    }
}
