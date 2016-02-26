package com.dsi.rest.requestresponse;

import java.util.Map;

public interface Response {

	Status getStatus();

	void setStatus(Status status);

	Object getEntity();

	void setEntity(Object entity);

	String getHeader(String name);

	void setHeader(String name, String value);

	Map<String, String> getHeaders();
	
	void redirect(String location);

}
