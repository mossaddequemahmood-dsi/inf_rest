package com.dsi.rest.jersey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.Set;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dsi.rest.Bootstrap;
import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.PathParam;
import com.dsi.rest.filter.internal.ResourceFilterBindingFeature;

public class BootstrapJersey implements Bootstrap<JaxRsApplication> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// FIXME: Load from prop file
	private final String SCAN_PKG_REST_ANNOTATION = "com.dsi";

	public BootstrapJersey() {
		logger.info("Bootstraping Jersey...");
	}

	public void init(JaxRsApplication bootstrapedObj) {

		bootstrapedObj.registerClasses(ResourceFilterBindingFeature.class);

		Reflections reflections = newReflections();

		try {
			Set<Method> methods = findAnnotatedMethods(reflections, Path.class);
			for (Method method : methods) {
				processMethodParamAnnotation(method);
				Path path = (Path) method.getAnnotation(Path.class);
				Resource resource = buildResource(path.mapping(), findHttpMethod(path), findMediaType(path),
						new CommonMethodInflector(method, method.getDeclaringClass()));
				bootstrapedObj.registerResources(resource);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}

	}

	private void processMethodParamAnnotation(Method method) {

		// Parameter[] parameters = method.getParameters();
		// method.getParameterTypes()

		Annotation[][] parameterAnnotations = method.getParameterAnnotations();

		for (Annotation[] annotations : parameterAnnotations) {
			PathParam pathParam = null;
			for (Annotation annotation : annotations) {
				if (annotation instanceof PathParam) {
					pathParam = (PathParam) annotation;
					break;
				}
			}

			if (pathParam != null) {
				System.out.println("Found path param!");
			}
		}
	}

	private Resource buildResource(String path, String httpMethod, MediaType respMediaType,
			Inflector<ContainerRequestContext, Object> inflector) {
		final Resource.Builder resourceBuilder = Resource.builder();
		resourceBuilder.path(path);
		final ResourceMethod.Builder methodBuilder = resourceBuilder.addMethod(httpMethod);
		methodBuilder.produces(respMediaType).handledBy(inflector);
		return resourceBuilder.build();
	}

	private Reflections newReflections() {
		Collection<URL> cBuilderUrls = ClasspathHelper.forPackage(SCAN_PKG_REST_ANNOTATION);
		Scanner[] scannners = { new MethodAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner() };
		ConfigurationBuilder cBuilder = new ConfigurationBuilder().setUrls(cBuilderUrls).setScanners(scannners);
		return new Reflections(cBuilder);
	}

	private Set<Method> findAnnotatedMethods(Reflections reflections, Class<? extends Annotation> cls) {
		return reflections.getMethodsAnnotatedWith(cls);
	}

	private MediaType findMediaType(Path path) {
		MediaType respMadiaType = MediaType.APPLICATION_JSON_TYPE;

		switch (path.responseContentType()) {
		case APPLICATION_JSON:
			respMadiaType = MediaType.APPLICATION_JSON_TYPE;
			break;
		case TEXT_PLAIN_TYPE:
			respMadiaType = MediaType.TEXT_PLAIN_TYPE;
			break;
		}

		return respMadiaType;
	}

	private String findHttpMethod(Path path) {
		return path.requestMethod().toString();
	}
}
