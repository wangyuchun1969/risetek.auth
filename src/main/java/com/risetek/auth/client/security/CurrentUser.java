package com.risetek.auth.client.security;

import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.shared.AuthorityAction;
import com.risetek.auth.shared.AuthorityInfo;
import com.risetek.auth.shared.GetResult;

@Singleton
public class CurrentUser {
	
	private final EventBus eventBus;
	private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;
	
	@Inject
	public CurrentUser(final EventBus eventBus, DispatchAsync dispatcher, PlaceManager placeManager) {
		this.eventBus = eventBus;
		this.dispatcher = dispatcher;
		this.placeManager = placeManager;
	}
	
    private	AuthorityInfo authorityInfo;
	
    public void forceSync() {
    	GWT.log("sync user information");
		dispatcher.execute(new AuthorityAction(), new AsyncCallback<GetResult<AuthorityInfo>>() {
			@Override
			public void onFailure(Throwable caught) {
				GWT.log("user information failed");
			}

			@Override
			public void onSuccess(GetResult<AuthorityInfo> result) {
				// Here we get user information from server.
				GWT.log("we get user information");
				setAuthorityInfo(result.getResults());
				if(!authorityInfo.isLogin())
					placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.login).build());
				else
					placeManager.revealDefaultPlace();
			}});
    }
    
	public void setAuthorityInfo(AuthorityInfo authorityInfo) {
		this.authorityInfo = authorityInfo;

		for( Entry<String, Boolean>  e : authorityInfo.getRoles().entrySet() )
			GWT.log("Current have: " + e.getKey() + " " + (e.getValue() ? "powered" : "forribden"));
	}
	
	public AuthorityInfo getAuthorityInfo() {
		return authorityInfo;
	}
}
