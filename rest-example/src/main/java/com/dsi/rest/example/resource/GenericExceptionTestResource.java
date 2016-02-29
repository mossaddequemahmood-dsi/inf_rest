package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.RestResource;

@RestResource
public class GenericExceptionTestResource {

	@Path(mapping = "error")
	public String testError() {
		throw new IllegalArgumentException();
	}

}