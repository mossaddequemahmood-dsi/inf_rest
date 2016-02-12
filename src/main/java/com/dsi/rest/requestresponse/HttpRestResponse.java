package com.dsi.rest.requestresponse;

import java.util.HashMap;
import java.util.Map;

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

}
