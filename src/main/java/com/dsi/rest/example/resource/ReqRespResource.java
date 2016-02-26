package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.entity.HttpMethod;
import com.dsi.rest.entity.MediaType;
import com.dsi.rest.requestresponse.Response;

@RestResource
public class ReqRespResource {

	/*
	 * Sample command - curl -v -XGET
	 * http://localhost:8080/getRequestWithResponseHeader
	 */
	@Path(mapping = "getRequestWithResponseHeader", requestMethod = HttpMethod.GET, responseContentType = MediaType.APPLICATION_JSON_TYPE)
	public void getRequestWithResponseHeader(Response response) {
		response.setHeader("a", "b");
	}

}