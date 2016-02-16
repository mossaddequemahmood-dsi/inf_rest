package com.dsi.rest.exception;

public class GenericErrorMessage extends AbstructErrorMessage {

	public GenericErrorMessage(int statusCode, String message) {
		setStatusCode(statusCode);
		setMessage(message);
	}
}
