package com.risetek.auth.client.application.resources;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ResourcesModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(PresenterImpl.class, PresenterImpl.MyView.class, ViewImpl.class,
                PresenterImpl.MyProxy.class);
    }
}
