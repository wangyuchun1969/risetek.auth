package com.risetek.auth.client.application.security;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SecurityModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(SecurityPresenter.class, SecurityPresenter.MyView.class, ViewImpl.class,
                SecurityPresenter.MyProxy.class);
    }
}
