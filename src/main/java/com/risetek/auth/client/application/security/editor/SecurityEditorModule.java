package com.risetek.auth.client.application.security.editor;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SecurityEditorModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindSingletonPresenterWidget(
				EditorPresenter.class,
				EditorPresenter.MyView.class,
				PageView.class);
	}
}
