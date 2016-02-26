package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.entity.HttpMethod;
import com.dsi.rest.entity.MediaType;

@RestResource
public class PlainTextGetReqResource {

	/*
	 * Sample command - curl -v -XGET http://localhost:8080/plainTextGetRequest
	 */
	@Path(mapping = "plainTextGetRequest", requestMethod = HttpMethod.GET, responseContentType = MediaType.TEXT_PLAIN_TYPE)
	public String plainTextGetRequest() {
		return "plainTextGetRequest - response";
	}

}