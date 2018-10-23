package com.risetek.auth.client.application.app;

import com.risetek.auth.client.application.menu.MenuWidget;
import com.risetek.auth.client.place.NameTokens;

public class AppMenu extends MenuWidget {

	@Override
	public String getLabel() {
		return "修改";
	}

	@Override
	public void onClick() {
		Goto(NameTokens.app);
	}

	@Override
	public boolean canReveal() {
		return getMyCurrentUser().getAuthorityInfo().isLogin();
	}
}