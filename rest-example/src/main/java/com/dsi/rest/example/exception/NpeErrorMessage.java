package com.dsi.rest.example.exception;

import com.dsi.rest.exception.AbstructErrorMessage;

public class NpeErrorMessage extends AbstructErrorMessage {

	public NpeErrorMessage(int statusCode, String message) {
		setStatusCode(statusCode);
		setMessage(message);
	}
}
