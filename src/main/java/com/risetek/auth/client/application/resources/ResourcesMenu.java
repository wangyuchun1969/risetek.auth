package com.risetek.auth.client.application.resources;

import com.risetek.auth.client.application.menu.MenuWidget;
import com.risetek.auth.client.place.NameTokens;

public class ResourcesMenu extends MenuWidget {

	@Override
	public String getLabel() {
		return "权限";
	}

	@Override
	public void onClick() {
		Goto(NameTokens.resource);
	}

	@Override
	public boolean canReveal() {
		return getMyCurrentUser().getAuthorityInfo().isLogin();
	}
}
