package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.Path.HttpMathod;
import com.dsi.rest.annotation.Path.MediaType;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;

@RestResource
public class ExampleResource2 {

	@Path(mapping = "felloworld/{user}/go/{age}", requestMethod = HttpMathod.GET, responseContentType = MediaType.TEXT_PLAIN_TYPE)
	public String getHello(String user, String age, String hr, Request request, Response response) {

		System.out.println(request);
		System.out.println(response);
		throw new IllegalArgumentException();
		//return "felloworld";
	}

}