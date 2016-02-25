package com.dsi.rest.jersey;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceBuilder {

	private final Logger logger = LoggerFactory.getLogger(ResourceBuilder.class);

	public Resource build(String path, String httpMethod, MediaType respMediaType,
			Inflector<ContainerRequestContext, Object> inflector) {

		logger.info("Building Jersey resource for path - {}, method - {}", path, httpMethod);

		final Resource.Builder resourceBuilder = Resource.builder();
		resourceBuilder.path(path);
		final ResourceMethod.Builder methodBuilder = resourceBuilder.addMethod(httpMethod);
		methodBuilder.produces(respMediaType).handledBy(inflector);
		return resourceBuilder.build();
	}

}
