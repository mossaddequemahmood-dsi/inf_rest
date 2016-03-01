package com.dsi.rest.jersey;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;

import org.glassfish.jersey.process.Inflector;

import com.dsi.rest.filter.GenericPreFilterHandler;
import com.dsi.rest.filter.PreFilterHandler;
import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;
import com.dsi.rest.util.StringUtil;

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

		int reqBodyLength = 0;

		String reqBody = null;
		try {
			reqBodyLength = context.getEntityStream().available();
			if (reqBodyLength > 0) {
				reqBody = StringUtil.convertStreamToString(context.getEntityStream());
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		List<Object> argList = new ArrayList<Object>();
		Map<String, List<String>> pathParams = context.getUriInfo().getPathParameters();
		for (List<String> vals : pathParams.values()) {
			argList.add(vals.get(0));
		}

		ResourceMethodArgumentBuilder argBuilder = new ResourceMethodArgumentBuilder(method, argList, reqBody, reqBodyLength, req,
				resp);
		Object[] args = argBuilder.build();

		OriginalExceptionHandler originalExceptionHandler = new OriginalExceptionHandler();
		try {
			return method.invoke(cls.newInstance(), args);
		} catch (InvocationTargetException e) {
			originalExceptionHandler.handle(req, resp, e, method);
		} catch (IllegalAccessException | IllegalArgumentException | InstantiationException e) {
			e.printStackTrace();
		}

		return null;
	}

}