package com.dsi.rest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dsi.rest.exception.GenericExceptionHandler;

@Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Path {

	String mapping() default "";

	HttpMethod requestMethod() default HttpMethod.GET;

	MediaType responseContentType() default MediaType.APPLICATION_JSON;

	Class<?> exceptionHandler() default GenericExceptionHandler.class;

	Class<?>[] preFilters() default {};

	enum HttpMethod {
		GET, POST, PUT, DELETE
	}

	enum MediaType {
		APPLICATION_JSON, TEXT_PLAIN_TYPE
	}
}