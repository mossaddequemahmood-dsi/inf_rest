package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.entity.HttpMethod;
import com.dsi.rest.requestresponse.Request;

@RestResource
public class GetReqQueryParamResource {

	/*
	 * Sample command - curl -v -XGET
	 * http://localhost:8080/getRequestWithQueryParam\?name=mahmood
	 */
	@Path(mapping = "getRequestWithQueryParam", requestMethod = HttpMethod.GET)
	public String getRequestWithQueryParam(Request request) {
		return "{\"name\":\"" + request.getQueryParam("name") + "\"}";
	}

}