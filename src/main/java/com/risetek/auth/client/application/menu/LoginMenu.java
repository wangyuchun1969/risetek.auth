package com.risetek.auth.client.application.menu;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.risetek.auth.client.RevealDefaultLinkColumnHandler.RevealDefaultLinkColumnEvent;
import com.risetek.auth.client.place.NameTokens;
import com.risetek.auth.shared.GetNoResult;
import com.risetek.auth.shared.LogInOutAction;

public class LoginMenu extends MenuWidget {

	@Override
	public String getLabel() {
		if( getMyCurrentUser().getAuthorityInfo().isLogin() )
			return "退出";
		else
			return "登录";
	}

	@Override
	public void onClick() {
		if( getMyCurrentUser().getAuthorityInfo().isLogin() ) {
			exeCute(new LogInOutAction(null), new AsyncCallback<GetNoResult>() {

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(GetNoResult result) {
					Goto(NameTokens.HOME);
					// To Update menu module.
			        fireEvent(new RevealDefaultLinkColumnEvent());
				}
			});
		} else {
			Goto(NameTokens.login);
		}
	}

	@Override
	public boolean canReveal() {
		return true;
	}
}
