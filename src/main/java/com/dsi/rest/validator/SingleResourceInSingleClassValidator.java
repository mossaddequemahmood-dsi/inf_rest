package com.dsi.rest.validator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dsi.rest.annotation.Path;
import com.dsi.rest.annotation.RestResource;
import com.dsi.rest.util.ReflectionUtil;

public class SingleResourceInSingleClassValidator implements ContextValidator<String> {

	private final Logger logger = LoggerFactory.getLogger(SingleResourceInSingleClassValidator.class);

	@Override
	public void validate(String scanPkgRestAnnotation) {

		Map<Class<?>, Method> matchMap = new HashMap<Class<?>, Method>();

		Reflections reflections = ReflectionUtil.newReflections(scanPkgRestAnnotation);

		Set<Class<?>> restResourceClasses = ReflectionUtil.findAnnotatedClasses(reflections, RestResource.class);
		for (Class<?> cls : restResourceClasses) {
			matchMap.put(cls, null);
		}

		Set<Method> methods = ReflectionUtil.findAnnotatedMethods(reflections, Path.class);
		for (Method method : methods) {
			if (matchMap.get(method.getDeclaringClass()) == null) {
				matchMap.put(method.getDeclaringClass(), method);
			} else {
				logger.error("Class {} contain more than one @Path method.", method.getDeclaringClass());
				throw new IllegalStateException("Each class should contain only one resource. Class "
						+ method.getDeclaringClass() + " contains more than one.");
			}

		}

	}

}
