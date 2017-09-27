package com.risetek.auth.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.risetek.auth.client.application.auth.AuthModule;
import com.risetek.auth.client.application.home.HomeModule;
import com.risetek.auth.client.application.login.LoginModule;
import com.risetek.auth.client.application.security.SecurityModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new HomeModule());
        install(new LoginModule());
        install(new AuthModule());
        install(new SecurityModule());

        bindPresenter(ApplicationPresenter.class,
        		ApplicationPresenter.MyView.class,
        		ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
    }
}
