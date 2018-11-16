package com.risetek.auth.client.application.account;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.risetek.auth.client.application.account.editor.AccountEditorModule;

public class ManageAccountModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
	
        bindPresenter(ManageAccountPresenter.class, ManageAccountPresenter.MyView.class, ViewImpl.class,
        		ManageAccountPresenter.MyProxy.class);	
        install(new AccountEditorModule());
    }
}
