package com.risetek.auth.client.application.selfAccount;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.risetek.auth.client.application.selfAccount.editor.SelfAccountEditorModule;

public class ManageSelfAccountModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
	
        bindPresenter(ManageSelfAccountPresenter.class, ManageSelfAccountPresenter.MyView.class, ManageSelfAccountViewImpl.class,
        		ManageSelfAccountPresenter.MyProxy.class);	
        install(new SelfAccountEditorModule());
    }
}
