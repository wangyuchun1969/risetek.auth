package com.risetek.auth.client.application;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface ApplicationBundle extends ClientBundle {
	
	public static ApplicationBundle resources = GWT.create(ApplicationBundle.class);
	
	interface ApplicationStyle extends CssResource {
		public String menuSolt();
		public String blackTitle();
		public String welcome();
		public String builder();
		public String mainSlot();
		public String clients();
		public String logo();
		public String black_head_wrap();
	}
	
	@Source("application.css")
	ApplicationStyle style();
}
