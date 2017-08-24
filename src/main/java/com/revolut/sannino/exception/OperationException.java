package com.revolut.sannino.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class OperationException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	public OperationException() {
        super(Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).build());
    }

    public OperationException(String message) {
      super(Response.status(Response.Status.BAD_REQUEST).entity(message).type(MediaType.TEXT_PLAIN).build());
    }

    public OperationException(JsonError jse) {
      super(Response.status(Response.Status.BAD_REQUEST).entity(jse).type(MediaType.APPLICATION_JSON).build());
    }

}
