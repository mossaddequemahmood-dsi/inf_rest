package com.dsi.rest.jersey;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceFilterBindingFeature implements DynamicFeature {

	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		context.register(CommonFilter.class);
	}
}
