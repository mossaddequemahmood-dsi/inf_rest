package com.dsi.rest.example.exception;

import com.dsi.rest.exception.ErrorMessage;
import com.dsi.rest.exception.ExceptionHandler;
import com.dsi.rest.exception.GenericErrorMessage;
import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;
import com.dsi.rest.requestresponse.Status;

public class NpeHandler implements ExceptionHandler {

	public void handle(Request request, Response response, Exception e) {
		ErrorMessage msg = new GenericErrorMessage(Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.toString());
		response.setEntity(msg);
		response.setStatus(Status.INTERNAL_SERVER_ERROR);
	}

}
