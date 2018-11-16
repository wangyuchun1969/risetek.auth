package com.risetek.auth.client.application.selfAccount.editor;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SelfAccountEditorModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindSingletonPresenterWidget(
				SelfAccountEditorPresenter.class,
				SelfAccountEditorPresenter.MyView.class,
				SelfAccountPageView.class);
	}
}
