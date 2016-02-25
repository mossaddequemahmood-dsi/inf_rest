package com.dsi.rest;

import java.lang.reflect.Method;

public class Route {

	private String path;
	private String httpMethod;
	private String responseContentType;
	private Method method;

	private Route(final String path, final String httpMethod, final String responseContentType, final Method method) {
		this.path = path;
		this.httpMethod = httpMethod;
		this.responseContentType = responseContentType;
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getResponseContentType() {
		return responseContentType;
	}

	public Method getMethod() {
		return method;
	}

	public static class RouteBuilder {

		private String path;
		private String httpMethod = "GET";
		private String responseContentType = "APPLICATION_JSON";
		private Method method;

		public RouteBuilder(String path, Method method) {
			this.path = path;
			this.method = method;
		}

		public RouteBuilder httpMethod(String httpMethod) {
			this.httpMethod = httpMethod;
			return this;
		}

		public RouteBuilder responseContentType(String responseContentType) {
			this.responseContentType = responseContentType;
			return this;
		}

		public Route build() {
			return new Route(path, httpMethod, responseContentType, method);
		}
	}

	@Override
	public String toString() {
		return "Route [path=" + path + ", httpMethod=" + httpMethod + ", responseContentType=" + responseContentType
				+ ", method=" + method + "]";
	}

}
