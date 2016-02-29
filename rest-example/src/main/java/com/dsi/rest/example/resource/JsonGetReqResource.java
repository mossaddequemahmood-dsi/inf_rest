package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.entity.HttpMethod;
import com.dsi.rest.entity.MediaType;

@RestResource
public class JsonGetReqResource {

	public static final String SIMPLE_JSON = "{\"msg\" : \"Hellooooooo World!\"}";

	/*
	 * Sample command - curl -v -XGET http://localhost:8080/jsonGetRequest
	 */
	@Path(mapping = "jsonGetRequest", requestMethod = HttpMethod.GET, responseContentType = MediaType.APPLICATION_JSON_TYPE)
	public String jsonGetRequest() {
		return SIMPLE_JSON;
	}

}