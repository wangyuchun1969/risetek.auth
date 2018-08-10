package com.risetek.auth.client.application.security;

import com.risetek.auth.client.application.menu.MenuWidget;
import com.risetek.auth.client.place.NameTokens;

public class SecurityMenu extends MenuWidget {

	@Override
	public String getLabel() {
		return "用户";
	}

	@Override
	public void onClick() {
		Goto(NameTokens.security);
	}

	@Override
	public boolean canReveal() {
		return getMyCurrentUser().getAuthorityInfo().isLogin();
	}
}
