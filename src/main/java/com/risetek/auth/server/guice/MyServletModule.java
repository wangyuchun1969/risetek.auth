package com.risetek.auth.server.guice;

import org.apache.shiro.guice.web.GuiceShiroFilter;

import com.google.inject.servlet.ServletModule;

public class MyServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		//shiro filter
        filter("/dispatch/*").through(GuiceShiroFilter.class);
	}
}
