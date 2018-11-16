package com.risetek.auth.client.application.account.editor;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class AccountEditorModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindSingletonPresenterWidget(
				EditorPresenter.class,
				EditorPresenter.MyView.class,
				PageView.class);
	}
}
