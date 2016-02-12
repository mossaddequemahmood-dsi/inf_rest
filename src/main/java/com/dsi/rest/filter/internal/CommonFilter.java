package com.dsi.rest.filter.internal;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.reflections.Reflections;

import com.dsi.rest.filter.Filter;
import com.dsi.rest.requestresponse.HttpRestRequest;
import com.dsi.rest.requestresponse.HttpRestResponse;
import com.dsi.rest.requestresponse.Request;
import com.dsi.rest.requestresponse.Response;

public class CommonFilter implements ContainerRequestFilter, ContainerResponseFilter {

	// FIXME: Load from prop file
	private final String SCAN_PKG_REST_ANNOTATION = "com.dsi";
	private final String REQ_KEY = "REQ";
	private final String RESP_KEY = "RESP";

	private Map<String, Filter> cache = new WeakHashMap<String, Filter>();

	public void filter(ContainerRequestContext requestContext) throws IOException {

		Request req = new HttpRestRequest(requestContext);
		requestContext.setProperty(REQ_KEY, req);

		Response resp = new HttpRestResponse();
		requestContext.setProperty(RESP_KEY, resp);

		// javax.ws.rs.core.Response.noContent().build();

		// FIXME: What happen if no filter added, always scan could be
		// performance issue.
		if (cache == null || cache.size() == 0) {
			buildCache();
		}

		Set<Filter> filters = new HashSet<Filter>(cache.values());
		for (Filter f : filters) {
			f.filter(req, resp);
		}

	}

	private void buildCache() {
		Reflections reflections = new Reflections(SCAN_PKG_REST_ANNOTATION);
		Set<Class<? extends Filter>> filters = reflections.getSubTypesOf(Filter.class);
		for (Class<? extends Filter> cls : filters) {
			try {
				cache.put(cls.getCanonicalName(), (Filter) cls.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		Response resp = (Response) requestContext.getProperty(RESP_KEY);

		if (resp.getStatus() != null) {
			responseContext.setStatus(resp.getStatus().getStatusCode());
		}

		if (resp.getEntity() != null) {
			responseContext.setEntity(resp.getEntity());
		}

		if (resp.getHeaders().size() > 0) {
			MultivaluedMap<String, Object> headers = responseContext.getHeaders();
			for (Map.Entry<String, String> header : resp.getHeaders().entrySet()) {
				headers.add(header.getKey(), header.getValue());
			}
		}
	}
}
