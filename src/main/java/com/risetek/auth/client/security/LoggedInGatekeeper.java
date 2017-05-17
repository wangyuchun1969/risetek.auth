package com.risetek.auth.client.security;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

@Singleton
public class LoggedInGatekeeper implements Gatekeeper {
	
	private final CurrentUser user;

	@Inject
	LoggedInGatekeeper(CurrentUser currentUser) {
		this.user = currentUser;
	}

	@Override
	public boolean canReveal() {
		if( user.getAuthorityInfo() == null ) {
			GWT.log("CurrentUser is not special.");
			return false;
		}
		return user.getAuthorityInfo().isLogin();
	}
}
