package com.dsi.rest.requestresponse;

import java.io.InputStream;

import com.dsi.rest.mutipart.Multipart;

public interface Request {

	InputStream getRequestStream();
	
	String getMethod();

	String getHeader(String name);

	String getCookie(String name);

	String getQueryParam(String name);

	String getPath();

	String getBaseUri();
	
	Multipart getMultipart();
}
