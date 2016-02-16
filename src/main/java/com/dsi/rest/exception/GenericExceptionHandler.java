package com.dsi.rest.exception;

import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;
import com.dsi.rest.requestresponse.Status;

public class GenericExceptionHandler implements ExceptionHandler {

	public static String ERROR_MSG = "Unknown error occured.";

	public void handle(Request request, Response response, Exception e) {
		ErrorMessage msg = new GenericErrorMessage(Status.INTERNAL_SERVER_ERROR.getStatusCode(), ERROR_MSG);
		response.setEntity(msg);
		response.setStatus(Status.INTERNAL_SERVER_ERROR);
	}

}
