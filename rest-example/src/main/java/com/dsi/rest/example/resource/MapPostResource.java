package com.dsi.rest.example.resource;

import java.util.Map;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.entity.HttpMethod;
import com.dsi.rest.entity.MediaType;

@RestResource
public class MapPostResource {

	/*
	 * Sample command - curl -XPOST http://localhost:8080/rest/postReqWithMap -d '{"msg1":"Hello","msg2":"World"}'
	 */
	@Path(mapping = "postReqWithMap/{some}/{thing}", requestMethod = HttpMethod.POST, responseContentType = MediaType.APPLICATION_JSON_TYPE)
	public Map jsonGetRequestWithPojoJsonResponse(String some, String thing, Map inMap) {
		System.out.println("Some - " + some);
		System.out.println("Thing - " + thing);
		return inMap;
	}

}