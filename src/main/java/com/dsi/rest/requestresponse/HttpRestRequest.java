package com.dsi.rest.requestresponse;

import javax.ws.rs.container.ContainerRequestContext;

public class HttpRestRequest implements Request {

	private ContainerRequestContext requestContext;

	public HttpRestRequest(ContainerRequestContext requestContext) {
		this.requestContext = requestContext;
	}

	@Override
	public String getMethod() {
		return requestContext.getMethod();
	}

	@Override
	public String getHeader(String name) {
		return requestContext.getHeaderString(name);
	}

	@Override
	public String getCookie(String name) {
		return requestContext.getCookies().get(name).getValue();
	}

	@Override
	public String getQueryParam(String name) {
		return requestContext.getUriInfo().getQueryParameters().getFirst(name);
	}

	@Override
	public String getPath() {
		return requestContext.getUriInfo().getPath();
	}

	@Override
	public String getBaseUri() {
		return requestContext.getUriInfo().getBaseUri().toString();
	}

}
