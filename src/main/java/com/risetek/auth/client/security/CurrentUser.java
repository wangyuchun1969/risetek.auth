package com.risetek.auth.client.security;

import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
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
	
	public enum InfoStatus {UNDETECTED, SYNCED };
	
	public InfoStatus InformationState = InfoStatus.UNDETECTED;
	
    private	AuthorityInfo currentAuthorityInfo;
	
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

				// Goto Place default
				if(!currentAuthorityInfo.isLogin() 
					&& (placeManager.getCurrentPlaceRequest().getNameToken() != NameTokens.login))
					placeManager.revealDefaultPlace();
			}});
    }
    
	public void setAuthorityInfo(AuthorityInfo authorityInfo) {
		if(null == authorityInfo)
			GWT.log("current user is null");
		
		InformationState = InfoStatus.SYNCED;
		currentAuthorityInfo = authorityInfo;

		for( Entry<String, Boolean>  e : currentAuthorityInfo.getRoles().entrySet() )
			GWT.log("Current have: " + e.getKey() + " " + (e.getValue() ? "powered" : "forribden"));
	}
	
	public AuthorityInfo getAuthorityInfo() {
		return currentAuthorityInfo;
	}
}
