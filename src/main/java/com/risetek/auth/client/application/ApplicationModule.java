package com.risetek.auth.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.risetek.auth.client.application.auth.AuthModule;
import com.risetek.auth.client.application.home.HomeModule;
import com.risetek.auth.client.application.login.LoginModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new HomeModule());
        install(new LoginModule());
        install(new AuthModule());

        bindPresenter(ApplicationPresenter.class,
        		ApplicationPresenter.MyView.class,
        		ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
    }
}
