package com.dsi.rest.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.dsi.rest.annotation.RestResource;

public class ReflectionUtil {

	private ReflectionUtil() {
		throw new IllegalStateException("Util class can not be instantiated.");
	}

	public static Reflections newReflections(String scanPackage) {
		Collection<URL> cBuilderUrls = ClasspathHelper.forPackage(scanPackage);
		Scanner[] scannners = { new MethodAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner() };
		ConfigurationBuilder cBuilder = new ConfigurationBuilder().setUrls(cBuilderUrls).setScanners(scannners);
		return new Reflections(cBuilder);
	}

	public static Set<Class<?>> findAnnotatedClasses(Reflections reflections, Class<RestResource> cls) {
		return reflections.getTypesAnnotatedWith(cls);
	}

	public static Set<Method> findAnnotatedMethods(Reflections reflections, Class<? extends Annotation> cls) {
		return reflections.getMethodsAnnotatedWith(cls);
	}

}
