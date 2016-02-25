package com.dsi.rest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.util.ReflectionUtil;

public class RouteScanner {

	private final Logger logger = LoggerFactory.getLogger(RouteScanner.class);

	private static RouteScanner INSTANCE;
	private String scanPkgRestAnnotation;
	private List<Route> routes = new ArrayList<Route>();

	private RouteScanner(String scanPkgRestAnnotation) {
		this.scanPkgRestAnnotation = scanPkgRestAnnotation;
		init();
	}

	public static RouteScanner getInstance(String scanPkgRestAnnotation) {
		if (INSTANCE == null) {
			INSTANCE = new RouteScanner(scanPkgRestAnnotation);
		}
		return INSTANCE;
	}

	public List<Route> listRoute() {
		return routes;
	}

	private void init() {

		Reflections reflections = ReflectionUtil.newReflections(scanPkgRestAnnotation);

		Set<Class<?>> restResourceClasses = ReflectionUtil.findAnnotatedClasses(reflections, RestResource.class);
		Set<Method> methods = ReflectionUtil.findAnnotatedMethods(reflections, Path.class);

		for (Iterator<Method> iterator = methods.iterator(); iterator.hasNext();) {
			Method method = (Method) iterator.next();
			if (!restResourceClasses.contains(method.getDeclaringClass())) {
				Path path = (Path) method.getAnnotation(Path.class);
				logger.info("Path mapping - {} removed since class {} is not annotated with RestResource",
						path.mapping(), method.getDeclaringClass());
				iterator.remove();
			}
		}

		for (Method method : methods) {
			Path path = (Path) method.getAnnotation(Path.class);
			Route route = new Route.RouteBuilder(path.mapping(), method).httpMethod(path.requestMethod().toString())
					.responseContentType(path.responseContentType().toString()).build();
			routes.add(route);
		}

	}

}
