package com.risetek.auth.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.risetek.auth.client.application.app.AppModule;
import com.risetek.auth.client.application.auth.AuthModule;
import com.risetek.auth.client.application.home.HomeModule;
import com.risetek.auth.client.application.login.LoginModule;
import com.risetek.auth.client.application.menu.MenuModule;
import com.risetek.auth.client.application.resources.ResourcesModule;
import com.risetek.auth.client.application.security.SecurityModule;
import com.risetek.auth.client.application.security.editor.SecurityEditorModule;
import com.risetek.auth.client.application.tools.ToolsModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
    	install(new MenuModule());
        install(new HomeModule());
        install(new ToolsModule());
        install(new LoginModule());
        install(new AuthModule());
        install(new SecurityModule());
        install(new SecurityEditorModule());
        install(new ResourcesModule());
        install(new AppModule());
       
        bindPresenter(ApplicationPresenter.class,
        		ApplicationPresenter.MyView.class,
        		ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
    }
}
