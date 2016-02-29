package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.entity.HttpMethod;
import com.dsi.rest.entity.MediaType;

public class IgnoredPathExampleResource {

	/*
	 * Sample command - curl -v -XGET http://localhost:8080/helloGetRequest
	 */
	@Path(mapping = "helloGetRequest", requestMethod = HttpMethod.GET, responseContentType = MediaType.TEXT_PLAIN_TYPE)
	public String helloGetRequest() {
		return "hello!";
	}

}