package com.dsi.rest.jersey;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.ws.rs.container.ContainerRequestContext;

import org.glassfish.jersey.process.Inflector;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.exception.ExceptionHandler;
import com.dsi.rest.filter.GenericPreFilterHandler;
import com.dsi.rest.filter.PreFilterHandler;
import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonMethodInflector implements Inflector<ContainerRequestContext, Object> {

	// FIXME: Load from prop file
	private final String REQ_KEY = "REQ";
	private final String RESP_KEY = "RESP";

	private Method method;
	private Class<?> cls;

	public CommonMethodInflector(Method method, Class<?> cls) {
		this.method = method;
		this.cls = cls;
	}

	public Object apply(ContainerRequestContext context) {

		Request req = (Request) context.getProperty(REQ_KEY);
		Response resp = (Response) context.getProperty(RESP_KEY);

		PreFilterHandler<Method> preFilterHandler = new GenericPreFilterHandler(method);
		preFilterHandler.handle(req, resp);

		boolean reqBodyMapped = false;
		int reqBodyLength = 0;
		String reqBody = null;
		int noOfArgs = method.getParameterTypes().length;

		try {
			reqBodyLength = context.getEntityStream().available();
			if (reqBodyLength > 0) {
				reqBody = convertStreamToString(context.getEntityStream());
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		List<Object> argList = new ArrayList<Object>();
		Map<String, List<String>> pathParams = context.getUriInfo().getPathParameters();
		for (List<String> vals : pathParams.values()) {
			argList.add(vals.get(0));
		}

		while (argList.size() < noOfArgs) {
			argList.add(null);
		}

		for (int i = 0; i < noOfArgs; i++) {
			Class<?> cls = method.getParameterTypes()[i];
			if (cls == Request.class) {
				argList.set(i, req);
			} else if (cls == Response.class) {
				argList.set(i, resp);
			} else {
				if (!reqBodyMapped && reqBodyLength > 0) {
					Object obj = fitEntityStreamInProperObject(reqBody, cls);
					if (obj != null) {
						argList.set(i, obj);
						reqBodyMapped = true;
					}
				}
			}
		}

		Object[] args = argList.toArray();

		try {
			return method.invoke(cls.newInstance(), args);
		} catch (InvocationTargetException e) {
			try {
				throw e.getCause();
			} catch (Throwable e1) {
				handleOriginalException(req, resp, (Exception) e1, method);
				e1.printStackTrace();
			}
		} catch (IllegalAccessException | IllegalArgumentException | InstantiationException e) {
			e.printStackTrace();
		}

		return null;
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

	private Object fitEntityStreamInProperObject(String reqBody, Class<?> cls) {
		Object returnObj = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			returnObj = mapper.readValue(reqBody, cls);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return returnObj;

	}

	private static String convertStreamToString(InputStream is) {
		@SuppressWarnings("resource")
		java.util.Scanner s = new Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}