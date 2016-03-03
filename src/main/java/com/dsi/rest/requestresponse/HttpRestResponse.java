package com.dsi.rest.requestresponse;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.dsi.rest.util.StringUtil;

public class HttpRestResponse implements Response {

	private Status status;
	private Object entity;
	private Map<String, String> header = new HashMap<String, String>();

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public Object getEntity() {
		return entity;
	}

	@Override
	public void setEntity(Object entity) {
		this.entity = entity;
	}

	@Override
	public String getHeader(String name) {
		return header.get(name);
	}

	@Override
	public void setHeader(String name, String value) {
		header.put(name, value);
	}

	@Override
	public Map<String, String> getHeaders() {
		return header;
	}

	@Override
	public void redirect(String location) {
		setStatus(Status.MOVED_PERMANENTLY);
		setHeader("Location", location);
		setHeader("Connection", "close");
		URI redirectURI = StringUtil.convertStringToURI(location);
		javax.ws.rs.core.Response.temporaryRedirect(redirectURI).build();
	}
}
