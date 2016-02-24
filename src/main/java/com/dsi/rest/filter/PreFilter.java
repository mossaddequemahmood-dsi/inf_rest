package com.dsi.rest.filter;

import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;

public interface PreFilter {
	void filter(Request request, Response response);
}
