package com.risetek.auth.client.application.app.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.ImportedWithPrefix;

public interface StyleBundle extends ClientBundle {
	public static StyleBundle resources = GWT.create(StyleBundle.class);
	
	@ImportedWithPrefix("AppEditorStyle")
	interface Style extends CssResource {
		public String Editor();
		public String EditorLable();
		public String editorBoxs();
		public String ItemOuter();
		public String editorButtonPanel();
		public String editorButton();
		public String editorSpace();
	}
	
	@Source("style.css")
	Style style();
}
