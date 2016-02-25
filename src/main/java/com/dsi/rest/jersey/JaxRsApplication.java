package com.dsi.rest.jersey;

import org.glassfish.jersey.server.ResourceConfig;

import com.dsi.rest.Bootstrap;

public class JaxRsApplication extends ResourceConfig {

	public JaxRsApplication() {
		Bootstrap<JaxRsApplication> bootstrap = new BootstrapJersey();
		bootstrap.init(this);
	}
}
