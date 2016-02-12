package com.dsi.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class JaxRsApplication extends ResourceConfig {

	public JaxRsApplication() {
		Bootstrap<JaxRsApplication> bootstrap = new BootstrapJersey();
		bootstrap.init(this);
	}
}
