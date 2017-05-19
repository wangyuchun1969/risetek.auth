package com.risetek.auth.client.application.auth;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class AuthModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(AuthPresenter.class, AuthPresenter.MyView.class, AuthView.class,
                AuthPresenter.MyProxy.class);
    }
}
