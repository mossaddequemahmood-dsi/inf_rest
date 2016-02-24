package com.dsi.rest.filter;

import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;

public interface PreFilterHandler<T> {

	void handle(Request request, Response response);

}
