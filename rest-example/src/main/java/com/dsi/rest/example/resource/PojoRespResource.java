package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.entity.HttpMethod;
import com.dsi.rest.entity.MediaType;
import com.dsi.rest.example.dto.Hello;

@RestResource
public class PojoRespResource {

	/*
	 * Sample command - curl -v -XGET
	 * http://localhost:8080/jsonGetRequestWithPojoJsonResponse
	 */
	@Path(mapping = "jsonGetRequestWithPojoJsonResponse", requestMethod = HttpMethod.GET, responseContentType = MediaType.APPLICATION_JSON_TYPE)
	public Hello jsonGetRequestWithPojoJsonResponse() {
		return new Hello("Hello", "World");
	}

}