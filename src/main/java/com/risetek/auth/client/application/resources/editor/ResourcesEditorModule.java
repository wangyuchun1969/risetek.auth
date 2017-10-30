package com.risetek.auth.client.application.resources.editor;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ResourcesEditorModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindSingletonPresenterWidget(
				EditorPresenter.class,
				EditorPresenter.MyView.class,
				PageView.class);
	}
}
