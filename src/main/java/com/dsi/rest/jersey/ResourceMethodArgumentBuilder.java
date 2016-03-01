package com.dsi.rest.jersey;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResourceMethodArgumentBuilder {

	private Method method;
	private String reqBody;
	private List<Object> argList = new ArrayList<Object>();
	private Request req;
	private Response resp;
	int reqBodyLength;

	public ResourceMethodArgumentBuilder(Method method, List<Object> pathParams, String requestBody, int reqBodyLength,
			Request request, Response response) {
		this.method = method;
		this.argList.addAll(pathParams);
		this.reqBody = requestBody;
		this.req = request;
		this.resp = response;
		this.reqBodyLength = reqBodyLength;
	}

	public Object[] build() {

		boolean reqBodyMapped = false;
		int noOfArgs = method.getParameterTypes().length;

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

		return argList.toArray();
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

}
