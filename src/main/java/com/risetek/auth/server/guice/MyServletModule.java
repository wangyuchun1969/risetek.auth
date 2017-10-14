package com.risetek.auth.server.guice;

import org.apache.shiro.guice.web.GuiceShiroFilter;

import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.rpc.server.guice.DispatchServiceImpl;
import com.risetek.auth.server.DbManagement;
import com.risetek.auth.server.oltu.servlet.AuthorizeServlet;
import com.risetek.auth.server.oltu.servlet.TokenServlet;
import com.risetek.auth.server.oltu.servlet.UserResourceServlet;

public class MyServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		serve("/dispatch/*").with(DispatchServiceImpl.class);
		serve("/oauth/token").with(TokenServlet.class);
		serve("/oauth/authorize").with(AuthorizeServlet.class);
		serve("/oauth/user").with(UserResourceServlet.class);
		//shiro filter
        filter("/dispatch/*").through(GuiceShiroFilter.class);
        
        bind(DbManagement.class).asEagerSingleton();
	}
}
