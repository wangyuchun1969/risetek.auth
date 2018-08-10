package com.risetek.auth.client.application.menu;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class MenuModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(MenuPagePresenter.class, MenuPagePresenter.MyView.class, MenuPageView.class,
                MenuPagePresenter.MyProxy.class);
    }
}
