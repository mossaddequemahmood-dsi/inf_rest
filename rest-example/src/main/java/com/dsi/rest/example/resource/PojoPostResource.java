package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.entity.HttpMethod;
import com.dsi.rest.entity.MediaType;
import com.dsi.rest.example.dto.Hello;

@RestResource
public class PojoPostResource {

	/*
	 * Sample command - curl -XPOST
	 * http://localhost:8080/jsonPostRequestWithPojoJsonResponse -d
	 * '{"msg1":"Hello","msg2":"World"}'
	 */
	@Path(mapping = "jsonPostRequestWithPojoJsonResponse", requestMethod = HttpMethod.POST, responseContentType = MediaType.APPLICATION_JSON_TYPE)
	public Hello jsonPostRequestWithPojoJsonResponse(Hello hello) {
		return hello;
	}

}