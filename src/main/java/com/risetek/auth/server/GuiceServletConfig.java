package com.risetek.auth.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.risetek.auth.server.guice.MyHandlerModule;
import com.risetek.auth.server.guice.MyServletModule;
import com.risetek.auth.server.shiro.MyShiroWebModule;

/*
 * WEB-INF/web.xml 声明的listener启动了该ServletConfig
 */
public class GuiceServletConfig extends GuiceServletContextListener {
	private ServletContext servletContext;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		this.servletContext = servletContextEvent.getServletContext();
		super.contextInitialized(servletContextEvent);
	}	
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		Injector injector = (Injector) servletContext.getAttribute(Injector.class.getName());
		if(null != injector) {
			DbManagement dbm = injector.getInstance(DbManagement.class);
			dbm.closeConnection();
		}

		super.contextDestroyed(servletContextEvent);
	}
	
	@Override
	protected Injector getInjector() {
		Injector injector = Guice.createInjector(
				new MyServletModule(),
				new MyHandlerModule(),
				new MyShiroWebModule(servletContext)
				);
		return injector;
	}
}
