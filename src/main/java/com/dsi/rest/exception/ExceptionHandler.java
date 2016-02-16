package com.dsi.rest.exception;

import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;

public interface ExceptionHandler {

	void handle(Request request, Response response, Exception e);

}
