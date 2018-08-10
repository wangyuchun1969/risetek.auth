package com.risetek.auth.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.ImportedWithPrefix;

public interface FlatMenuStyleBundle extends ClientBundle {
	public static FlatMenuStyleBundle resources = GWT.create(FlatMenuStyleBundle.class);
	
	@ImportedWithPrefix("FlatMenuStyle")
	interface Style extends CssResource {
		String dropdown();
		String dropdownContent();
		String namewarp();
		String items();
	}
	
	@Source("flatmenu.css")
	Style style();
}
