package com.dsi.rest.example.resource;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.example.exception.NpeHandler;

@RestResource
public class NullPointerExceptionTestResource {

	@Path(mapping = "nullerror", exceptionHandler = NpeHandler.class)
	public String testNpe() {
		throw new NullPointerException("NPE :-(");
	}

}