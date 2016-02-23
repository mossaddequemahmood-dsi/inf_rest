package com.dsi.rest.requestresponse;

import java.io.InputStream;

import javax.ws.rs.container.ContainerRequestContext;

import com.dsi.rest.mutipart.Multipart;
import com.dsi.rest.mutipart.MultipartBuilder;

public class HttpRestRequest implements Request {

	private ContainerRequestContext requestContext;
	private Multipart multipart;

	public HttpRestRequest(ContainerRequestContext requestContext) {
		this.requestContext = requestContext;
		this.multipart = new MultipartBuilder(this).build();
	}

	@Override
	public InputStream getRequestStream() {
		if (requestContext.hasEntity()) {
			return requestContext.getEntityStream();
		}
		return null;
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

	@Override
	public Multipart getMultipart() {
		return multipart;
	}

}
