package com.risetek.auth.client.application.selfAccount.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.ImportedWithPrefix;

public interface StyleBundle extends ClientBundle {
	static StyleBundle resources = GWT.create(StyleBundle.class);
	
	@ImportedWithPrefix("securityEditorStyle")
	interface Style extends CssResource {
		public String Editor();
		public String EditorLable();
		public String EditorTitleLabel();
		public String editorBoxs();
		public String ItemOuter();
		public String editorButtonPanel();
		public String editorButton();
		public String editorSpace();
	}
	
	@Source("style.css")
	Style style();
}
