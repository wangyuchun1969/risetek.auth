package com.risetek.auth.client.application.tools;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ToolsModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(ToolsPresenter.class, ToolsPresenter.MyView.class, ToolsView.class,
                ToolsPresenter.MyProxy.class);
    }
}
