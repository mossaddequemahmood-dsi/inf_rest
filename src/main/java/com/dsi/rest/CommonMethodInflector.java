package com.dsi.rest;

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

import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;
import com.dsi.rest.requestresponse.Status;
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
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
			resp.setStatus(Status.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}

		return null;
	}

	private Object fitEntityStreamInProperObject(String reqBody, Class<?> cls) {
		Object returnObj = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			returnObj = mapper.readValue(reqBody, cls);
			System.out.println(returnObj);
		} catch (IOException e1) {
			System.out.println(e1);
		}
		return returnObj;

	}

	private static String convertStreamToString(InputStream is) {
		@SuppressWarnings("resource")
		java.util.Scanner s = new Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}