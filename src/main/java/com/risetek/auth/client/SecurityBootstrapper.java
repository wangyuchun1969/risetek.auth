package com.risetek.auth.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.client.security.CurrentUser;
import com.risetek.auth.shared.AuthorityAction;
import com.risetek.auth.shared.AuthorityInfo;
import com.risetek.auth.shared.GetResult;

public class SecurityBootstrapper implements Bootstrapper {

	private final PlaceManager placeManager;
	private final CurrentUser user;
	private final DispatchAsync dispatcher;
	public static boolean authOnly = true;
	
	@Inject
	public SecurityBootstrapper(CurrentUser user, DispatchAsync dispatcher, PlaceManager placeManager) {
		this.user = user;
		this.dispatcher = dispatcher;
		this.placeManager = placeManager;
	}
	
	@Override
	public void onBootstrap() {
		if("/login.html".equals(Location.getPath())) {
			placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.auth).build());
			return;
		}
		
		authOnly = false;
		dispatcher.execute(new AuthorityAction(), new AsyncCallback<GetResult<AuthorityInfo>>() {
			@Override
			public void onFailure(Throwable caught) {
				GWT.log("user information failed");
			}

			@Override
			public void onSuccess(GetResult<AuthorityInfo> result) {
				// Here we get user information from server.
				user.setAuthorityInfo(result.getResults());
				placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.HOME).build());
			}});
	}

}
