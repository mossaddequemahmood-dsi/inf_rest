package com.dsi.rest.jersey;

import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.model.Resource;

import com.dsi.rest.Route;
import com.dsi.rest.util.Transformer;

public class RouteToResourceTransformer implements Transformer<Route, Resource> {

	@Override
	public Resource transform(Route route) {

		ResourceBuilder resourceBuilder = new ResourceBuilder();
		Resource resource = resourceBuilder.build(route.getPath(), route.getHttpMethod(), findMediaType(route
				.getResponseContentType()), new CommonMethodInflector(route.getMethod(), route.getMethod()
				.getDeclaringClass()));
		return resource;
	}

	private MediaType findMediaType(String respMediaTypeStr) {
		MediaType respMadiaType = MediaType.APPLICATION_JSON_TYPE;

		switch (respMediaTypeStr) {
		case "APPLICATION_JSON_TYPE":
			respMadiaType = MediaType.APPLICATION_JSON_TYPE;
			break;
		case "TEXT_PLAIN_TYPE":
			respMadiaType = MediaType.TEXT_PLAIN_TYPE;
			break;
		}

		return respMadiaType;
	}

}
