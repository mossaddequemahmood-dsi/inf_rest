package com.dsi.rest.filter;

import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;

public interface Filter {
	void filter(Request request, Response response);
}
