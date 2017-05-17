package com.risetek.auth.client.gin;

import com.gwtplatform.dispatch.rpc.client.gin.RpcDispatchAsyncModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.risetek.auth.client.application.ApplicationModule;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.client.security.CurrentUser;
import com.risetek.auth.client.security.LoggedInGatekeeper;

public class ClientModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
    	// System Special
        install(new DefaultModule
                .Builder()
                .defaultPlace(NameTokens.HOME)
                .errorPlace(NameTokens.HOME)
                .unauthorizedPlace(NameTokens.login)
                .build());
        
    	install(new RpcDispatchAsyncModule());

        // Application special
        install(new ApplicationModule());
        
        bind(CurrentUser.class).asEagerSingleton();
        bind(LoggedInGatekeeper.class);
    }
}
