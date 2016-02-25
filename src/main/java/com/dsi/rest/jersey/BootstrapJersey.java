package com.dsi.rest.jersey;

import java.util.List;

import org.glassfish.jersey.server.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dsi.rest.Bootstrap;
import com.dsi.rest.Route;
import com.dsi.rest.RouteScanner;
import com.dsi.rest.util.Transformer;

public class BootstrapJersey implements Bootstrap<JaxRsApplication> {

	private final Logger logger = LoggerFactory.getLogger(BootstrapJersey.class);

	// FIXME: Load from prop file
	private final String SCAN_PKG_REST_ANNOTATION = "com.dsi";

	public BootstrapJersey() {
		logger.info("Bootstraping Jersey...");
	}

	public void init(JaxRsApplication bootstrapedObj) {

		registerCommonFeature(bootstrapedObj);

		Transformer<Route, Resource> transformer = new RouteToResourceTransformer();
		RouteScanner scanner = RouteScanner.getInstance(SCAN_PKG_REST_ANNOTATION);

		List<Route> routes = scanner.listRoute();
		for (Route route : routes) {
			Resource resource = transformer.transform(route);
			bootstrapedObj.registerResources(resource);
		}
	}

	private void registerCommonFeature(JaxRsApplication bootstrapedObj) {
		bootstrapedObj.registerClasses(ResourceFilterBindingFeature.class);
	}

}
