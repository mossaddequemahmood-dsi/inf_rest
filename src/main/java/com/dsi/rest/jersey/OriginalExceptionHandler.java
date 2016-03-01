package com.dsi.rest.jersey;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.exception.ExceptionHandler;
import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;

public class OriginalExceptionHandler {

	public void handle(Request request, Response response, InvocationTargetException e, Method method) {
		try {
			throw e.getCause();
		} catch (Throwable e1) {
			handleOriginalException(request, response, (Exception) e1, method);
			e1.printStackTrace();
		}
	}

	private void handleOriginalException(Request request, Response response, Exception e, Method method) {

		Path path = method.getAnnotation(Path.class);
		@SuppressWarnings("unchecked")
		Class<ExceptionHandler> exceptionHandler = (Class<ExceptionHandler>) path.exceptionHandler();
		try {
			ExceptionHandler handler = exceptionHandler.newInstance();
			handler.handle(request, response, e);
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}

}
