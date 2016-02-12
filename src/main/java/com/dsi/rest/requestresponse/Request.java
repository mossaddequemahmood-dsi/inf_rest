package com.dsi.rest.requestresponse;

public interface Request {

	String getMethod();

	String getHeader(String name);

	String getCookie(String name);

	String getQueryParam(String name);

	String getPath();

	String getBaseUri();
}
