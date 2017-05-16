package com.risetek.auth.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.risetek.auth.server.guice.MyServletModule;

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
		super.contextDestroyed(servletContextEvent);
	}
	
	@Override
	protected Injector getInjector() {
		Injector injector = Guice.createInjector(
//				new MyServletModule()
				);
		return injector;
	}
}
