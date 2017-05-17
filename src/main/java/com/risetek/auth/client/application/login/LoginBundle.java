package com.risetek.auth.client.application.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.ImportedWithPrefix;
import com.google.gwt.resources.client.ImageResource;

public interface LoginBundle extends ClientBundle {
	
	public static LoginBundle resources = GWT.create(LoginBundle.class);
	
	@ImportedWithPrefix("loginWidget")
	interface Style extends CssResource {
		public String background();
		public String loginButton();
		public String topTitle();
		public String box_outer();
		public String box_outer_border();
		public String box_outer_border_highlight();
		public String box_icon();
		public String box_icon_img();
		public String box_input();
		public String box_input_area();
		public String box_tips();
		public String interval();
	}
	
	@Source("user-icon.png")
	ImageResource user_png();

	@Source("password-icon.png")
	ImageResource password_png();
	
	@Source("login.css")
	Style style();
}
