package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.entity.HttpMethod;
import com.dsi.rest.example.filter.ExamplePreFilter;

@RestResource
public class PreFilterExampleResource {

	/*
	 * Sample command - curl -v -XGET http://localhost:8080/prefilterGetRequest
	 */
	@Path(mapping = "prefilterGetRequest", requestMethod = HttpMethod.GET, preFilters = { ExamplePreFilter.class })
	public String plainTextGetRequest() {
		return "plainTextGetRequest - response";
	}

}