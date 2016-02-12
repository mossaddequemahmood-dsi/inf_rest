package com.dsi.rest.example.filter;

import com.dsi.rest.filter.Filter;
import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;

public class ExampleFilter2 implements Filter {

	public void filter(Request request, Response response) {
		System.out.println("Inside ExampleFilter2.");
	}

}
