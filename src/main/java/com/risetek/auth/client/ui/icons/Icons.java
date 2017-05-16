package com.risetek.auth.client.ui.icons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class Icons {
	
	public static IconBundle resources = GWT.create(IconBundle.class);
	
	public interface IconBundle extends ClientBundle {
	@Source("risetekLogo.png")
	ImageResource RisetekLogo();
	}
}