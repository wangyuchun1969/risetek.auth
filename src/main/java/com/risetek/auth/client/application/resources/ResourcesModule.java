package com.risetek.auth.client.application.resources;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.risetek.auth.client.application.resources.editor.ResourcesEditorModule;

public class ResourcesModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(PresenterImpl.class, PresenterImpl.MyView.class, ViewImpl.class,
                PresenterImpl.MyProxy.class);
        install(new ResourcesEditorModule());
    }
}
