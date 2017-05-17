package com.risetek.auth.server.guice;

import org.apache.shiro.guice.web.GuiceShiroFilter;

import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.rpc.server.guice.DispatchServiceImpl;

public class MyServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		serve("/dispatch/*").with(DispatchServiceImpl.class);
		//shiro filter
        filter("/dispatch/*").through(GuiceShiroFilter.class);
	}
}
