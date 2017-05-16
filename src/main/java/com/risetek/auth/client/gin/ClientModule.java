package com.risetek.auth.client.gin;

import com.gwtplatform.dispatch.rpc.client.gin.RpcDispatchAsyncModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.risetek.auth.client.application.ApplicationModule;
import com.risetek.auth.client.place.NameTokens;

public class ClientModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
    	// System Special
        install(new DefaultModule
                .Builder()
                .defaultPlace(NameTokens.HOME)
                .errorPlace(NameTokens.HOME)
                .unauthorizedPlace(NameTokens.HOME)
                .build());
        
    	install(new RpcDispatchAsyncModule());

        // Application special
        install(new ApplicationModule());
    }
}
