package com.risetek.auth.client.application.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface ResBundle extends ClientBundle {
	
	public static ResBundle resources = GWT.create(ResBundle.class);
	
	interface Style extends CssResource {
		public String notes_mydream();
		public String view_panel();
		public String space_panel();
		public String flow_panel();
	}
	
	@Source("home.css")
	Style style();
}
