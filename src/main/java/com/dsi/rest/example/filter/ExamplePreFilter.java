package com.dsi.rest.example.filter;

import com.dsi.rest.filter.PreFilter;
import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;

public class ExamplePreFilter implements PreFilter {

	public void filter(Request request, Response response) {
		System.out.println("Inside ExamplePreFilter.");
	}

}
